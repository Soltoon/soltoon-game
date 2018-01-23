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
                return 200;
            case CANNON:
                return 4000;
            case GIANT:
                return 1000;
            case CASTLE:
                return 20000;
        }
        return null;
    }

    /*
     * @return The number of rounds the fighter cannot shoot after each shooting.
     */
    public Integer getReloadingTime() {
        switch (this) {
            case CANNON:
                return 0;
            case GIANT:
                return 2;
            case CASTLE:
                return 1;
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
                return 1;
            case CASTLE:
                return Integer.MAX_VALUE;
            case MUSKETEER:
                return 0;
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
                return 50;
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
                return 4;
            case GIANT:
                return 1;
            case CASTLE:
                return 5;
        }
        return 0;
    }

    public Integer getShootingPower() {
        switch (this) {
            case CASTLE:
                return 200;
            case MUSKETEER:
                return 50;
            case CANNON:
                return 100;
            case GIANT:
                return 500;
        }
        return 0;
    }

    public Integer getDeathPenalty() {
        return getCost() / 2;
    }

    public Integer getCreatePoint() {
        return getCost();
    }

    public static KhadangType getRandomType() {
        int i = (int) ((Math.random() - 1e-10) * values().length);
        return values()[i];
    }
}