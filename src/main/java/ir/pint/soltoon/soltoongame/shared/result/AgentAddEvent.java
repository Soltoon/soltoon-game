package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class AgentAddEvent extends Event {
    private long agent;
    private KhadangType agentType;
    private int hp;
    private int x, y;
    private long player;
    private int score;
    private int cost;

    public AgentAddEvent(long agent, int x, int y, KhadangType agentType, int hp, long player, int score, int cost) {
        super(EventType.AGENT_ADD);
        this.agent = agent;
        this.x = x;
        this.y = y;
        this.agentType = agentType;
        this.player = player;
        this.hp = hp;
        this.score = score;
        this.cost = cost;
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

    public KhadangType getAgentType() {
        return agentType;
    }

    public void setAgentType(KhadangType agentType) {
        this.agentType = agentType;
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

    public int getHp() {
        return hp;
    }

    public int getScore() {
        return score;
    }

    public int getCost() {
        return cost;
    }
}
