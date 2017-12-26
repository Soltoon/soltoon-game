package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.communication.Message;
import ir.pint.soltoon.soltoongame.shared.map.Game;

import java.io.Serializable;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Query extends Message implements Serializable{

    protected Game gameBoard;
    public Query(Long id) {
        super(id);
    }

    public Game getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Game gameBoard) {
        this.gameBoard = gameBoard;
    }
}
