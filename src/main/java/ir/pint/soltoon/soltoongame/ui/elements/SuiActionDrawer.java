package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.actions.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SuiActionDrawer extends JComponent {
    private SuiManager suiManager;
    private SuiAction currentAction;


    private static final Stroke ADD_STROKE = new BasicStroke(6.0f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 15.0f, new float[]{15.0f}, 5.0f);
    private static final Stroke ADD_STROKE_TINY = new BasicStroke(2.0f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 5.0f, new float[]{5.0f}, 2.0f);
    private static final Stroke SHOOT_STROKE = new BasicStroke(6.0f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 10.0f, new float[]{10.0f}, 1.0f);
    private static final Stroke SHOOT_STROKE_TINY = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 5.0f, new float[]{5.0f}, 2.0f);

    public SuiActionDrawer(SuiManager suiManager) {
        this.suiManager = suiManager;

        setBounds(0, 0, suiManager.getSuiConfiguration().getMapWidth(), suiManager.getSuiConfiguration().getMapHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (suiManager.getSuiConfiguration().isFinalSceneOnly())
            return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int cellSize = suiManager.getSuiConfiguration().getCellSize();

        if (currentAction instanceof SuiActionAdd) {
            if (cellSize < 50)
                g2d.setStroke(ADD_STROKE_TINY);
            else
                g2d.setStroke(ADD_STROKE);
            Color decode = Color.decode("#444444");
            g2d.setColor(decode.brighter());
            g2d.drawOval(getCellX(currentAction.getX()), getCellY(currentAction.getY()), cellSize, cellSize);
        } else if (currentAction instanceof SuiActionShoot) {

            if (cellSize < 50)
                g2d.setStroke(SHOOT_STROKE_TINY);
            else
                g2d.setStroke(SHOOT_STROKE);

            g2d.setColor(new Color(20, 20, 20, 96));
            g2d.drawLine(getCellXShoot(currentAction.getX()), getCellYShoot(currentAction.getY()), getCellXShoot(((SuiActionShoot) currentAction).getToX()), getCellYShoot(((SuiActionShoot) currentAction).getToY()));
            g2d.setColor(Color.RED);
            int boxSize = Math.min(cellSize / 3, 8);
            g2d.fillRect(getCellXShoot(((SuiActionShoot) currentAction).getToX()) - boxSize / 2, getCellYShoot(((SuiActionShoot) currentAction).getToY()) - boxSize / 2, boxSize, boxSize);

        } else if (currentAction instanceof SuiActionMove) {
            SuiActionMove e = (SuiActionMove) this.currentAction;

            if (cellSize < 50)
                g2d.setStroke(SHOOT_STROKE_TINY);
            else
                g2d.setStroke(SHOOT_STROKE);
            g2d.setColor(Color.decode("#444444").brighter());

            int xAdd = e.getToX() == e.getX() ? 0 : cellSize / -2;
            int yAdd = e.getToY() == e.getY() ? 0 : cellSize / -2;

            g2d.drawLine(getCellXMove(e.getX()), getCellYMove(e.getY()), getCellXMove(e.getToX()), getCellYMove(e.getToY()));
            g2d.setColor(Color.decode("#444444"));
//            g2d.fillOval(getCellXMove(e.getToX()) - 5 + xAdd, getCellYMove(e.getToY()) - 5 + yAdd, 12, 12);
        } else if (currentAction instanceof SuiActionDie) {
            g2d.setColor(new Color(255, 30, 0, 10));
            g2d.fillRect(getCellX(currentAction.getX()), getCellY(currentAction.getY()), cellSize, cellSize);
        }

    }

    private int getCellYShoot(int y) {
        return getCellY(y) + getCellSize() / 2;
    }

    private int getCellSize() {
        return suiManager.getSuiConfiguration().getCellSize();
    }

    private int getCellXShoot(int x) {
        return (int) (getCellX(x) + getCellSize() / 2);
    }


    private int getCellXMove(int x) {
        return getCellX(x) + getCellSize() / 2;
    }

    private int getCellYMove(int y) {
        return getCellY(y) + getCellSize() / 2;
    }

    private int getCellY(int y) {
        return y * getCellSize();
    }

    private int getCellX(int x) {
        return x * getCellSize();
    }

    public void setCurrentAction(SuiAction currentAction) {
        this.currentAction = currentAction;
    }
}
