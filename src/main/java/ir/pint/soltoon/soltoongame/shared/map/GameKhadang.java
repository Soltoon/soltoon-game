package ir.pint.soltoon.soltoongame.shared.map;

// final
public abstract class GameKhadang extends GameAwareElement {

    protected final Long id;
    protected final Long ownerId;
    protected final KhadangType type;
    protected Integer cellId;

    protected Integer remainingRestingTime = 0;
    protected Integer remainingReloadingTime = 0;
    protected Integer health;

    private transient GameSoltoon owner;
    protected transient Cell cell;


    protected GameKhadang(Long id, Long ownerId, KhadangType fighterType) {
        this.id = id;
        this.ownerId = ownerId;

        this.type = fighterType;
        this.health = type.getHealth();
        this.remainingReloadingTime = type.getReloadingTime();
        this.remainingRestingTime = type.getRestingTime();
    }

    public Long getId() {
        return id;
    }

    public KhadangType getType() {
        return type;
    }

    public int getRemainingRestingTime() {
        return remainingRestingTime;
    }

    public int getRemainingReloadingTime() {
        return remainingReloadingTime;
    }

    public Cell getCell() {
        if (cell == null && game != null)
            cell = game.getCell(cellId);

        return cell;
    }

    public GameSoltoon getOwner() {
        if (owner == null && this.game != null)
            owner = game.getSoltoon(ownerId);

        return owner;
    }

    public int getHealth() {
        return health;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof GameKhadang)) return false;

        GameKhadang that = (GameKhadang) object;

        return id.equals(that.id);
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "id=" + id +
                ", cell=" + getCell() +
                ", cellId=" + cellId +
                ", type=" + type +
                ", health=" + health +
                ", remainingRestingTime=" + remainingRestingTime +
                ", remainingReloadingTime=" + remainingReloadingTime +
                '}';
    }


}
