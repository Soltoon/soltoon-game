package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;

public class SuiActionDamage extends SuiAction {
    private Long id;
    private int damage;

    public SuiActionDamage(Long id, int damage) {
        this.id = id;
        this.damage = damage;
    }

    public SuiActionDamage(int x, int y, Long player, Long id, int damage) {
        super(x, y, player);
        this.id = id;
        this.damage = damage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void apply(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(id);
        fighter.setHp(fighter.getHp() - damage);
    }

    @Override
    public void revert(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(id);
        fighter.setHp(fighter.getHp() + damage);
    }
}
