package ir.pint.soltoon.soltoongame.server.sync;

import ir.pint.soltoon.soltoongame.shared.result.AgentAddEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentDiedEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentMoveEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentShootEvent;
import ir.pint.soltoon.utils.shared.facades.result.EventLog;
import ir.pint.soltoon.utils.shared.facades.result.MetaLog;
import ir.pint.soltoon.utils.shared.facades.result.ResultHandler;

public class JavadTypeResultHandler implements ResultHandler {
    private String result = "";

    private long player1 = Long.MIN_VALUE;

    @Override
    public void addEvent(EventLog eventLog) {

        if (eventLog instanceof AgentAddEvent) {
            player1 = player1 == Long.MIN_VALUE ? Long.MIN_VALUE : ((AgentAddEvent) eventLog).getAgent();
        }

        synchronized (result) {
            if (eventLog instanceof AgentAddEvent) {
                String appearType = ((AgentAddEvent) eventLog).getAgentType().toString();
                result = result + String.format("%d-appear-%d,%d-%s-%d\n",
                        ((AgentAddEvent) eventLog).getAgent(),
                        ((AgentAddEvent) eventLog).getX() + 1,
                        ((AgentAddEvent) eventLog).getY() + 1,
                        appearType,
                        ((AgentAddEvent) eventLog).getPlayer() == player1 ? 1 : 2
                );
            } else if (eventLog instanceof AgentMoveEvent) {
                result = result + String.format("%d-move-%d,%d\n",
                        ((AgentMoveEvent) eventLog).getAgent(),
                        ((AgentMoveEvent) eventLog).getToX() + 1,
                        ((AgentMoveEvent) eventLog).getToY() + 1
                );
            } else if (eventLog instanceof AgentShootEvent) {
                result = result + String.format("%d-shoot-%d,%d\n",
                        ((AgentShootEvent) eventLog).getAgent(),
                        ((AgentShootEvent) eventLog).getTargetX() + 1,
                        ((AgentShootEvent) eventLog).getTargetY() + 1
                );
            } else if (eventLog instanceof AgentDiedEvent) {
                result = result + String.format("%d-killed\n",
                        ((AgentDiedEvent) eventLog).getAgent()
                );
                result = result + String.format("%d-penalty-%d\n",
                        ((AgentDiedEvent) eventLog).getPlayer(),
                        ((AgentDiedEvent) eventLog).getPenalty()
                );
            }
        }

    }

    @Override
    public void addMeta(MetaLog metaLog) {

    }

    @Override
    public void putMisc(String s, Object o) {

    }

    @Override
    public void addException(Exception e) {

    }

    @Override
    public boolean flush() {
//        System.out.println("\n\n** JAVAD RESULT **\n\n" + result + "\n\n");
        return true;
    }
}
