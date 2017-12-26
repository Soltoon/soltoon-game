package ir.pint.soltoon.soltoongame.client.implementations;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.actions.Move;
import ir.pint.soltoon.soltoongame.shared.actions.Shoot;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class SoltoonSakht extends Soltoon {
    private static final double MUSKTEER_PERCENTAGE = 0.5;
    private static final double GIANT_PERCENTAGE = 0.2;
    private static final double CANNON_PERCENTAGE = 0.3;

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
                    } else {
//                        System.out.println("NULL@"+(i + kx)+":"+(j + ky));
                    }
                }
            }
        }

        for (int i = 0; i < mapEq.length; i++) {
//            System.out.println(Arrays.toString(mapEq[i]));
        }

        if (free == 0)
            return null;

        int x = -1, y = 0;

        int distancePow2 = Integer.MAX_VALUE;

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if (!mapEq[i][j]) {
                    int d2 = (i - mapWidth / 2) * (i - mapWidth / 2) + (j - mapHeight / 2) * (j - mapHeight / 2);
                    if (d2 < distancePow2) {
                        x = i;
                        y = j;
                        distancePow2 = d2;
                    }
                }
            }
        }

        if (x == -1)
            return null;

//        System.out.println(gb.getCell(x, y));

        if (soltoon.getMoney() > KhadangType.CASTLE.getCost() * 2 ||
                distancePow2 < 6 && soltoon.getMoney() > KhadangType.CASTLE.getCost()) {
            return new AddKhadang(
                    new KillNearestKhadang(KhadangType.CASTLE),
                    x, y
            );
        }

        double allKhadangs = soltoon.getKhadangs().size();
        int muskteers = getKhadangsCount(soltoon, KhadangType.MUSKETEER);
        int cannons = getKhadangsCount(soltoon, KhadangType.CANNON);
        int giants = getKhadangsCount(soltoon, KhadangType.GIANT);


        if (muskteers / allKhadangs < MUSKTEER_PERCENTAGE) {
            return new AddKhadang(
                    new FollowAndKillNearestKhadang(KhadangType.MUSKETEER),
                    x, y
            );
        } else if (giants / allKhadangs < GIANT_PERCENTAGE)
            return new AddKhadang(
                    new FollowAndKillNearestKhadang(KhadangType.MUSKETEER),
                    x, y
            );
        else
            return new AddKhadang(
                    new KillNearestKhadang(KhadangType.CANNON),
                    x, y
            );
    }

    private int getKhadangsCount(GameSoltoon soltoon, KhadangType type) {
        int count = 0;
        for (GameKhadang khadang : soltoon.getKhadangs()) {
            if (khadang.getType() == type)
                count++;
        }

        return count;
    }
}

class KillNearestKhadang extends Khadang {
    public KillNearestKhadang(KhadangType type) {
        super(type);
    }

    @Override
    public void init(Game gameBoard) {
    }

    @Override
    public void lastThingsToDo(Game gameBoard) {

    }

    protected GameKhadang getNearestKhadang(Game gb) {
        GameKhadang me = gb.getKhadang(getId());
        GameSoltoon owner = me.getOwner();
        Cell myCell = me.getCell();

        boolean[][] cells = new boolean[gb.getMapWidth()][gb.getMapHeight()];

        LinkedList<CellDistance> c = new LinkedList<>();
        c.add(new CellDistance(myCell, 0));
        GameKhadang selected = null;
        while (!c.isEmpty()) {
            CellDistance pop = c.pop();
            if (pop.distance > 0 && pop.cell.hasKhadang() && !pop.cell.getKhadang().getOwner().equals(owner)) {
                selected = pop.cell.getKhadang();
                break;
            }

            for (int i = 0; i < 4; i++) {
                Cell cell = gb.getCell(pop.cell, Direction.get(i));
                if (cell == null) {
                    continue;
                }
                if (!cells[cell.getX()][cell.getY()]) {
                    cells[cell.getX()][cell.getY()] = true;
                    c.add(new CellDistance(cell, myCell.getDistance(cell)));
                }
            }
        }
        return selected;
    }

    @Override
    public Action getAction(Game gb) {
        GameKhadang nearestKhadang = getNearestKhadang(gb);


        if (nearestKhadang == null) {
            return null;
        }

        Cell cell1 = nearestKhadang.getCell();
        return new Shoot(cell1.getX(), cell1.getY());
    }

    protected Direction bfs(Cell startPosition, Cell finalPosition, Game gb) {
        boolean[][] cells = new boolean[gb.getMapWidth()][gb.getMapHeight()];
        LinkedList<CellDirection> c = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            Cell cell2 = gb.getCell(startPosition, Direction.get(i));
            if (cell2 != null) {
                c.add(new CellDirection(cell2, Direction.get(i)));
                cells[cell2.getX()][cell2.getY()] = true;
            }
        }


        while (!c.isEmpty()) {
            CellDirection pop = c.pop();
            if (pop.cell.equals(finalPosition))
                return pop.direction;

            for (int i = 0; i < 4; i++) {
                Cell cell2 = gb.getCell(pop.cell, Direction.get(i));
                if (cell2 != null && !cells[cell2.getX()][cell2.getY()]) {
                    c.add(new CellDirection(cell2, Direction.get(i)));
                    cells[cell2.getX()][cell2.getY()] = true;
                }
            }
        }
        return null;
    }

    private static class CellDistance {
        public Cell cell;
        public Integer distance;

        public CellDistance(Cell cell, Integer distance) {
            this.cell = cell;
            this.distance = distance;
        }

        public CellDistance() {
        }
    }

    private static class CellDirection {
        public Cell cell;
        public Direction direction;

        public CellDirection(Cell cell, Direction direction) {
            this.cell = cell;
            this.direction = direction;
        }

        public CellDirection() {
        }
    }
}

class FollowAndKillNearestKhadang extends KillNearestKhadang {
    public FollowAndKillNearestKhadang(KhadangType type) {
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
        GameKhadang me = gb.getKhadang(getId());
        Cell cell = me.getCell();
        GameKhadang nearestKhadang = getNearestKhadang(gb);

        if (nearestKhadang == null) {
            return new Move(Direction.get(((int) (Math.random() * 4))));
        }

        if (nearestKhadang.getCell().getDistance(cell) > me.getType().getShootingRange()) {
            Direction bfs = bfs(me.getCell(), nearestKhadang.getCell(), gb);
            if (bfs != null) {

                return new Move(bfs);
            }
        }

        Cell cell1 = nearestKhadang.getCell();


        return new Shoot(cell1.getX(), cell1.getY());
    }
}