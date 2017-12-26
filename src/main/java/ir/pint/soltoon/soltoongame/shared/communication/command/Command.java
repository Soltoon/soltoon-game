package ir.pint.soltoon.soltoongame.shared.communication.command;

import ir.pint.soltoon.soltoongame.shared.agents.Agent;
import ir.pint.soltoon.soltoongame.shared.communication.Message;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Command extends Message {
    private transient Agent agent;
    public Command(Long id) {
        super(id);
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
