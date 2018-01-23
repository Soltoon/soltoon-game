package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.shared.Platform;

public class Sui extends Thread {
    private boolean gameIsReady = false;
    private SuiManager suiManager;
    public static long sleepTime = 1000;
    public static int speed = 2;
    public static boolean finalScene = false;

    private Sui(SuiManager suiManager) {
        this.suiManager = suiManager;
    }

    public static Sui initiate() {
        SuiConfiguration suiConfiguration = new SuiConfiguration();
        SuiManager suiManager = SuiManager.initiate(suiConfiguration);
        Sui sui = new Sui(suiManager);
        Platform.register(sui);
        return sui;
    }


    @Override
    public void run() {
        int skipper = 0;

        while (true) {
            try {
                Thread.sleep(Math.max(sleepTime / speed, 50));
                if (suiManager.getSuiConfiguration().isPlay() && !suiManager.getSuiConfiguration().isFinalSceneOnly()) {
                    suiManager.nextStep();
                }
                if ((suiManager.getSuiConfiguration().isFinalSceneOnly() || finalScene) && suiManager.getSuiConfiguration().isEndEventRecieved()) {
                    while (suiManager.hasNextStep())
                        suiManager.nextStep();

                    finalScene = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isGameReady() {
        return gameIsReady;
    }

    public void setGameReady() {
        gameIsReady = true;
    }
}
