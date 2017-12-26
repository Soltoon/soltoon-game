package ir.pint.soltoon.soltoongame.shared.communication.command;

import ir.pint.soltoon.soltoongame.shared.actions.Action;

public class CommandAction extends Command{

    private Action action;
    public CommandAction(Long id, Action action) {
        super(id);
        this.action=action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
