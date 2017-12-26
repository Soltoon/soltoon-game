package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.map.Game;

/**
 * Created by amirkasra on 10/1/2017 AD.
 */
public class QueryFinalize extends Query {

    public QueryFinalize(Long id, Game game) {
        super(id);
        this.gameBoard = game;
    }

    public Game getGameBoard() {
        return gameBoard;
    }
}
