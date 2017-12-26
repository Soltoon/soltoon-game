package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.result.PlayerJoin;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.comminucation.ComServer;
import ir.pint.soltoon.utils.shared.comminucation.Comminucation;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Hashtable;

public class ServerComminucation {

    private int port;
    private ServerSocket serverSocket;
    private ComServer comServer;
    private HashMap<Long, Comminucation> clients = new HashMap<>();
    private Hashtable<ComRemoteInfo, Comminucation> comminucations = new Hashtable<>();

    ServerComminucation(ComServer comServer) {
        this.comServer = comServer;
    }

    public void connect() {
        try {
            if (!this.comServer.connectAll(true)) {
                System.err.println("It looks like there is a problem connecting with clients. \n" +
                        "* Check if your clients are running.\n" +
                        "* Check your configurations.\n");
                Platform.exit(Platform.CONNECTION_ERROR);
            }

        } catch (Exception e) {

        }

        this.comminucations = this.comServer.getComminucations();

        // assign id to clients
        try {

            for (ComRemoteInfo remoteInfo : this.comminucations.keySet()) {
                long client = UUID.getLong();
                clients.put(client, this.comminucations.get(remoteInfo));

                PlayerJoin playerJoin = new PlayerJoin(client, remoteInfo);
                ResultStorage.addEvent(playerJoin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Command query(Query query, long client, long timeout) {
        Comminucation comminucation = clients.get(client);
        if (comminucation != null)
            return query(query, comminucation, timeout);
        else
            return null;
    }

    public Command query(Query query, Comminucation clientCom, long timeout) {
        Object o = null;
        try {
            clientCom.getObjectOutputStream().writeObject(query);
            o = clientCom.getObjectInputStream().readObject(((int) timeout));
        } catch (IOException e) {
            ResultStorage.addException(e);
            return null;
        }

        if (!(o instanceof Command))
            return null;
        return ((Command) o);

    }

    public void sendResult(Result result, Command command, long client) {
        Comminucation comminucation = clients.get(client);
        if (comminucation != null)
            sendResult(result, command, comminucation);
    }

    public void sendResult(Result result, Command command, Comminucation clientCom) {
        try {
            clientCom.getObjectOutputStream().writeObject(result);
        } catch (IOException e) {

        }
    }

    public HashMap<Long, Comminucation> getClients() {
        return clients;
    }
}
