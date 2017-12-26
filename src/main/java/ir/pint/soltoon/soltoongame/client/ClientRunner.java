package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.utils.shared.comminucation.*;

public class ClientRunner {
    public static void runContainer(Class<? extends Soltoon> soltoonClass) {
        run(soltoonClass, ComRemoteConfig.fromEnv());
    }

    public static void run(Class<? extends Soltoon> soltoonClass) {
        run(soltoonClass, GameConfiguration.DEFAULT_REMOTE_CONFIG);
    }

    public static void run(Class<? extends Soltoon> soltoonClass, ComRemoteConfig remoteConfig) {
        Comminucation connect = ComClient.connect(remoteConfig, 5000);
        NetworkClientManager clientManager = new NetworkClientManager(soltoonClass, connect);
        clientManager.run();
        Platform.exit(Platform.OK);
    }

    public static void runPlayerTwo(Class<? extends Soltoon> soltoonClass) {
        run(soltoonClass, GameConfiguration.DEFAULT_REMOTE_CONFIG2);
    }
}
