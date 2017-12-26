package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiFighterType;
import ir.pint.soltoon.soltoongame.ui.SuiManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SuiFighter extends JComponent {
    private SuiFighterType fighterUI;
    private SuiManager suiManager;
    private BufferedImage image = null;

    private int boardX;
    private int boardY;

    private long id;

    private int hp = 15;
    private long player = 1L;

    private int restingTime = 2;
    private int reloadingTime = 2;


    public SuiFighter(SuiFighterType fighterUI, int boardX, int boardY, long id, long player, int hp) {
        this.fighterUI = fighterUI;
        this.boardX = boardX;
        this.boardY = boardY;
        this.id = id;
        this.player = player;
        this.hp = hp;
    }


    public void setBoardX(int boardX) {
        this.boardX = boardX;
    }

    public void setBoardY(int boardY) {
        this.boardY = boardY;
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (suiManager != null) {
            if (image == null)
                try {
                    image = ImageIO.read(getClass().getResource(fighterUI.image));
                } catch (IOException e) {
                    // ignore
                }

            Graphics2D graphics = (Graphics2D) g;
            graphics.clearRect(0, 0, getWidth(), getHeight());
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);


            graphics.drawImage(image.getScaledInstance(getImageSize(), getImageSize(), Image.SCALE_SMOOTH), getImageX(), getImageY(), getImageSize(), getImageSize(), null);

//            graphics.setColor(Color.decode("#F97063"));
//            graphics.fillRect(0, getMapHeight() - 7, ((int) (getMapWidth() * getRestingTimePercentage())), 3);
//
//            graphics.setColor(Color.decode("#F8C36C"));
//            graphics.fillRect(0, getMapHeight() - 4, (int) (getMapWidth() * getReloadingTimePrecentage()), 3);


            if (getWidth() < 50) {
                graphics.setColor(suiManager.getSuiConfiguration().getPlayerColor(player));
                graphics.fillRect(0, 0, getWidth(), 2);
                graphics.setColor(Color.decode("#CC3333"));
                graphics.fillRect(0, getHeight() - 2, ((int) (getWidth() * (getHp() / ((double) fighterUI.fighterType.getHealth())))), 2);
            } else {
                graphics.setFont(new Font("Lora", Font.PLAIN, 12));
                graphics.setColor(Color.BLACK);
                graphics.drawString(hp + "", ((int) (getWidth() * 0.05)), 10 + ((int) (getHeight() * 0.05)));

                graphics.setColor(suiManager.getSuiConfiguration().getPlayerColor(player));
                graphics.fillOval(getWidth() - 12, 2, 10, 10);
            }
        }
    }

    private double getReloadingTimePrecentage() {
        return Math.min(reloadingTime / ((double) fighterUI.fighterType.getReloadingTime()), 1);
    }

    private double getRestingTimePercentage() {
        return Math.min(restingTime / ((double) fighterUI.fighterType.getRestingTime()), 1);
    }

    private int getImageY() {
        return (getHeight() - getImageSize()) / 2;
    }

    private int getImageX() {
        return (getWidth() - getImageSize()) / 2;
    }

    private int getImageSize() {
        return (int) Math.min(getWidth() * 0.7, getHeight() * 0.7);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public long getPlayer() {
        return player;
    }

    public void setPlayer(long player) {
        this.player = player;
    }

    public int getRestingTime() {
        return restingTime;
    }

    public void setRestingTime(int restingTime) {
        this.restingTime = restingTime;
    }

    public int getReloadingTime() {
        return reloadingTime;
    }

    public void setReloadingTime(int reloadingTime) {
        this.reloadingTime = reloadingTime;
    }


    public void setSuiManager(SuiManager suiManager) {
        this.suiManager = suiManager;
    }

    public void rebound() {
        int cellSize = suiManager.getSuiConfiguration().getCellSize();
        setBounds(boardX * cellSize, boardY * cellSize, cellSize, cellSize);
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }
}

