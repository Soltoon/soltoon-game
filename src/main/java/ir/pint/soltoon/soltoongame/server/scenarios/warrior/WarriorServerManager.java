package ir.pint.soltoon.soltoongame.server.scenarios.warrior;

import ir.pint.soltoon.soltoongame.client.LocalClientManager;
import ir.pint.soltoon.soltoongame.server.ServerComminucation;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.filters.AgentFilter;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.server.scenarios.helloWorld.HelloWorldSoltoon;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.actions.Check;
import ir.pint.soltoon.soltoongame.shared.actions.Nothing;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.GameAwareElement;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

public class WarriorServerManager extends ServerManager {

    private ManagerGameSoltoon warriorSoltoon;
    private Class<? extends Soltoon> warriorSoltoonClass;
    private WarriorAgentFilter warriorAgentFilter = new WarriorAgentFilter();

    public WarriorServerManager(ServerComminucation server, Class<? extends Soltoon> warriorSoltoonClass) {
        this.players = 2;
        this.localPlayers = 1;
        this.rounds = 500;
        this.server = server;
        this.width = 20;
        this.height = 15;
        this.gameBoard = new ManagerGame(this.height, this.width);
        this.warriorSoltoonClass = warriorSoltoonClass;
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
        LocalClientManager warriorSoltoonManager = new LocalClientManager(warriorSoltoonClass);
        long warriorSoltoonId = UUID.getLong();
        addLocalClientManager(warriorSoltoonId, warriorSoltoonManager);
        warriorSoltoon = initializeClient(warriorSoltoonId);
        warriorSoltoon.setWeight(100);
        warriorSoltoon.setMaster(true);
    }

    @Override
    protected void addMapInfoToResult() {
        super.addMapInfoToResult();
        ResultStorage.putMisc("scenario", "warrior");
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
        querySoltoons(warriorAgentFilter);

        updateKhadangs();
        queryKhadangs(warriorAgentFilter);

        removeKilledKhadangs();
    }

    private class WarriorAgentFilter implements AgentFilter {
        @Override
        public boolean isQueryAllowed(GameAwareElement agent) {
            return true;
        }

        @Override
        public boolean isActionAllowed(GameAwareElement agent, Action action) {
            if (agent instanceof GameSoltoon
                    && (((GameSoltoon) agent).getKhadangs().size() > 0 || action instanceof AddKhadang && (((AddKhadang) action).getType() == KhadangType.CANNON || ((AddKhadang) action).getType() == KhadangType.CASTLE)))
                return false;
            return true;
        }
    }
}
