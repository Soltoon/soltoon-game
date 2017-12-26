package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryExit;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.utils.clients.proxy.ProxyTimeLimit;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareBeanProxy;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.comminucation.Comminucation;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.io.IOException;

public class NetworkClientManager extends ClientManager {
    private Comminucation comminucation;

    public NetworkClientManager(Class<? extends Soltoon> player, Comminucation comminucation) {
        super(player);
        this.comminucation = comminucation;
    }

    public void run() {
        ProxyTimeLimit proxyTimeLimit = new ProxyTimeLimit();
        ClientInterface proxifiedClientExecutor = null;
        try {
            proxifiedClientExecutor = TimeAwareBeanProxy.createBean(client, ClientInterface.class, proxyTimeLimit);
        } catch (Exception e) {
            ResultStorage.addException(e);
            Platform.exit(Platform.FATAL_ERROR);
        }

        ComInputStream comInputStream = comminucation.getObjectInputStream();
        ComOutputStream comOutputStream = comminucation.getObjectOutputStream();

        int errors = 0;
        Query query = null;
        try {
            while (true) {
                try {
                    query = ((Query) comInputStream.readObject());
                    errors = 0;
                } catch (IOException e) {
                    ResultStorage.addException(e);
                    errors++;
                    if (errors > 15)
                        Platform.exit(Platform.CONNECTION_ERROR);
                    e.printStackTrace();
                }


                if (query == null)
                    continue;

                if (query instanceof QueryExit) {
                    Platform.exit(Platform.OK);
                }

                proxyTimeLimit.setTimeLimit(GameConfiguration.QUERY_TIME);
                proxyTimeLimit.setExtraTimeLimit(GameConfiguration.QUERY_EXTRA_TIME);
                Command command = handleQuery(query);
                comOutputStream.writeObject(command);


                Result commandResult = (Result) comInputStream.readObject();
                handleResult(query, command, commandResult);
            }
        } catch (Exception e) {
            ResultStorage.addException(e);
            Platform.exit(Platform.GENERAL_ERROR);
        }

    }
}
