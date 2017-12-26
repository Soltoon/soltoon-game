package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.shared.Platform;

public class Sui extends Thread {
    private boolean gameIsReady = false;
    private SuiManager suiManager;

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
                Thread.sleep(100);
                if (suiManager.getSuiConfiguration().isPlay() && !suiManager.getSuiConfiguration().isFinalSceneOnly() && skipper++ >= 5) {
                    suiManager.nextStep();
                    skipper = 0;
                }
                if (suiManager.getSuiConfiguration().isFinalSceneOnly() && suiManager.getSuiConfiguration().isEndEventRecieved()) {
                    while (suiManager.hasNextStep())
                        suiManager.nextStep();
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
