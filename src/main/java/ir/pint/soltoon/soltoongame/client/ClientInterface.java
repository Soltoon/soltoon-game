package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.agents.Agent;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareProxyInterface;

public interface ClientInterface extends TimeAwareProxyInterface {
    CommandAction queryAction(QueryAction query, Agent agent);

    void recieveResults(Result result, Command command, Agent agent);

    void initialize(Soltoon player, Game gameBoard);

    void lastThingsToDo(Game gameBoard, Agent agent);
}
