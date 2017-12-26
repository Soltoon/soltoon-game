package ir.pint.soltoon.soltoongame.client.implementations;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.actions.Move;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.*;

import java.util.Iterator;

public class SoltoonSibl extends Soltoon {

    @Override
    public void init(Game gameBoard) {

    }

    @Override
    public void lastThingsToDo(Game gameBoard) {

    }

    @Override
    public Action getAction(Game gb) {
        GameSoltoon soltoon = gb.getSoltoon(getId());

        int mapWidth = gb.getMapWidth();
        int mapHeight = gb.getMapHeight();

        boolean[][] mapEq = new boolean[mapWidth][mapHeight];
        int free = mapWidth * mapHeight;

        Iterator<GameKhadang> khadangIterator = gb.getKhadangs().values().iterator();
        while (khadangIterator.hasNext()) {
            GameKhadang khadang = khadangIterator.next();

            int kx = khadang.getCell().getX();
            int ky = khadang.getCell().getY();

            Integer range = khadang.getType().getShootingRange();
            for (int i = -range; i <= range; i++) {
                int jMax = range - Math.abs(i);
                for (int j = -jMax; j <= jMax; j++) {
                    if (gb.getCell(i + kx, j + ky) != null) {
                        if (!mapEq[i + kx][j + ky])
                            free--;

                        mapEq[i + kx][j + ky] = true;
                    }
                }
            }
        }

        int randomFree = ((int) (free * Math.random()));

        int x = 0, y = 0;

        SearchRandom:
        for (int i = 0, k = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if (!mapEq[i][j]) {
                    if (k == randomFree) {
                        x = i;
                        y = j;
                        break SearchRandom;
                    } else {
                        k++;
                    }
                }
            }
        }

        KhadangType t;
        if (soltoon.getMoney() >= KhadangType.CASTLE.getCost()) {
            t = KhadangType.CASTLE;
        } else if (soltoon.getMoney() >= KhadangType.GIANT.getCost()) {
            t = KhadangType.GIANT;
        } else {
            t = KhadangType.MUSKETEER;
        }

        return new AddKhadang(new GijKhadang(t), x, y);
    }

    private class GijKhadang extends Khadang {
        public GijKhadang(KhadangType type) {
            super(type);
        }

        @Override
        public void init(Game gameBoard) {

        }

        @Override
        public void lastThingsToDo(Game gameBoard) {

        }

        @Override
        public Action getAction(Game gb) {
            return new Move(Direction.get(((int) (Math.random() * 4))));
        }
    }
}
