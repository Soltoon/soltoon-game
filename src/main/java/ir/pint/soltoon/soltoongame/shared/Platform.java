package ir.pint.soltoon.soltoongame.shared;

import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Platform {
    public static final int CONNECTION_ERROR = 1;
    public static final int PLAYERS_COUNT_ERROR = 2;
    public static final int CLIENT_INITIALIZATION_ERROR = 3;
    public static final int FATAL_ERROR = 4;
    public static final int GENERAL_ERROR = 5;
    public static final int OK = 0;

    private static ConcurrentLinkedDeque<Thread> threads = new ConcurrentLinkedDeque<>();

    public static void exit(int i) {
        ResultStorage.flush();


        if (Platform.CONNECTION_ERROR == i)
            System.exit(i);

        Thread t;
        while ((t = threads.pollFirst()) != null) {
            while (true) {
                try {
                    t.join();
                    break;
                } catch (InterruptedException e) {

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        System.exit(i);
    }

    public static void register(Thread thread) {
        threads.addLast(thread);
    }
}
