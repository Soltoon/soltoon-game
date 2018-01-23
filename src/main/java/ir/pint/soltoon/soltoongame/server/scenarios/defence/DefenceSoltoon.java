package ir.pint.soltoon.soltoongame.server.scenarios.defence;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by amirkasra on 1/12/2018 AD.
 */
public class DefenceSoltoon extends Soltoon {
    int wave = 1, count = 0;

    @Override
    public void init(Game game) {
    }

    @Override
    public void lastThingsToDo(Game game) {
    }

    @Override
    public Action getAction(Game game) {

        boolean clear = true;
        for (GameKhadang k : game.getKhadangs().values())
            if (k.getOwner().getId().equals(this.getId()))
                clear = false;

        if (count == wave && !clear) return null;
        if (count == wave) {
            count = 0;
            wave *= 2;
        }

        ArrayList<Cell> emptyCells = getEmptyCells(game);
        if (emptyCells.size() == 0) return null;

        Random r = new Random();
        count++;
        int index = Math.abs(r.nextInt()) % emptyCells.size();
        Cell c = emptyCells.get(index);
        return new AddKhadang(new DefenceKhadang((count % 2 == 0) ? KhadangType.GIANT : KhadangType.MUSKETEER),
                c.getX(), c.getY());
    }

    private ArrayList<Cell> getEmptyCells(Game game) {
        int h = game.getMapHeight(), w = game.getMapWidth();
        ArrayList<Cell> ans = new ArrayList();
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                boolean ok = true;
                Cell c = game.getCell(i, j);
                if (c.getKhadang() != null) continue;
                for (GameKhadang k : game.getKhadangs().values())
                    if (k.getOwner().getId() != this.getId())
                        if (c.getDistance(k.getCell()) <= k.getType().getShootingRange()) {
                            ok = false;
                            break;
                        }
                if (ok) ans.add(c);
            }
        return ans;
    }
}