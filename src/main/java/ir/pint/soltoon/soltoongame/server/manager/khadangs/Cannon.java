package ir.pint.soltoon.soltoongame.server.manager.khadangs;

import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class Cannon extends ManagerGameKhadang {
    public Cannon(Long id, Long ownerId) {
        super(id, ownerId, KhadangType.CANNON);
    }
}
