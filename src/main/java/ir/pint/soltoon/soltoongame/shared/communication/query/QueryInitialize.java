package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.map.Game;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class QueryInitialize extends Query {

    public QueryInitialize(Long id) {
        super(id);
    }

    public QueryInitialize(Long id, Game gameBoard) {
        super(id);
        this.gameBoard = gameBoard;
    }

    public Game getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Game gameBoard) {
        this.gameBoard = gameBoard;
    }
}
