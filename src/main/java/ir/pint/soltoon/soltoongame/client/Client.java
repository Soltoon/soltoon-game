package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.agents.Agent;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.utils.clients.proxy.DefaultTimeAwareBean;

public class Client extends DefaultTimeAwareBean implements ClientInterface {
    @Override
    public CommandAction queryAction(QueryAction query, Agent agent) {
        if (agent == null)
            return null;

        agent.setParentBean(this);
        agent.setId(query.getId());
        CommandAction command = null;
        Game gameBoard = ((QueryAction) query).getGameBoard();
        Action action = null;
        try {
            action = agent.getAction(gameBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        command = new CommandAction(query.getId(), action);
        command.setAgent(agent);
        return command;
    }

    @Override
    public void initialize(Soltoon player, Game gameBoard) {
        player.setParentBean(this);
        player.init(gameBoard);
    }

    @Override
    public void recieveResults(Result result, Command command, Agent agent) {
        agent.setParentBean(this);
        agent.recieveResults(result, command);
    }

    @Override
    public void lastThingsToDo(Game gameBoard, Agent agent) {
        agent.setParentBean(this);
        agent.lastThingsToDo(gameBoard);
    }
}
