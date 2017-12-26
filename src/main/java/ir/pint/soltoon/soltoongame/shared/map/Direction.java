package ir.pint.soltoon.soltoongame.shared.map;

public enum Direction {
    RIGHT, LEFT, UP, DOWN;


    public int xDifference() {
        switch (this) {
            case DOWN:
                return 0;
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            case UP:
                return 0;
        }
        return 0;
    }

    public int yDifference() {
        switch (this) {
            case DOWN:
                return 1;
            case LEFT:
                return 0;
            case RIGHT:
                return 0;
            case UP:
                return -1;
        }
        return 0;
    }

    public static Direction get(int i) {
        int index = i % values().length;
        return values()[index];
    }

}
