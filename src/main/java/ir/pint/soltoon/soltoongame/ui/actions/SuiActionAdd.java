package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;
import ir.pint.soltoon.soltoongame.ui.elements.SuiGameBoard;
import ir.pint.soltoon.soltoongame.ui.elements.SuiPlayer;

public class SuiActionAdd extends SuiAction {
    private Long id;
    private int score;
    private int cost;

    public SuiActionAdd() {
    }

    public SuiActionAdd(int x, int y, Long id, Long player, int score, int cost) {
        super(x, y, player);
        this.id = id;
        this.score = score;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void apply(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(id);
        SuiGameBoard gameBoard = suiManager.getGameBoard();
        fighter.setSuiManager(suiManager);
        fighter.rebound();
        gameBoard.add(fighter);

        SuiPlayer player = suiManager.getPlayer(this.player);
        if (player != null) {
            player.addScore(score);
            player.addMoney(-cost);
        }
    }

    @Override
    public void revert(SuiManager suiManager) {
        SuiGameBoard gameBoard = suiManager.getGameBoard();
        SuiFighter fighter = suiManager.getFighter(id);
        gameBoard.remove(fighter);
        SuiPlayer player = suiManager.getPlayer(this.player);
        if (player != null) {
            player.addScore(-score);
            player.addMoney(cost);
        }
    }

    @Override
    public boolean sleep() {
        return true;
    }
}
