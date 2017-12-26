package ir.pint.soltoon.soltoongame.server.manager;

import ir.pint.soltoon.soltoongame.shared.map.Cell;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.utils.shared.facades.json.SerializeAs;

@SerializeAs(Cell.class)
public class ManagerCell extends Cell {
    public ManagerCell(int id, int x, int y, Game game) {
        super(x, y);
        setGame(game);
        this.id = id;
    }

    public void setKhadang(GameKhadang khadang) {
        if (khadang == null) {
            this.khadangId = -1L;
            this.khadang = null;
        } else {
            this.khadangId = khadang.getId();
            this.khadang = khadang;
        }
    }


    public void assignKhadang(ManagerGameKhadang khadang) {
        setKhadang(khadang);
        khadang.setCell(this);
    }


    @Override
    public void setGame(Game game) {
        super.setGame(game);
    }
}
