package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;

public class SuiActionShoot extends SuiAction {
    private int toX, toY;
    private boolean died = false;

    public SuiActionShoot() {
    }

    public SuiActionShoot(int x, int y, Long player, int toX, int toY) {
        super(x, y, player);
        this.toX = toX;
        this.toY = toY;
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

    public boolean isDied() {
        return died;
    }

    public void setDied(boolean died) {
        this.died = died;
    }

    @Override
    public String toString() {
        return "SuiActionShoot{" +
                "toX=" + toX +
                ", toY=" + toY +
                ", died=" + died +
                ", x=" + x +
                ", y=" + y +
                ", player=" + player +
                '}';
    }

    @Override
    public void apply(SuiManager suiManager) {

    }

    @Override
    public void revert(SuiManager suiManager) {

    }

    @Override
    public boolean sleep() {
        return true;
    }
}
