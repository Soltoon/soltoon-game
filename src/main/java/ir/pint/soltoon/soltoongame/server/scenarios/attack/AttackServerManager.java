package ir.pint.soltoon.soltoongame.server.scenarios.defence;

import ir.pint.soltoon.soltoongame.client.LocalClientManager;
import ir.pint.soltoon.soltoongame.server.ServerComminucation;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class DefenceServerManager extends ServerManager {
    private ManagerGameSoltoon defenceSoltoon;

    public DefenceServerManager(ServerComminucation server) {
        this.players = 2;
        this.localPlayers = 1;
        this.rounds = 500;
        this.server = server;
        this.width = 20;
        this.height = 15;
        this.gameBoard = new ManagerGame(this.height, this.width);
        this.playersInitialMoney = 10000;
        this.playersMoneyPerRound = 0;
    }

    @Override
    public void run() {
        addMapInfoToResult();
        initiateLocalClient();
        initiateExternalClients();
        startRounds();
        quitPlayers();
        Platform.exit(Platform.OK);
    }


    private void initiateLocalClient() {
        LocalClientManager warriorSoltoonManager = new LocalClientManager(DefenceSoltoon.class);
        long warriorSoltoonId = UUID.getLong();
        addLocalClientManager(warriorSoltoonId, warriorSoltoonManager);
        defenceSoltoon = initializeClient(warriorSoltoonId);
        defenceSoltoon.setWeight(-10);
    }

    @Override
    protected void addMapInfoToResult() {
        super.addMapInfoToResult();
        ResultStorage.putMisc("scenario", "defence");
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
