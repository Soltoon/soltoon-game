package ir.pint.soltoon.soltoongame.server.scenarios.attack;

import ir.pint.soltoon.soltoongame.server.scenarios.defence.DefenceKhadang;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;

import java.util.ArrayList;

/**
 * Created by amirkasra on 1/12/2018 AD.
 */
public class Scenario5Soltoon extends Soltoon {
    ArrayList<ToBeAdded> todo;

	@Override
	public void init(Game game) {
		todo = new ArrayList<>();
		todo.add(new ToBeAdded(KhadangType.CASTLE,7,7));
		todo.add(new ToBeAdded(KhadangType.CANNON, 4,4));
		todo.add(new ToBeAdded(KhadangType.CANNON, 10,10));
		todo.add(new ToBeAdded(KhadangType.CANNON, 10,4));
		todo.add(new ToBeAdded(KhadangType.CANNON, 4,10));

	}

	@Override
	public void lastThingsToDo(Game game) {

	}

	@Override
	public Action getAction(Game game) {
		ToBeAdded a = todo.get(0);
		todo.remove(0);
		return new AddKhadang(new DefenceKhadang(a.type),a.x,a.y);
	}
	private static class ToBeAdded {
		public KhadangType type;
		public int x,y;
		public ToBeAdded(KhadangType type, int x,int y) {
			this.type = type;
			this.x = x;
			this.y = y;
		}
	}
}