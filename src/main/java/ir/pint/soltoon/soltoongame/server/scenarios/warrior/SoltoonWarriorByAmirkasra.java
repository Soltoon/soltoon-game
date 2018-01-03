package ir.pint.soltoon.soltoongame.server.scenarios.warrior;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

import java.util.Random;

/**
 * Created by amirkasra on 12/31/2017 AD.
 */
public class SoltoonWarriorByAmirkasra extends Soltoon {

    @Override
    public void init(Game game) {

    }

    @Override
    public void lastThingsToDo(Game game) {

    }

    @Override
    public Action getAction(Game game) {
        GameSoltoon soltoon = game.getSoltoon(getId());
        int h = game.getMapHeight(), w = game.getMapWidth();
        if (soltoon.getKhadangs().size() < 1) {
            Random r = new Random();
            int x = r.nextInt() % w, y = r.nextInt() % h;
            return new AddKhadang(new DumbKhadang(KhadangType.MUSKETEER), x, y);
        }
        return null;
    }
}
