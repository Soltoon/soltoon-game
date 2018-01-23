package ir.pint.soltoon.soltoongame.server.scenarios.defence;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.Move;
import ir.pint.soltoon.soltoongame.shared.actions.Shoot;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.map.*;

/**
 * Created by amirkasra on 12/31/2017 AD.
 */
public class DefenceKhadang extends Khadang {
    GameKhadang me;
    int h, w;

    public DefenceKhadang(KhadangType type) {
        super(type);
    }

    @Override
    public void init(Game game) {

    }

    @Override
    public void lastThingsToDo(Game game) {

    }

    @Override
    public Action getAction(Game game) {
        me = game.getKhadang(getId());
        h = game.getMapHeight();
        w = game.getMapWidth();
        GameKhadang e = closestEnemy(game);
        if (e == null) return null;
        int dis = e.getCell().getDistance(me.getCell());
        if (dis <= me.getType().getShootingRange() && me.getRemainingReloadingTime() == 0)
            return new Shoot(e.getCell().getX(), e.getCell().getY());
        for (int i = 0; i < 4; i++) {
            Cell c = game.getCell(me.getCell(), Direction.get(i));
            if (c == null || c.getKhadang() != null) continue;
            if (c.getDistance(e.getCell()) < dis) return new Move(Direction.get(i));
        }
        return null;
    }

    private GameKhadang closestEnemy(Game game) {
        GameKhadang ans = null;
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                GameKhadang x = game.getCell(i, j).getKhadang();
                if (x != null && x.getOwner() != me.getOwner()) {
                    if (ans == null) ans = x;
                    else if (ans.getCell().getDistance(me.getCell()) > x.getCell().getDistance(me.getCell()))
                        ans = x;
                }
            }
        return ans;
    }
}