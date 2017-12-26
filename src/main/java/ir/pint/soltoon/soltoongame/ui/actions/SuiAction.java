package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;

public abstract class SuiAction implements SuiStep {
    protected int x, y;
    protected Long player;


    public SuiAction() {
    }

    public SuiAction(int x, int y, Long player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Long getPlayer() {
        return player;
    }

    public void setPlayer(Long player) {
        this.player = player;
    }

    public boolean sleep(){
        return false;
    }
}
