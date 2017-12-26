package ir.pint.soltoon.soltoongame.shared.map;


import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCall;

// FINAL
public class Cell extends GameAwareElement {

    protected Integer id;
    protected Integer x, y;
    protected Boolean blocked = false;
    protected Long khadangId = -1L;


    protected transient GameKhadang khadang;


    protected Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getId() {
        return id;
    }

    private void link() {
        if (khadang != null) {
            PrivateCall.call(khadang, "setCell", this);
        }
    }

    public GameKhadang getKhadang() {
        if (khadang == null && this.game != null && khadangId > 0)
            khadang = this.game.getKhadang(khadangId);
        return khadang;
    }

    private void setKhadang(GameKhadang khadang) {
        this.khadang = khadang;
        if (khadang != null)
            this.khadangId = khadang.getId();
        else
            this.khadangId = -1L;
    }

    public boolean hasKhadang() {
        return khadangId >= 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public int getDistance(Cell destination) {
        if (destination == null)
            return Integer.MAX_VALUE;

        int difX = this.getX() - destination.getX();
        int difY = this.getY() - destination.getY();
        return Math.abs(difX) + Math.abs(difY);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Cell)) return false;

        Cell cell = (Cell) object;

        if (!x.equals(cell.x)) return false;
        return y.equals(cell.y);
    }


    @Override
    public String toString() {
        return "Cell{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", blocked=" + blocked +
                ", khadangId=" + khadangId +
                '}';
    }
}

