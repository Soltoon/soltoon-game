package ir.pint.soltoon.soltoongame.shared.result;

public class AgentShootEvent extends Event {
    private long agent;
    private long player;

    private int x, y;
    private int targetX, targetY;

    public AgentShootEvent(long agent, long player, int x, int y, int targetX, int targetY) {
        super(EventType.AGENT_SHOOT);
        this.agent = agent;
        this.player = player;
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
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

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
}
