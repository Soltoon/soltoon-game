package ir.pint.soltoon.soltoongame.server.manager.khadangs;


import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class Giant extends ManagerGameKhadang {
    public Giant(Long id, Long ownerId) {
        super(id, ownerId, KhadangType.GIANT);

    }
}
