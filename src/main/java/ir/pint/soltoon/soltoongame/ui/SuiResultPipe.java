package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.shared.result.*;
import ir.pint.soltoon.soltoongame.ui.actions.*;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;
import ir.pint.soltoon.soltoongame.ui.elements.SuiPlayer;
import ir.pint.soltoon.utils.shared.facades.result.EventLog;
import ir.pint.soltoon.utils.shared.facades.result.MetaLog;
import ir.pint.soltoon.utils.shared.facades.result.ResultHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class SuiResultPipe implements ResultHandler {
    private SuiManager suiManager;

    private int playersJoined = 0;
    private AtomicInteger playerDependencies = new AtomicInteger(0);
    private AtomicInteger mapDependencies = new AtomicInteger(4);

    public SuiResultPipe(SuiManager suiManager) {
        this.suiManager = suiManager;
    }

    @Override
    public void addEvent(EventLog eventLog) {
        try {

            if (eventLog instanceof PlayerJoin) {
                PlayerJoin e = (PlayerJoin) eventLog;

                SuiPlayer suiPlayer = new SuiPlayer(e.getId(), e.getRemoteInfo(), e.getInitialMoney());
                suiManager.addPlayer(suiPlayer);


                if (mapDependencies.get() < 1 && playerDependencies.decrementAndGet() <= -suiManager.getSuiConfiguration().getPlayers()) {
                    suiManager.playerDependenciesResolved();
                }
            } else if (eventLog instanceof AgentAddEvent) {
                AgentAddEvent e = (AgentAddEvent) eventLog;

                SuiFighter suiFighter = new SuiFighter(SuiFighterType.get(e.getAgentType()), e.getX(), e.getY(), e.getAgent(), e.getPlayer(), e.getHp());
                suiManager.addFighter(suiFighter);

                SuiActionAdd suiActionAdd = new SuiActionAdd(e.getX(), e.getY(), e.getAgent(), e.getPlayer(), e.getScore(), e.getCost());
                suiManager.addStep(suiActionAdd);
            } else if (eventLog instanceof AgentMoveEvent) {
                AgentMoveEvent e = (AgentMoveEvent) eventLog;

                SuiActionMove suiActionMove = new SuiActionMove(e.getFromX(), e.getFromY(), e.getAgent(), e.getPlayer(), e.getToX(), e.getToY());
                suiManager.addStep(suiActionMove);
            } else if (eventLog instanceof AgentShootEvent) {
                AgentShootEvent e = (AgentShootEvent) eventLog;

                SuiActionShoot suiActionShoot = new SuiActionShoot(e.getX(), e.getY(), e.getPlayer(), e.getTargetX(), e.getTargetY());
                suiManager.addStep(suiActionShoot);
            } else if (eventLog instanceof AgentDiedEvent) {
                AgentDiedEvent e = (AgentDiedEvent) eventLog;
                SuiActionDie suiActionDie = new SuiActionDie(e.getAgent(), e.getPlayer(), e.getPenalty(), e.getX(), e.getY());
                suiManager.addStep(suiActionDie);
            } else if (eventLog instanceof AgentDamagedEvent) {
                AgentDamagedEvent e = (AgentDamagedEvent) eventLog;
                SuiActionDamage suiActionDamage = new SuiActionDamage(e.getX(), e.getY(), e.getPlayer(), e.getAgent(), e.getDamage());
                suiManager.addStep(suiActionDamage);
            } else if (eventLog instanceof GameEndedEvent) {
                suiManager.getSuiConfiguration().setEndEventRecieved(true);
            } else if (eventLog instanceof RoundStartEvent) {
                RoundStartEvent e = (RoundStartEvent) eventLog;
                SuiNextRound suiNextRound = new SuiNextRound(e.getRound(), e.getMoneyByPlayer());
                suiManager.addStep(suiNextRound);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMeta(MetaLog metaLog) {

    }

    @Override
    public void putMisc(String s, Object o) {
        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();
        if (s.equals("mapWidth")) {
            suiConfiguration.setBoardWidth(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("mapHeight")) {
            suiConfiguration.setBoardHeight(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("players")) {
            suiConfiguration.setPlayers(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("rounds")) {
            suiConfiguration.setRounds(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("finalSceneOnly")) {
            suiConfiguration.setFinalSceneOnly(((boolean) o));
        }

        if (mapDependencies.get() < 1)
            suiManager.mapDependenciesResloved();
    }

    @Override
    public void addException(Exception e) {

    }

    @Override
    public boolean flush() {
        return false;
    }
}
