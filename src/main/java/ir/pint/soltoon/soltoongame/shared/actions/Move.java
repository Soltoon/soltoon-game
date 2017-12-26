package ir.pint.soltoon.soltoongame.shared.actions;

import ir.pint.soltoon.soltoongame.server.manager.ManagerCell;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.map.Direction;
import ir.pint.soltoon.soltoongame.shared.result.AgentMoveEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public final class Move extends Action {
    private Direction direction;

    public Move(Direction direction) {
        this.direction = direction;
    }

    protected boolean execute(ManagerGame gameboard, ManagerGameKhadang khadang) {

        if (khadang == null) {
            return true; //age yevaght GameObject nabud
        }

        if (!((ManagerGameSoltoon) khadang.getOwner()).isMaster()) {
            if (khadang.getRemainingRestingTime() != 0) {
                return true;
            }
        }


        ManagerCell currentCell = (ManagerCell) khadang.getCell();
        ManagerCell newCell = (ManagerCell) gameboard.getCell(currentCell, direction);

        if (newCell == null) {
            return true;
        }

        if (newCell.getKhadang() != null) {
            return true;
        }

        gameboard.moveKhadang(khadang, newCell);

        khadang.resetRestingTime();

        ResultStorage.addEvent(new AgentMoveEvent(khadang.getId(), khadang.getOwner().getId(), currentCell.getX(), currentCell.getY(), newCell.getX(), newCell.getY()));

        return false;
    }

    public Direction getDirection() {
        return direction;
    }
}
