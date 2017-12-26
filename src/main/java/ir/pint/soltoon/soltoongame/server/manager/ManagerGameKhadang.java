package ir.pint.soltoon.soltoongame.server.manager;

import ir.pint.soltoon.soltoongame.shared.map.Cell;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.utils.shared.facades.json.SerializeAs;

@SerializeAs(GameKhadang.class)
public class ManagerGameKhadang extends GameKhadang {
    public ManagerGameKhadang(Long id, Long ownerId, KhadangType fighterType) {
        super(id, ownerId, fighterType);
    }

    public void resetRestingTime() {
        remainingReloadingTime = type.getRestingTime();
    }

    public void resetReloadingTime() {
        remainingReloadingTime = type.getReloadingTime();
    }

    public boolean damageBy(int amount) {
        if (amount < 0) return false;
        health -= amount;


        return health <= 0;
    }

    public void remainingReloadingTimeMM() {
        remainingReloadingTime--;
        if (remainingReloadingTime < 0) remainingReloadingTime = 0;
    }


    public void remainingRestingTimeMM() {
        if (remainingRestingTime != Integer.MAX_VALUE)
            remainingRestingTime--;
        if (remainingRestingTime < 0) remainingRestingTime = 0;
    }

    public void turnPassed() {
        remainingReloadingTimeMM();
        remainingRestingTimeMM();
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCell(Cell cell) {
        this.cellId = cell.getId();
        this.cell = cell;
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
    }
}
