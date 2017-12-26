package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.client.LocalClientManager;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.server.filters.AgentFilter;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.actions.*;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandNothing;
import ir.pint.soltoon.soltoongame.shared.communication.query.*;
import ir.pint.soltoon.soltoongame.shared.communication.result.*;
import ir.pint.soltoon.soltoongame.shared.exceptions.ClientInitializationException;
import ir.pint.soltoon.soltoongame.shared.map.GameAwareElement;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.soltoongame.shared.result.PlayerJoin;
import ir.pint.soltoon.soltoongame.shared.result.RoundStartEvent;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCall;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.util.*;

public abstract class ServerManager {
    protected int rounds;
    protected int width, height;
    protected int players;

    protected ManagerGame gameBoard;
    protected ServerComminucation server;
    protected HashMap<Long, LocalClientManager> localClients = new HashMap<>();

    public abstract void run();

    protected void initiateExternalClients() {
        server.connect();

        Set<Long> clientIds = server.getClients().keySet();

        if (clientIds.size() != this.players) {
            System.err.printf("You must have at least %d connected clients.\n" +
                    "* Check your configurations.\n", this.players);
            Platform.exit(Platform.PLAYERS_COUNT_ERROR);
        }

        Iterator<Long> clients = clientIds.iterator();
        boolean initialization = true;
        long client;
        while (clients.hasNext()) {
            client = clients.next();

            initialization &= initializeClient(client) != null;
        }


        if (!initialization)
            Platform.exit(Platform.CLIENT_INITIALIZATION_ERROR);
    }

    protected ManagerGameSoltoon initializeClient(Long client) {
        return initializeClient(client, GameConfiguration.PLAYERS_INITIAL_SCORE, GameConfiguration.PLAYERS_TURN_MONERY, GameConfiguration.PLAYERS_INITIAL_MONEY);
    }

    protected ManagerGameSoltoon initializeClient(Long client, Integer initialScore, Integer turnMoney, Integer initialMoney) {
        QueryInitialize queryInitialize = new QueryInitialize(client, gameBoard);
        Command command = query(queryInitialize, client, GameConfiguration.INITIALIZE_TIMEOUT);

        if (command != null && command instanceof CommandInitialize) {
            ResultInitialize resultInitialize = new ResultInitialize(client, Status.SUCCESS);
            sendResult(resultInitialize, command, queryInitialize, client);
            return addClient(client, initialScore, turnMoney, initialMoney);
        } else {
            ResultInitialize resultInitialize = new ResultInitialize(client, Status.FAILURE);
            sendResult(resultInitialize, command, queryInitialize, client);

            ResultStorage.addException(new ClientInitializationException(client));

        }
        return null;
    }


    protected void addMapInfoToResult() {
        ResultStorage.putMisc("rounds", this.rounds);
        ResultStorage.putMisc("mapWidth", this.width);
        ResultStorage.putMisc("mapHeight", this.height);
        ResultStorage.putMisc("players", this.players);
    }

    protected ManagerGameSoltoon addClient(Long client, Integer initialScore, Integer turnMoney, Integer initialMoney) {
        ManagerGameSoltoon engineGameSoltoon = new ManagerGameSoltoon(client, initialScore, turnMoney, initialMoney);

        gameBoard.addSoltoon(engineGameSoltoon);
        return engineGameSoltoon;
    }

    protected void addLocalClientManager(Long client, LocalClientManager localClientManager) {
        localClients.put(client, localClientManager);
        PlayerJoin playerJoin = new PlayerJoin(client, new ComRemoteInfo("local", 0, ""));
        ResultStorage.addEvent(playerJoin);
    }

    protected Command query(Query query, Long client, long queryWaitTime) {
        if (localClients.containsKey(client))
            return localClients.get(client).handleQuery(query);
        return server.query(query, client, queryWaitTime);
    }

    protected void sendResult(Result result, Command command, Query query, Long client) {
        if (localClients.containsKey(client))
            localClients.get(client).handleResult(query, command, result);
        server.sendResult(result, command, client);
    }

    protected void updateSoltoons() {
        Map<Long, GameSoltoon> soltoons = gameBoard.getSoltoons();

        Map<Long, Integer> currentMoneyForResult = new HashMap<>();

        for (GameSoltoon soltoon : soltoons.values()) {
            ManagerGameSoltoon engineGameSoltoon = (ManagerGameSoltoon) soltoon;
            engineGameSoltoon.passTurn();
            currentMoneyForResult.put(engineGameSoltoon.getId(), engineGameSoltoon.getMoney());
        }
        ResultStorage.addEvent(new RoundStartEvent(gameBoard.getCurrentRound(), currentMoneyForResult));

    }

    protected void querySoltoons() {
        querySoltoons(null);
    }

    protected void querySoltoons(AgentFilter agentFilter) {
        if (agentFilter == null)
            agentFilter = AgentFilter.NULL_OBJECT;

        Map<Long, GameSoltoon> soltoons = gameBoard.getSoltoons();

        ArrayList<GameSoltoon> gameSoltoons = new ArrayList<>(soltoons.values());
        gameSoltoons.sort(new Comparator<GameSoltoon>() {
            @Override
            public int compare(GameSoltoon gameSoltoon, GameSoltoon t1) {
                return ((ManagerGameSoltoon) gameSoltoon).compareTo(((ManagerGameSoltoon) t1));
            }
        });

        LinkedList<GameSoltoon> soltoonsToQuery = new LinkedList<>(gameSoltoons);
        // query soltoons
        while (!soltoonsToQuery.isEmpty()) {
            GameSoltoon soltoon = soltoonsToQuery.pop();

            ManagerGameSoltoon engineGameSoltoon = (ManagerGameSoltoon) soltoon;
            if (!agentFilter.isQueryAllowed(engineGameSoltoon))
                continue;

            boolean skipPlayer = false;
            QueryAction queryAction = new QueryAction(soltoon.getId(), gameBoard);
            Command command = query(queryAction, soltoon.getId(), GameConfiguration.QUERY_WAIT_TIME);

            if (command == null || (command instanceof CommandAction && !agentFilter.isActionAllowed(engineGameSoltoon, ((CommandAction) command).getAction())))
                command = new CommandNothing();

            boolean commandSuccessful = false;
            Result result = null;

            if (command instanceof CommandAction) {
                Action action = ((CommandAction) command).getAction();
                if (action instanceof AddKhadang) {
                    Long uuid = UUID.getLong();
                    commandSuccessful = !PrivateCall.<Boolean>call(action, "execute", gameBoard, soltoon, uuid);
                    if (commandSuccessful)
                        result = ResultAddFighterAction.successful(soltoon.getId(), uuid);
                } else if (((CommandAction) command).getAction() instanceof Check) {
                    commandSuccessful = checkAction(command, soltoon);
                }

                skipPlayer = !commandSuccessful;
            } else {
                skipPlayer = true;
            }

            if (!commandSuccessful) {
//                System.out.println(command);
//                System.out.println(queryAction);
//                System.out.println(gameBoard);
                result = new ResultAction(soltoon.getId(), Status.FAILURE);
            }
            sendResult(result, command, queryAction, soltoon.getId());

            if (!skipPlayer)
                soltoonsToQuery.add(soltoon);
        }
    }

    private boolean checkAction(Command command, GameAwareElement gameElement) {
        return true;
    }

    protected void updateKhadangs() {
        Collection<GameKhadang> khadangsList = new ArrayList<GameKhadang>(gameBoard.getKhadangs().values());

        for (GameKhadang khadang : khadangsList) {
            ((ManagerGameKhadang) khadang).turnPassed();
        }
    }

    protected void queryKhadangs() {
        queryKhadangs(null);
    }

    protected void queryKhadangs(AgentFilter agentFilter) {
        Long khadangOwner = null;
        if (agentFilter == null)
            agentFilter = AgentFilter.NULL_OBJECT;

        Collection<GameKhadang> khadangsList = new ArrayList<GameKhadang>(gameBoard.getKhadangs().values());

        ArrayList<GameKhadang> recentlyKilledKhadangs = gameBoard.getRecentlyKilledKhadangs();
        for (GameKhadang khadang : khadangsList) {
            // skip killed ones
            if (recentlyKilledKhadangs.contains(khadang))
                continue;
            if (!agentFilter.isQueryAllowed(khadang))
                continue;

            khadangOwner = khadang.getOwner().getId();
            QueryAction queryAction = new QueryAction(khadang.getId(), gameBoard);
            Command command = query(queryAction, khadangOwner, GameConfiguration.QUERY_WAIT_TIME);

            if (command == null || (command instanceof CommandAction && !agentFilter.isActionAllowed(khadang, ((CommandAction) command).getAction())))
                command = new CommandNothing();

            boolean commandSuccessful = false;
            Result result = null;

            if (command instanceof CommandAction) {
                Action action = ((CommandAction) command).getAction();
                if (action instanceof Move) {
                    commandSuccessful = !PrivateCall.<Boolean>call(action, "execute", gameBoard, khadang);
                } else if (action instanceof Shoot) {
                    commandSuccessful = !PrivateCall.<Boolean>call(action, "execute", gameBoard, khadang);
                }
            }


            if (commandSuccessful) {
                result = new ResultAction(khadangOwner, Status.SUCCESS);
            } else {
                result = new ResultAction(khadangOwner, Status.FAILURE);
            }

            sendResult(result, command, queryAction, khadangOwner);
        }
    }

    protected void quitPlayers() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {
            QueryExit queryExit = new QueryExit(player, null);
            server.query(queryExit, player, GameConfiguration.EXIT_QUERY_TIME);
        }
    }

    protected void removeKilledKhadangs() {
        Long khadangOwner = null;
        for (GameKhadang khadang : gameBoard.getRecentlyKilledKhadangs()) {
            khadangOwner = khadang.getOwner().getId();
            QueryFinalize queryFinalize = new QueryFinalize(khadang.getId(), gameBoard);
            Command command = server.query(queryFinalize, khadangOwner, GameConfiguration.QUERY_WAIT_TIME);
            server.sendResult(new ResultFinalize(khadang.getId(), Status.SUCCESS), command, khadangOwner);
            gameBoard.removeKhadang(((ManagerGameKhadang) khadang));
        }
        gameBoard.emptyRecentlyKilledKhadangs();
    }

}
