package ir.pint.soltoon.soltoongame.client.facades;

import ir.pint.soltoon.soltoongame.shared.map.Game;

public class GameFacade {
    private static Game game;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameFacade.game = game;
    }
}
