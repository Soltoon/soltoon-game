package ir.pint.soltoon.soltoongame.shared.result;

public class AgentMoveEvent extends Event {
    private long agent;
    private long player;
    private int fromX, fromY;
    private int toX, toY;

    public AgentMoveEvent(long agent, long player, int fromX, int fromY, int toX, int toY) {
        super(EventType.AGENT_MOVE);
        this.agent = agent;
        this.player = player;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
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

    public int getFromX() {
        return fromX;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
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
}
