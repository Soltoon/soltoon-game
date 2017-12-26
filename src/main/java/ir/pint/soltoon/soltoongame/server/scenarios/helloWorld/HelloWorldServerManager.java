package ir.pint.soltoon.soltoongame.server.scenarios.helloWorld;

import ir.pint.soltoon.soltoongame.client.LocalClientManager;
import ir.pint.soltoon.soltoongame.server.filters.AgentFilter;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.ServerComminucation;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.Check;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.GameAwareElement;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

public class HelloWorldServerManager extends ServerManager {

    private ManagerGameSoltoon helloWorldSoltoon;
    private HelloWorldAgentFilter helloWorldAgentFilter = new HelloWorldAgentFilter();

    public HelloWorldServerManager(ServerComminucation server, int width, int height) {
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
        initiateLocalClient();
        initiateExternalClients();
        startRounds();
        quitPlayers();
        Platform.exit(Platform.OK);
    }

    private void initiateLocalClient() {
        LocalClientManager helloWorldClientManager = new LocalClientManager(HelloWorldSoltoon.class);
        long helloWorldSoltoonId = UUID.getLong();
        addLocalClientManager(helloWorldSoltoonId, helloWorldClientManager);
        helloWorldSoltoon = initializeClient(helloWorldSoltoonId);
        helloWorldSoltoon.setWeight(-100);
        helloWorldSoltoon.setMaster(true);
    }

    @Override
    protected void addMapInfoToResult() {
        super.addMapInfoToResult();

        ResultStorage.putMisc("scenario", "helloWorld");
        ResultStorage.putMisc("finalSceneOnly", true);
    }

    private void startRounds() {
        startRound(0);
        ResultStorage.addEvent(new GameEndedEvent());
    }


    private void startRound(int round) {
        gameBoard.setRound(round);

        querySoltoons(helloWorldAgentFilter);
    }

    private class HelloWorldAgentFilter implements AgentFilter {
        @Override
        public boolean isQueryAllowed(GameAwareElement agent) {
            return true;
        }

        @Override
        public boolean isActionAllowed(GameAwareElement agent, Action action) {
            return agent.equals(HelloWorldServerManager.this.helloWorldSoltoon) || action instanceof Check;
        }
    }
}
