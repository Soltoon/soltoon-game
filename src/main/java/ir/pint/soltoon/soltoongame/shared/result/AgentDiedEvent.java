package ir.pint.soltoon.soltoongame.shared.result;

public class AgentDiedEvent extends Event {
    private long agent;
    private int x, y;
    private long player, killer;
    private int penalty;

    public AgentDiedEvent(long agent, int x, int y, int penalty, long player, long killer) {
        super(EventType.AGENT_DIE);
        this.agent = agent;
        this.x = x;
        this.y = y;
        this.player = player;
        this.killer = killer;
        this.penalty = penalty;
    }

    public long getAgent() {
        return agent;
    }

    public void setAgent(long agent) {
        this.agent = agent;
    }

    public long getPlayer() {
        return player;
    }

    public void setPlayer(long player) {
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

    public long getKiller() {
        return killer;
    }

    public void setKiller(long killer) {
        this.killer = killer;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}
