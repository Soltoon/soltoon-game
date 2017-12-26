package ir.pint.soltoon.soltoongame.shared.communication.result;

import java.util.HashMap;

public class ResultAddFighterAction extends ResultAction {
    private long fighterId;

    private ResultAddFighterAction(Long id, Status status, HashMap data) {
        super(id, status, data);
    }

    public ResultAddFighterAction(Long id, Status status, long fighterId) {
        super(id, status);
        this.fighterId = fighterId;
    }

    public static ResultAddFighterAction successful(Long player, Long fighterId) {
        ResultAddFighterAction resultAddFighterAction = new ResultAddFighterAction(player, Status.SUCCESS, fighterId);
        return resultAddFighterAction;
    }

    public long getFighterId() {
        return fighterId;
    }

    public void setFighterId(long fighterId) {
        this.fighterId = fighterId;
    }
}
