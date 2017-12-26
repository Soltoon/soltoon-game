package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SuiPlayer extends JComponent {
    private static final int PLAYER_HEIGHT = 100;
    private Long id;
    private int money;
    private int score;
    private ComRemoteInfo remoteInfo;
    private SuiManager suiManager;

    public SuiPlayer(long id, ComRemoteInfo remoteInfo) {
        this.id = id;
        this.remoteInfo = remoteInfo;
    }

    public SuiPlayer(Long id, ComRemoteInfo remoteInfo, int initialMoney) {
        this.id = id;
        this.money = initialMoney;
        this.score = 0;
        this.remoteInfo = remoteInfo;
    }

    public void setSuiManager(SuiManager suiManager) {
        this.suiManager = suiManager;
        Dimension dimension = new Dimension(suiManager.getSuiConfiguration().getPanelWidth(), PLAYER_HEIGHT);
        setSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        revalidate();
        repaint();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        revalidate();
        repaint();
    }

    public void addScore(int score) {
        this.score += score;
        revalidate();
        repaint();
    }

    public ComRemoteInfo getRemoteInfo() {
        return remoteInfo;
    }

    public void setRemoteInfo(ComRemoteInfo remoteInfo) {
        this.remoteInfo = remoteInfo;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(40, 40, 40));
        g2d.setFont(SuiConfiguration.HEAD_FONT);

        String displayName = Objects.equals(remoteInfo.getDisplayName(), "") || remoteInfo.getDisplayName() == null ?
                remoteInfo.getHost() + ":" + remoteInfo.getPort() : remoteInfo.getDisplayName();
        g2d.drawString(displayName, 10, 25);

        g2d.setFont(SuiConfiguration.TEXT_FONT);

        g2d.drawString("Score: " + score, 10, 55);
        g2d.drawString("Money: " + money, 10, 70);

        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();

        Color playerColor = suiConfiguration.getPlayerColor(id);
        g2d.setColor(playerColor);

        g2d.fillRect(10, 30, (int) (suiConfiguration.getPanelWidth() * 0.8), 5);
    }


    public void addMoney(int m) {
        this.money += m;
        revalidate();
        repaint();
    }
}
