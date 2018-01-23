package ir.pint.soltoon.soltoongame.client.utils;

import ir.pint.soltoon.soltoongame.client.facades.GameFacade;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;

public class SoltoonPointer {
    private Long id;

    public SoltoonPointer(Long id) {
        this.id = id;
    }

    public SoltoonPointer(GameSoltoon soltoon) {
        this.id = soltoon.getId();
    }

    public Long getId() {
        return id;
    }

    public GameSoltoon getSoltoon(){
        return GameFacade.getGame().getSoltoon(id);
    }
}
