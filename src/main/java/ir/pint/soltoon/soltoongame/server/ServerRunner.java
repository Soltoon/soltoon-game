package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.server.scenarios.freeWorld.FreeWorldServerManager;
import ir.pint.soltoon.soltoongame.server.scenarios.helloWorld.HelloWorldServerManager;
import ir.pint.soltoon.soltoongame.server.scenarios.name.NameServerManager;
import ir.pint.soltoon.soltoongame.server.sync.JavadTypeResultHandler;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.ui.GUIRunner;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.comminucation.ComServer;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerRunner {

    public static void runContainer() {
        ArrayList<ComRemoteInfo> remotes = ComRemoteInfo.createFromEnv();
        run(remotes, ServerMode.CONTAINER);
    }

    public static void run() {
        GameConfiguration.NUMBER_OF_PLAYERS = 2;
        run(Arrays.asList(GameConfiguration.DEFAULT_REMOTE_INFO, GameConfiguration.DEFAULT_REMOTE_INFO2), ServerMode.GUI);

    }

    public static void run(ComRemoteInfo comRemoteInfo, ServerMode serverMode) {
        run(Arrays.asList(comRemoteInfo), serverMode);
    }

    public static void run(List<ComRemoteInfo> remoteInfos, ServerMode serverMode) {

        // rebound communication between filters and clients.
        ComServer comServer = ComServer.initiate(remoteInfos);

        if (serverMode == ServerMode.GUI) {
            GUIRunner.openGUI();
        }

        // CREATE comminucation wrapper
        ServerComminucation server = new ServerComminucation(comServer);

        // start judge
        ServerManager serverManager = new FreeWorldServerManager(server);
        serverManager.run();

        Platform.exit(Platform.OK);
    }


    public static void runHelloWorld() {
        runHelloWorld(GameConfiguration.DEFAULT_REMOTE_INFO);
    }

    public static void runHelloWorld(ComRemoteInfo comRemoteInfo) {
        runHelloWorld(GameConfiguration.DEFAULT_REMOTE_INFO, GameConfiguration.BOARD_WIDTH, GameConfiguration.BOARD_HEIGHT);
    }

    public static void runHelloWorld(ComRemoteInfo remoteInfo, int width, int height) {
        // rebound communication between filters and clients.
        ComServer comServer = ComServer.initiate(remoteInfo);

        GUIRunner.openGUI();

        // CREATE comminucation wrapper
        ServerComminucation server = new ServerComminucation(comServer);

        // start judge
        ServerManager serverManager = new HelloWorldServerManager(server, width, height);
        serverManager.run();

        Platform.exit(Platform.OK);
    }

    public static void runName(int width, int height) {
        runName(GameConfiguration.DEFAULT_REMOTE_INFO, width, height);
    }

    public static void runName(ComRemoteInfo remoteInfo, int width, int height) {
        // rebound communication between filters and clients.
        ComServer comServer = ComServer.initiate(remoteInfo);

        GUIRunner.openGUI();

        // CREATE comminucation wrapper
        ServerComminucation server = new ServerComminucation(comServer);

        // start judge
        ServerManager serverManager = new NameServerManager(server, width, height);
        serverManager.run();

        Platform.exit(Platform.OK);
    }
}
