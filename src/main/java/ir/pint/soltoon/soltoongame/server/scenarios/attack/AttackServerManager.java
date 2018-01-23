package ir.pint.soltoon.soltoongame.server.scenarios.attack;

import ir.pint.soltoon.soltoongame.client.LocalClientManager;
import ir.pint.soltoon.soltoongame.server.ServerComminucation;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.filters.AgentFilter;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.server.scenarios.defence.DefenceSoltoon;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.map.GameAwareElement;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class AttackServerManager extends ServerManager {
    private ManagerGameSoltoon attack;
    private AttackAgentFilter attackAgentFilter = new AttackAgentFilter();

    public AttackServerManager(ServerComminucation server) {
        this.players = 2;
        this.localPlayers = 1;
        this.rounds = GameConfiguration.ROUNDS;
        this.server = server;
        this.width = GameConfiguration.BOARD_WIDTH;
        this.height = GameConfiguration.BOARD_HEIGHT;
        this.gameBoard = new ManagerGame(this.height, this.width);
        this.playersInitialMoney = GameConfiguration.ATTACK_INITIAL_MONEY;
        this.playersMoneyPerRound = GameConfiguration.ATTACK_MONEY_PER_ROUND;
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
        LocalClientManager warriorSoltoonManager = new LocalClientManager(AttackSoltoon.class);
        long warriorSoltoonId = UUID.getLong();
        addLocalClientManager(warriorSoltoonId, warriorSoltoonManager);
        attack = initializeClient(warriorSoltoonId);
        attack.setWeight(-10);
        attack.setSingleStep(true);
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
        querySoltoons(attackAgentFilter);

        updateKhadangs();
        queryKhadangs(attackAgentFilter);

        removeKilledKhadangs();
    }


    private class AttackAgentFilter implements AgentFilter {
        @Override
        public boolean isQueryAllowed(GameAwareElement agent) {
            return true;
        }

        @Override
        public boolean isActionAllowed(GameAwareElement agent, Action action) {
            if (agent instanceof GameSoltoon
                    && !agent.equals(attack)
                    && action instanceof AddKhadang
                    && ((((AddKhadang) action).getType() == KhadangType.CANNON || ((AddKhadang) action).getType() == KhadangType.CASTLE)))
                return false;
            return true;
        }
    }
}
