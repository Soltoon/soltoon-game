package ir.pint.soltoon.soltoongame.server.manager.khadangs;

import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class Musketeer extends ManagerGameKhadang {
    public Musketeer(Long id, Long ownerId) {
        super(id, ownerId, KhadangType.MUSKETEER);
    }
}
