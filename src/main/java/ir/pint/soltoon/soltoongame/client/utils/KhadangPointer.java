package ir.pint.soltoon.soltoongame.client.utils;

import ir.pint.soltoon.soltoongame.client.facades.GameFacade;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;

public class KhadangPointer {
    private Long id;

    public KhadangPointer(Long id) {
        this.id = id;
    }

    public KhadangPointer(GameKhadang gameKhadang) {
        this.id = gameKhadang.getId();
    }

    public Long getId() {
        return id;
    }

    public GameKhadang getKhadang() {
        return GameFacade.getGame().getKhadang(id);
    }
}
