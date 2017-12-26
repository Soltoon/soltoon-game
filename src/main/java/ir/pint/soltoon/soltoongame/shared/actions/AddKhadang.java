package ir.pint.soltoon.soltoongame.shared.actions;

import ir.pint.soltoon.soltoongame.server.ServerConfiguration;
import ir.pint.soltoon.soltoongame.server.manager.ManagerCell;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.result.AgentAddEvent;
import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCall;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

/**
 *
 */
public final class AddKhadang extends Action {
    private int x, y;
    private KhadangType type;
    private transient Khadang khadang;

    public AddKhadang(Khadang khadang, int x, int y) {
        this.khadang = khadang;
        this.x = x;
        this.y = y;
        this.type = khadang.getType();
    }

    protected boolean execute(ManagerGame gameBoard, ManagerGameSoltoon soltoon, Long id) {
        if (type == null) {
            return true;
        }

        ManagerCell cell = (ManagerCell) gameBoard.getCell(x, y);

        if (cell == null || cell.hasKhadang()) {
//            System.out.println("POR"+cell);
            return true; // por nabashe yevaght
        }

        if (!soltoon.isMaster()) {
            if (soltoon.getMoney() - type.getCost() < 0) {
//                System.out.println("BIPOOLI");
                return true; // pool bashe
            }

            boolean ok = true;
            for (GameKhadang khadang : gameBoard.getKhadangs().values()) {
                double coeff = khadang.getOwner().equals(soltoon) ? GameConfiguration.FRIEND_COEEF : 1;
                if (cell.getDistance(khadang.getCell()) <= khadang.getType().getShootingRange() * coeff) {
                    ok = false;
//                    System.out.println(khadang.getCell());
//                    System.out.println(cell.getDistance(khadang.getCell()));
                }
            }

            if (!ok) {
//                System.out.println("INRANGE");
//                System.out.println(cell);
                return true;
            }
        }

        soltoon.changeMoney(-type.getCost());
        soltoon.changeScore(type.getCreatePoint());

        GameKhadang khadang = PrivateCall.call(type, "getFactory", id, soltoon.getId());

        if (khadang == null) {
            throw new Error("Server Error!");
        }


        gameBoard.addKhadangToMap(((ManagerGameKhadang) khadang), cell);
        ResultStorage.addEvent(new AgentAddEvent(id, x, y, type, type.getHealth(), soltoon.getId(), type.getCreatePoint(), type.getCost()));
        return false;
    }

    public Khadang getKhadang() {
        return khadang;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "AddKhadang{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
