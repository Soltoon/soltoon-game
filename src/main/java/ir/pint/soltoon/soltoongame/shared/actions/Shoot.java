package ir.pint.soltoon.soltoongame.shared.actions;

import ir.pint.soltoon.soltoongame.server.manager.ManagerCell;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.result.AgentDamagedEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentDiedEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentShootEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public final class Shoot extends Action {
    private int x, y;

    public Shoot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected boolean execute(ManagerGame gb, ManagerGameKhadang khadang) {
        if (khadang == null)
            return true;

        KhadangType type = khadang.getType();

        ManagerCell target = (ManagerCell) gb.getCell(x, y);


        if (target == null)
            return true;

        if (target.getDistance(khadang.getCell()) > type.getShootingRange())
            return true;
        if (!((ManagerGameSoltoon) khadang.getOwner()).isMaster()) {
            if (khadang.getRemainingReloadingTime() > 0)
                return true;
        }
        khadang.resetReloadingTime();

        int fromX = khadang.getCell().getX();
        int fromY = khadang.getCell().getY();

        ResultStorage.addEvent(new AgentShootEvent(khadang.getId(), khadang.getOwner().getId(), fromX, fromY, this.x, this.y));


        ManagerGameKhadang targetKhadang = (ManagerGameKhadang) target.getKhadang();
        if (targetKhadang != null) {
            Integer damage = type.getShootingPower();

            if (targetKhadang.damageBy(damage)) {
                gb.removeKhadangFromMap(targetKhadang);


                ManagerGameSoltoon targetOwner = (ManagerGameSoltoon) targetKhadang.getOwner();
                targetOwner.changeScore(-targetKhadang.getType().getDeathPenalty());
                ResultStorage.addEvent(new AgentDiedEvent(targetKhadang.getId(), x, y, targetKhadang.getType().getDeathPenalty(), targetKhadang.getOwner().getId(), khadang.getOwner().getId()));

            }
            ResultStorage.addEvent(new AgentDamagedEvent(targetKhadang.getId(), targetKhadang.getOwner().getId(), khadang.getOwner().getId(), damage, khadang.getHealth(), target.getX(), target.getY()));

        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
