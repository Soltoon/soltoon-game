package ir.pint.soltoon.soltoongame.shared.map;

import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCallMethod;

public abstract class GameAwareElement {
    protected transient Game game;

    @PrivateCallMethod
    protected void setGame(Game game) {
        this.game = game;
    }
}
