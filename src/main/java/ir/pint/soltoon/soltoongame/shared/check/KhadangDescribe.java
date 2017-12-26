package ir.pint.soltoon.soltoongame.shared.check;

import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class KhadangDescribe {
    private final Long id;
    private final Long ownerId;
    private final Integer x, y;
    private final KhadangType khadangType;
    private final Long[] khadangsInRange;

    public KhadangDescribe(Long id, Long ownerId, Integer x, Integer y, KhadangType khadangType, Long[] khadangsInRange) {
        this.id = id;
        this.ownerId = ownerId;
        this.x = x;
        this.y = y;
        this.khadangType = khadangType;
        this.khadangsInRange = khadangsInRange;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public KhadangType getKhadangType() {
        return khadangType;
    }

    public Long[] getKhadangsInRange() {
        return khadangsInRange;
    }
}
