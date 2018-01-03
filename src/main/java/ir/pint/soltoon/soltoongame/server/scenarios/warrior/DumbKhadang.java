package ir.pint.soltoon.soltoongame.server.scenarios.warrior;

import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

/**
 * Created by amirkasra on 12/31/2017 AD.
 */
public class DumbKhadang extends Khadang {
	public DumbKhadang(KhadangType type) {
		super(type);
	}

	@Override
	public void init(Game game) {

	}

	@Override
	public void lastThingsToDo(Game game) {

	}

	@Override
	public Action getAction(Game game) {
		return null;
	}
}
