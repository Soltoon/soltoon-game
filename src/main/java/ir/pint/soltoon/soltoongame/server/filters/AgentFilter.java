package ir.pint.soltoon.soltoongame.server.filters;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.agents.Agent;
import ir.pint.soltoon.soltoongame.shared.map.GameAwareElement;

public interface AgentFilter {
    AgentFilter NULL_OBJECT = new AgentFilter() {
        @Override
        public boolean isQueryAllowed(GameAwareElement agent) {
            return true;
        }

        @Override
        public boolean isActionAllowed(GameAwareElement agent, Action action) {
            return true;
        }
    };

    boolean isQueryAllowed(GameAwareElement agent);

    boolean isActionAllowed(GameAwareElement agent, Action action);
}
