package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SuiGameBoard extends JPanel {
    private ConcurrentLinkedDeque<Component> components = new ConcurrentLinkedDeque<>();
    private SuiManager suiManager;

    public SuiGameBoard(SuiManager suiManager) {
        this.suiManager = suiManager;
        setLayout(null);
    }

    public void initiate() {
        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();
        setBounds(0, 0, suiConfiguration.getMapWidth(), suiConfiguration.getFrameHeight());
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();
        int cellSize = suiConfiguration.getCellSize();

        if (suiConfiguration.isInitiated()) {
            graphics.clearRect(0, 0, getWidth(), getHeight());
            graphics.setColor(Color.decode("#fafafa"));
            graphics.fillRect(0,0,getWidth(),getHeight());
//            for (int i = 0, k = 0; i < suiConfiguration.getBoardWidth(); i++) {
//                for (int j = 0; j < suiConfiguration.getBoardHeight(); j++, k++) {
//                    graphics.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
//                }
//            }

            graphics.setColor(Color.decode("#eeeeee"));
            for (int i = 0; i < suiConfiguration.getBoardWidth() + 1; i++) {
                graphics.drawLine(i * cellSize, 0, i * cellSize, cellSize * suiConfiguration.getBoardHeight());
            }

            for (int i = 0; i < suiConfiguration.getBoardHeight() + 1; i++) {
                graphics.drawLine(0, i * cellSize, cellSize * suiConfiguration.getBoardWidth(), i * cellSize);
            }
            graphics.setColor(Color.decode("#e0e0e0"));
            graphics.drawLine(0, suiConfiguration.getBoardHeight() * cellSize+1, cellSize * suiConfiguration.getBoardWidth(), suiConfiguration.getBoardHeight() * cellSize+1);
        }
    }

    @Override
    public void repaint() {
        super.repaint();

        if (components != null && !components.isEmpty()) {
            Iterator<Component> iterator = components.iterator();
            while (iterator.hasNext()) {
                Component next = iterator.next();
                next.revalidate();
                next.repaint();
            }
        }
    }

    @Override
    public synchronized Component add(Component component) {
        components.addLast(component);
        return super.add(component);
    }

    @Override
    public void remove(Component component) {
        components.remove(component);
        super.remove(component);
    }

    public void forceRepaint() {
        revalidate();
        repaint();
    }
}
