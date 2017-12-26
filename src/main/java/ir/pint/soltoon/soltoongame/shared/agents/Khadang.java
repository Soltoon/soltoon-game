package ir.pint.soltoon.soltoongame.shared.agents;

import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public abstract class Khadang extends Agent {
    private KhadangType type;

    public Khadang(KhadangType type) {
        this.type = type;
    }

    public KhadangType getType() {
        return type;
    }
}
