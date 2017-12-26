package ir.pint.soltoon.soltoongame.shared.actions;

import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;

public final class Nothing extends Action {
    protected boolean execute(ManagerGame gb) {
        return false;
    }
}
