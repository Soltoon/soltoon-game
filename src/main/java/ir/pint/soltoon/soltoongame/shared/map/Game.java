package ir.pint.soltoon.soltoongame.shared.map;


import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCall;

import java.util.*;

/**
 * This class holds all information about the game.
 *
 * @author Payam Mohammadi
 * @since 1.0.0
 */

// Final
public class Game {

    protected int mapHeight, mapWidth;
    protected Integer currentRound;

    protected Map<Long, GameSoltoon> soltoons = new HashMap<>();
    protected Map<Long, GameKhadang> khadangs = new HashMap<>();
    protected Map<Long, Long> khadangSoltoons = new HashMap<>();

    protected Cell[][] cells;


    protected Game() {
    }


    private void afterDeserialize() {
        for (Cell[] row : cells) {
            if (row == null || row.length == 0)
                continue;

            for (Cell cell : row)
                PrivateCall.call(cell, "setGame", this);

        }

        for (GameSoltoon gameSoltoon : soltoons.values()) {
            PrivateCall.call(gameSoltoon, "setGame", this);
        }

        for (GameKhadang gameKhadang : khadangs.values())
            PrivateCall.call(gameKhadang, "setGame", this);
    }

    protected Game(int mapHeight, int mapWidth) {
        this();
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    /**
     * @return Map height.
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * @return Map width.
     */
    public int getMapWidth() {
        return mapWidth;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Cell getCell(Cell center, Direction direction) {

        int x = center.getX() + direction.xDifference(), y = center.getY() + direction.yDifference();

        return getCell(x, y);
    }


    public Cell getCell(Integer x, Integer y) {
        if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight)
            return cells[x][y];
        return null;
    }

    public Cell getCell(Integer cellId) {

        if (cellId == null)
            return null;

        int size = cells.length * cells[0].length;
        if (cellId < 0)
            cellId = size - ((cellId + 1) % (cells.length * cells[0].length) - 1);

        cellId = cellId % size;

        return cells[cellId / cells[0].length][cellId % cells[0].length];
    }

    public GameSoltoon getOwner(Long id) {
        Long soltoonId = khadangSoltoons.get(id);
        if (soltoonId == null || soltoonId < 0)
            return null;

        return soltoons.get(soltoonId);
    }

    public GameKhadang getKhadang(Long id) {
        return khadangs.get(id);
    }

    public Map<Long, GameKhadang> getKhadangs() {
        return khadangs;
    }

    public GameSoltoon getSoltoon(Long id) {
        return soltoons.get(id);
    }

    public Map<Long, GameSoltoon> getSoltoons() {
        return soltoons;
    }
}
