package ir.pint.soltoon.soltoongame.shared.map;


import ir.pint.soltoon.soltoongame.server.manager.khadangs.Cannon;
import ir.pint.soltoon.soltoongame.server.manager.khadangs.Castle;
import ir.pint.soltoon.soltoongame.server.manager.khadangs.Giant;
import ir.pint.soltoon.soltoongame.server.manager.khadangs.Musketeer;
import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCallMethod;

/**
 * This Enum helps you get information about different fighter types.
 *
 * @author Payam Mohammadi
 * @since 1.0.0
 */

// final
public enum KhadangType {
    MUSKETEER,
    CANNON,
    GIANT,
    CASTLE;

    /**
     * https://en.wikipedia.org/wiki/Health_(gaming)
     *
     * @return Initial HP
     */
    public Integer getHealth() {
        switch (this) {
            case MUSKETEER:
                return 100;
            case CANNON:
                return 2000;
            case GIANT:
                return 500;
            case CASTLE:
                return 10000;
        }
        return null;
    }

    /*
     * @return The number of rounds the fighter cannot shoot after each shooting.
     */
    public Integer getReloadingTime() {
        switch (this) {
            case CANNON:
                return 2;
            case GIANT:
                return 4;
            case CASTLE:
                return 2;
            case MUSKETEER:
                return 0;
        }
        return null;
    }

    /**
     * @return The number of rounds the fighter cannot move after each moving.
     */
    public Integer getRestingTime() {
        switch (this) {
            case CANNON:
                return Integer.MAX_VALUE;
            case GIANT:
                return 2;
            case CASTLE:
                return Integer.MAX_VALUE;
            case MUSKETEER:
                return 1;
        }
        return null;
    }

    @PrivateCallMethod
    private GameKhadang getFactory(Long id, Long owner) {

        switch (this) {
            case MUSKETEER:
                return new Musketeer(id, owner);
            case CASTLE:
                return new Castle(id, owner);
            case CANNON:
                return new Cannon(id, owner);
            case GIANT:
                return new Giant(id, owner);
        }
        return null;
    }

    public Integer getCost() {
        switch (this) {
            case CANNON:
                return 100;
            case GIANT:
                return 300;
            case CASTLE:
                return 1000;
            case MUSKETEER:
                return 10;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Distance between two cells is Diff in x-axis + Diff in y-axis.
     *
     * @return Maximum distance of the target
     */
    public Integer getShootingRange() {
        switch (this) {
            case MUSKETEER:
                return 3;
            case CANNON:
                return 5;
            case GIANT:
                return 1;
            case CASTLE:
                return 7;
        }
        return 0;
    }

    public Integer getShootingPower() {
        switch (this) {
            case CASTLE:
                return 75;
            case MUSKETEER:
                return 20;
            case CANNON:
                return 50;
            case GIANT:
                return 250;
        }
        return 0;
    }

    public Integer getDeathPenalty() {
        return getCost() / 4;
    }

    public Integer getCreatePoint() {
        return getCost() / 2;
    }

    public static KhadangType getRandomType() {
        int i = (int) ((Math.random() - 1e-10) * values().length);
        return values()[i];
    }
}
