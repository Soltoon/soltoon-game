package ir.pint.soltoon.soltoongame.shared;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteConfig;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

public class GameConfiguration {
    public static final double FRIEND_COEEF = 0.25;
    public static double INITIAL_HP_COEFF = 1;
    public static int PLAYERS_INITIAL_SCORE = 0;
    public static double CONNECTION_COEFF = 1.3;
    public static double EXTRA_TIME_COEFF = 0.2;
    public static long QUERY_TIME = 2000L;
    public static long QUERY_EXTRA_TIME = (long) (QUERY_TIME * EXTRA_TIME_COEFF);
    public static long CONNECTION_EXTRA_TIME = 500L;
    public static long QUERY_WAIT_TIME = QUERY_TIME + QUERY_EXTRA_TIME + CONNECTION_EXTRA_TIME;
    public static long QUERY_INITIALIZE_TIME = 1000L;
    public static long INITIALIZE_TIMEOUT = (long) (QUERY_INITIALIZE_TIME * CONNECTION_COEFF);
    public static long QUERY_FINALIZE_TIME = QUERY_TIME;
    public static long QUERY_FINALIZE_EXTRA_TIME = QUERY_EXTRA_TIME;
    public static long EXIT_QUERY_TIME = 1000L;

    public static int NUMBER_OF_PLAYERS = 2;
    public static int ROUNDS = 10;
    public static int BOARD_WIDTH = 20;
    public static int BOARD_HEIGHT = 15;

    public static int PLAYERS_INITIAL_MONEY = 100;
    public static int PLAYERS_TURN_MONERY = 50;


    public static int DEFAULT_CLIENTS_COUNT = 2;
    public static ComRemoteInfo DEFAULT_REMOTE_INFO = new ComRemoteInfo("player1", "First player", "127.0.0.1", 5566, "password1");
    public static ComRemoteInfo DEFAULT_REMOTE_INFO2 = new ComRemoteInfo("player2", "Second player", "127.0.0.1", 5567, "password2");
    public static ComRemoteConfig DEFAULT_REMOTE_CONFIG = new ComRemoteConfig("password1", 5566);
    public static ComRemoteConfig DEFAULT_REMOTE_CONFIG2 = new ComRemoteConfig("password2", 5567);
}
