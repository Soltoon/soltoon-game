package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;

public class LocalClientManager extends ClientManager {
    public LocalClientManager(Class<? extends Soltoon> soltoon) {
        super(soltoon);
    }

    @Override
    public Command handleQuery(Query query) {
        return super.handleQuery(query);
    }

    @Override
    public void handleResult(Query query, Command command, Result commandResult) {
        super.handleResult(query, command, commandResult);
    }
}
