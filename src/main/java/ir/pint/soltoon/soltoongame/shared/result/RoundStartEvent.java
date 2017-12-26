package ir.pint.soltoon.soltoongame.shared.result;

import java.util.Map;

public class RoundStartEvent extends Event {
    private int round = 0;
    private Map<Long, Integer> moneyByPlayer;

    public RoundStartEvent(int round, Map<Long, Integer> moneyByPlayer) {
        super(EventType.ROUND_START);
        this.round = round;
        this.moneyByPlayer = moneyByPlayer;
    }

    public RoundStartEvent() {
        super(EventType.ROUND_START);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Map<Long, Integer> getMoneyByPlayer() {
        return moneyByPlayer;
    }

    public void setMoneyByPlayer(Map<Long, Integer> moneyByPlayer) {
        this.moneyByPlayer = moneyByPlayer;
    }
}
