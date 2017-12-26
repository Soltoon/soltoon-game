package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;

public class SuiActionMove extends SuiAction {
    private int toX, toY;
    private Long fighter;

    public SuiActionMove() {
    }

    public SuiActionMove(int x, int y, Long fighter, Long player, int toX, int toY) {
        super(x, y, player);
        this.toX = toX;
        this.toY = toY;
        this.fighter = fighter;
    }

    public int getToX() {
        return toX;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public int getToY() {
        return toY;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }

    @Override
    public String toString() {
        return "SuiActionMove{" +
                "toX=" + toX +
                ", toY=" + toY +
                ", x=" + x +
                ", y=" + y +
                ", player=" + player +
                '}';
    }

    @Override
    public void apply(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(this.fighter);
        fighter.setBoardX(toX);
        fighter.setBoardY(toY);
        fighter.rebound();
    }

    @Override
    public void revert(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(this.fighter);
        fighter.setBoardX(x);
        fighter.setBoardY(y);
        fighter.rebound();
    }

    @Override
    public boolean sleep() {
        return true;
    }
}
