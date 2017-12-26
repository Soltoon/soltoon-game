package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.communication.command.*;
import ir.pint.soltoon.soltoongame.shared.communication.query.*;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultAddFighterAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Status;
import ir.pint.soltoon.soltoongame.shared.agents.Agent;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.HashMap;
import java.util.Map;

public abstract class ClientManager {
    protected Map<Long, Agent> agents = new HashMap<>();
    protected Class<? extends Soltoon> soltoon;
    protected ClientInterface client = new Client();

    public ClientManager(Class<? extends Soltoon> soltoon) {
        this.soltoon = soltoon;
    }

    protected Command handleQuery(Query query) {
        Long id = query.getId();
        Agent agent = agents.getOrDefault(id, null);
        Command command = null;

        if (query instanceof QueryAction) {
            command = client.queryAction(((QueryAction) query), agent);
        } else if (query instanceof QueryInitialize) {
            try {
                Soltoon player = this.soltoon.newInstance();
                agent = player;
                player.setId(query.getId());
                addAgent(player, query.getId());
                client.initialize(player, ((QueryInitialize) query).getGameBoard());
                command = new CommandInitialize(query.getId());
                command.setAgent(agent);
            } catch (Exception e) {
                ResultStorage.addException(e);
                Platform.exit(Platform.FATAL_ERROR);
            }
        } else if (query instanceof QueryFinalize) {
            client.lastThingsToDo(((QueryFinalize) query).getGameBoard(), agent);
            removeAgent(id);
            command = new CommandFinalize(id);
        }

        if (command == null)
            command = new CommandNothing();

        return command;
    }

    protected void removeAgent(Long id) {
        agents.remove(id);
    }

    protected void addAgent(Agent agent, Long id) {
        agents.put(id, agent);
    }

    protected void handleResult(Query query, Command command, Result commandResult) {
        if (command instanceof CommandAction && ((CommandAction) command).getAction() instanceof AddKhadang) {
            if (commandResult instanceof ResultAddFighterAction && commandResult.getStatus() == Status.SUCCESS) {
                Khadang khadang = ((AddKhadang) ((CommandAction) command).getAction()).getKhadang();
                addAgent(khadang, ((ResultAddFighterAction) commandResult).getFighterId());
                khadang.setId(((ResultAddFighterAction) commandResult).getFighterId());
                khadang.init(query.getGameBoard());
            }
        }

        if (command.getAgent() != null)
            client.recieveResults(commandResult, command, command.getAgent());

    }

}
