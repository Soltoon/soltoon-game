package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.map.Game;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public class QueryAction extends Query {
    public QueryAction(Long id, Game gameBoard) {
        super(id);
        this.gameBoard = gameBoard;
    }

}
