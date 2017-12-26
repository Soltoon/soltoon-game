package ir.pint.soltoon.soltoongame.server.manager.khadangs;


import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

public class Castle extends ManagerGameKhadang {
    public Castle(Long id, Long ownerId) { super(id, ownerId, KhadangType.CASTLE);}
}
