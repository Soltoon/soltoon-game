package ir.pint.soltoon.soltoongame.server.manager;

import ir.pint.soltoon.soltoongame.shared.map.Cell;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.utils.shared.facades.json.SerializeAs;

import java.util.ArrayList;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */

@SerializeAs(Game.class)
public class ManagerGame extends Game {
    protected transient ArrayList<GameKhadang> recentlyKilledKhadangs = new ArrayList<>();


    public ManagerGame(int height, int width) {
        super(height, width);

        this.cells = new ManagerCell[width][height];

        int k = 0;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                this.cells[i][j] = new ManagerCell(k++, i, j, this);
    }

    public void setRound(int r) {
        this.currentRound = r;
    }

    public ArrayList<GameKhadang> getRecentlyKilledKhadangs() {
        return recentlyKilledKhadangs;
    }

    public void emptyRecentlyKilledKhadangs() {
        recentlyKilledKhadangs.clear();
    }

    public void removeKhadangFromMap(ManagerGameKhadang gameKhadang) {
        if (gameKhadang == null)
            return;

        GameKhadang khadang = khadangs.remove(gameKhadang.getId());

        // empty cell
        ((ManagerCell) khadang.getCell()).setKhadang(null);

        // remove khadang from from soltoon's khadangs
        ((ManagerGameSoltoon) gameKhadang.getOwner()).removeKhadang(gameKhadang);

        // add khadang to recently killed khadangs
        recentlyKilledKhadangs.add(gameKhadang);
    }

    public void removeKhadang(ManagerGameKhadang gameKhadang) {
        if (gameKhadang == null || !khadangs.containsKey(gameKhadang.getId()))
            return;

        khadangSoltoons.remove(gameKhadang.getId());
        gameKhadang.setGame(null);
    }

    public void addKhadangToMap(ManagerGameKhadang khadang, ManagerCell engineCell) {
        khadang.setGame(this);
        khadangs.put(khadang.getId(), khadang);
        ((ManagerGameSoltoon) khadang.getOwner()).addKhadang(khadang);
        engineCell.assignKhadang(khadang);
    }

    public void addSoltoon(ManagerGameSoltoon soltoon) {
        soltoon.setGame(this);
        soltoons.put(soltoon.getId(), soltoon);
    }

    public void moveKhadang(ManagerGameKhadang gameKhadang, ManagerCell cell) {
        Cell prevCell = gameKhadang.getCell();
        if (prevCell != null) {
            ((ManagerCell) prevCell).setKhadang(null);
        }
        cell.assignKhadang(gameKhadang);
    }


}
