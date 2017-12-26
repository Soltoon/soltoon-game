package ir.pint.soltoon.soltoongame.server.scenarios.helloWorld;

import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class HelloWorldSoltoon extends Soltoon {

    private int fighters = 15;

    @Override
    public void init(Game gameBoard) {
//        fighters = (int) ((0.05 + Math.random() * 0.1) * gameBoard.getMapWidth() * gameBoard.getMapHeight());
    }

    @Override
    public void lastThingsToDo(Game gameBoard) {

    }

    @Override
    public Action getAction(Game gb) {
        AddKhadang addKhadang = new AddKhadang(new Khadang(KhadangType.MUSKETEER) {
            @Override
            public void init(Game gameBoard) {

            }

            @Override
            public void lastThingsToDo(Game gameBoard) {

            }

            @Override
            public Action getAction(Game gb) {
                return null;
            }
        }, (int) (gb.getMapWidth() * Math.random()), (int) (gb.getMapHeight() * Math.random()));
        return fighters-- > 0 ? addKhadang : null;
    }
}
