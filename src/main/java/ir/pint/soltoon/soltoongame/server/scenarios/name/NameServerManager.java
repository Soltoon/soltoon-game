package ir.pint.soltoon.soltoongame.server.scenarios.name;

import ir.pint.soltoon.soltoongame.client.LocalClientManager;
import ir.pint.soltoon.soltoongame.server.ServerComminucation;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.filters.AgentFilter;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.Check;
import ir.pint.soltoon.soltoongame.shared.map.GameAwareElement;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

public class NameServerManager extends ServerManager {

    public NameServerManager(ServerComminucation server, int width, int height) {
        this.players = 1;
        this.rounds = 1;
        this.server = server;
        this.width = width;
        this.height = height;
        this.gameBoard = new ManagerGame(this.height, this.width);
        this.gameBoard = new ManagerGame(this.height, this.width);
    }

    @Override
    public void run() {
        addMapInfoToResult();
        initiateExternalClients();

        ManagerGameSoltoon gameSoltoon = ((ManagerGameSoltoon) gameBoard.getSoltoons().values().iterator().next());
        gameSoltoon.setMaster(true);

        startRounds();
        quitPlayers();
        Platform.exit(Platform.OK);
    }

    @Override
    protected void addMapInfoToResult() {
        super.addMapInfoToResult();

        ResultStorage.putMisc("scenario", "name");
        ResultStorage.putMisc("finalSceneOnly", true);
    }

    private void startRounds() {
        startRound(0);
        ResultStorage.addEvent(new GameEndedEvent());
    }


    private void startRound(int round) {
        gameBoard.setRound(round);

        querySoltoons();
    }

}
