package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public enum SuiFighterType {
    MUSKETEER("/musketeer.png", KhadangType.MUSKETEER), BOMBER("/bomber.png", KhadangType.CANNON), GIANT("/giant.png", KhadangType.GIANT), TOWER("/castle.png", KhadangType.CASTLE);

    SuiFighterType(String image, KhadangType fighterType) {
        this.image = image;
        this.fighterType = fighterType;
    }

    public final String image;
    public final KhadangType fighterType;

    public static SuiFighterType get(KhadangType agentType) {
        for (SuiFighterType fighterUI : values()) {
            if (fighterUI.fighterType == agentType)
                return fighterUI;
        }
        return null;
    }
}
