package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;

public interface SuiStep {
    void apply(SuiManager suiManager);

    void revert(SuiManager suiManager);

    boolean sleep();
}
