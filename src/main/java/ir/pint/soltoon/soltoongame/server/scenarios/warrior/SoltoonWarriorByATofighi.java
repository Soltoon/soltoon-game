package ir.pint.soltoon.soltoongame.server.scenarios.warrior;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.*;

import java.util.ArrayList;
import java.util.List;

public class SoltoonWarriorByATofighi extends Soltoon {

    @Override
    public void init(Game game) {

    }

    @Override
    public void lastThingsToDo(Game game) {

    }

    @Override
    public Action getAction(Game game) {
        GameSoltoon currentSoltoon = game.getSoltoon(this.getId());

        if (currentSoltoon.getKhadangs().size() != 0 ||
                KhadangType.MUSKETEER.getCost() > currentSoltoon.getMoney()) {
            return null;
        }

        List<Cell> cells = getFreeCells(game);
        if (cells.size() == 0)
            return null;

        Cell randomCell = cells.get((int) (Math.random() * cells.size()));
        return new AddKhadang(new Khadang(KhadangType.MUSKETEER) {
            @Override
            public void init(Game game) {

            }

            @Override
            public void lastThingsToDo(Game game) {

            }

            @Override
            public Action getAction(Game game) {
                return null;
            }

        }, randomCell.getX(), randomCell.getY());
    }

    private List<Cell> getFreeCells(Game game) {
        int w = game.getMapWidth(), h = game.getMapHeight();
        boolean[][] free = new boolean[w][h];
        for(int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                free[i][j] = true;
            }
        }

        for (GameKhadang khadang : game.getKhadangs().values()) {
            int x = khadang.getCell().getX();
            int y = khadang.getCell().getY();
            int shootingRange = khadang.getType().getShootingRange();

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (khadang.getCell().getDistance(game.getCell(i, j)) <= shootingRange)
                        free[i][j] = false;
                }
            }
        }

        List<Cell> result = new ArrayList<>(0);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (free[i][j]) {
                    result.add(game.getCell(i, j));
                }
            }
        }
        return result;
    }
}