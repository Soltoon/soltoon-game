package ir.pint.soltoon.soltoongame.server.scenarios.freeWorld;

import ir.pint.soltoon.soltoongame.server.ServerComminucation;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class FreeWorldServerManager extends ServerManager {
    public FreeWorldServerManager(ServerComminucation server) {
        this.server = server;
        this.players = GameConfiguration.NUMBER_OF_PLAYERS;
        this.width = GameConfiguration.BOARD_WIDTH;
        this.height = GameConfiguration.BOARD_HEIGHT;
        this.rounds = GameConfiguration.ROUNDS;
        this.gameBoard = new ManagerGame(this.height, this.width);
    }

    public FreeWorldServerManager(ServerComminucation server, int players, int width, int height, int rounds) {
        this.server = server;
        this.players = players;
        this.width = width;
        this.height = height;
        this.rounds = rounds;
        this.gameBoard = new ManagerGame(this.height, this.width);
    }

    @Override
    public void run() {
        addMapInfoToResult();

        initiateExternalClients();

        startRounds();

        quitPlayers();

        Platform.exit(Platform.OK);
    }

    private void startRounds() {
        for (int round = 0; round < this.rounds; round++) {
            startRound(round);
        }
        ResultStorage.addEvent(new GameEndedEvent());
    }


    private void startRound(int round) {

        gameBoard.setRound(round);

        updateSoltoons();
        querySoltoons();

        updateKhadangs();
        queryKhadangs();

        removeKilledKhadangs();
    }
}
