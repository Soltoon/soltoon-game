package ir.pint.soltoon.soltoongame.shared.map;

import java.util.LinkedList;

// Final
public class GameSoltoon extends GameAwareElement {
    private final Long id;
    protected Integer score;
    protected Integer moneyPerTurn;
    protected Integer money;

    protected LinkedList<Long> khadangsId = new LinkedList<>();

    protected transient Game game;
    protected transient LinkedList<GameKhadang> khadangs;

    protected GameSoltoon(Long id) {
        this.id = id;
    }

    protected GameSoltoon(Long id, int score) {
        this.id = id;
        this.score = score;
    }

    public Long getId() {
        return id;
    }


    public int getScore() {
        return score;
    }

    public LinkedList<GameKhadang> getKhadangs() {
        if (this.khadangs == null)
            prepareKhadangs();

        return this.khadangs;
    }

    private void prepareKhadangs() {
        if (this.game == null)
            return;

        this.khadangs = new LinkedList<>();
        for (Long id : this.khadangsId) {
            this.khadangs.add(game.getKhadang(id));
        }
    }

    @Override
    public String toString() {
        return "GameSoltoon{" +
                "id=" + id +
                ", score=" + score +
                ", khadangsId=" + khadangsId +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof GameSoltoon)) return false;

        GameSoltoon that = (GameSoltoon) object;

        return id.equals(that.id);
    }

    public int getMoney() {
        return money;
    }

    public int getMoneyPerTurn() {
        return moneyPerTurn;
    }

    @Override
    protected void setGame(Game game) {
        super.setGame(game);
        this.game = game;
    }
}
