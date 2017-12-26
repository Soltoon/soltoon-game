package ir.pint.soltoon.soltoongame.ui;


import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SuiConfiguration {

    // Static initialization
    private static final int DEFAULT_CELL_SIZE = 70;
    private static final int DEFAULT_PANEL_WIDTH = 200;

    public static int CELL_SIZE = 70;
    public static final LinkedList<Color> palleteColors = new LinkedList<Color>();
    public static Font font;
    public static Font TITLE_FONT;
    public static Font HEAD_FONT;
    public static Font TEXT_FONT;

    private int round = 0;

    static {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = Font.createFont(Font.TRUETYPE_FONT, SuiConfiguration.class.getResourceAsStream("/Lora-Regular.ttf"));
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        HEAD_FONT = new Font("Lora", Font.BOLD, 18);
        TEXT_FONT = new Font("Lora", Font.PLAIN, 12);
        TITLE_FONT = new Font("Lora", Font.BOLD, 22);

    }


    private int boardWidth, boardHeight;
    private int cellSize;
    private int rounds, players;
    private double screenSizeWidth, screenSizeHeight;

    private volatile boolean initiated = false;
    private volatile boolean play = true;
    private volatile boolean endEventRecieved = false;
    private volatile boolean finalSceneOnly;

//    private volatile


    private Map<Long, Color> playerColors = new ConcurrentHashMap<>();


    static {
        palleteColors.addAll(Arrays.asList(Color.decode("#FFAA1C"), Color.decode("#233140"), Color.decode("#BA6723"), Color.decode("#62895e")));
    }

    public Color getPlayerColor(Long player) {
        Color color = playerColors.get(player);
        return color == null ? assignColor(player) : color;
    }

    private Color assignColor(Long player) {
        Color c;
        if ((c = palleteColors.pop()) == null) {
            c = new Color(((int) (Math.random() * 255)), ((int) (Math.random() * 255)), ((int) (Math.random() * 255)));
        }

        playerColors.put(player, c);

        return c;
    }

    public SuiConfiguration() {
    }

    public SuiConfiguration(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        initialize();
    }

    public void initialize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.screenSizeWidth = screenSize.getWidth();
        this.screenSizeHeight = screenSize.getHeight();

        double cellSizeHeight = Math.min(Math.min(DEFAULT_CELL_SIZE, screenSizeHeight / (boardHeight + 2)), (screenSizeHeight - 100) / boardHeight);
        double cellSizeWidth = Math.min(Math.min(DEFAULT_CELL_SIZE, (screenSizeWidth - DEFAULT_PANEL_WIDTH) / (boardWidth + 2)), (screenSizeWidth - DEFAULT_PANEL_WIDTH - 100) / (boardWidth));

        this.cellSize = ((int) Math.min(cellSizeWidth, cellSizeHeight));

        initiated = true;


    }

    public int getCellSize() {
        return cellSize;
    }

    public int getMapWidth() {
        return boardWidth * getCellSize();
    }

    public int getMapHeight() {
        return boardHeight * getCellSize();
    }


    public int getPanelWidth() {
        return DEFAULT_PANEL_WIDTH;
    }

    public int getFrameX() {
        return screenSizeWidth > getFrameWidth() ? (int) ((screenSizeWidth - getFrameWidth()) / 2) : 0;
    }

    public int getFrameY() {
        return screenSizeHeight > getFrameHeight() ? (int) ((screenSizeHeight - getFrameHeight()) / 2) : 0;
    }

    public int getFrameWidth() {
        return getMapWidth() + getPanelWidth() + 20;
    }

    public int getFrameHeight() {
        return Math.max(getMapHeight() + getCellSize() / 2, 500);
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public boolean isInitiated() {
        return initiated;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    @Override
    public String toString() {
        return "SuiConfiguration{" +
                "boardWidth=" + boardWidth +
                ", boardHeight=" + boardHeight +
                ", cellSize=" + cellSize +
                ", rounds=" + rounds +
                ", players=" + players +
                ", screenSizeWidth=" + screenSizeWidth +
                ", screenSizeHeight=" + screenSizeHeight +
                ", playerColors=" + playerColors +
                '}';
    }

    public void setFinalSceneOnly(boolean finalSceneOnly) {
        this.finalSceneOnly = finalSceneOnly;
    }

    public boolean isFinalSceneOnly() {
        return finalSceneOnly;
    }

    public boolean isEndEventRecieved() {
        return endEventRecieved;
    }

    public void setEndEventRecieved(boolean endEventRecieved) {
        this.endEventRecieved = endEventRecieved;
    }

    public int getPlayers() {
        return players;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRounds() {
        return rounds;
    }
}
