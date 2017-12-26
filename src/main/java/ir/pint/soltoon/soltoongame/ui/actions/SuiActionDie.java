package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;
import ir.pint.soltoon.soltoongame.ui.elements.SuiPlayer;

public class SuiActionDie extends SuiAction {
    private Long id;
    private int penalty;

    public SuiActionDie() {
    }

    public SuiActionDie(int x, int y, Long player) {
        super(x, y, player);
    }

    public SuiActionDie(long agent, long player, int penalty, int x, int y) {
        super(x, y, player);
        this.id = agent;
        this.penalty = penalty;
    }

    @Override
    public void apply(SuiManager suiManager) {

        SuiFighter fighter = suiManager.getFighter(id);

        if (fighter != null) {
            suiManager.getGameBoard().remove(fighter);
            SuiPlayer suiPlayer = suiManager.getPlayer(player);
            suiPlayer.addScore(-penalty);
        }
    }

    @Override
    public void revert(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(id);

        if (fighter != null) {
            suiManager.getGameBoard().add(fighter);
            SuiPlayer suiPlayer = suiManager.getPlayer(player);
            suiPlayer.addScore(penalty);
        }
    }

    @Override
    public boolean sleep() {
        return true;
    }
}
