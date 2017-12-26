package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.analysis.AnalysisType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

public class SuiPanel extends JPanel {
    private SuiManager suiManager;
    private Box playersBox;

    private JLabel roundbox = new JLabel();

    public SuiPanel(SuiManager suiManager) {
        this.suiManager = suiManager;

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();

        setSize(suiConfiguration.getPanelWidth(), suiConfiguration.getFrameHeight());

        setMaximumSize(new Dimension(suiConfiguration.getPanelWidth(), suiConfiguration.getFrameHeight()));

        Box controlsBox = Box.createHorizontalBox();
        controlsBox.add(new SuiPrevTurnButton());
        controlsBox.add(new SuiPauseStartButton());
        controlsBox.add(new SuiNextTurnButton());
        controlsBox.setAlignmentY(JComponent.TOP_ALIGNMENT);

        controlsBox.setSize(suiConfiguration.getPanelWidth(), controlsBox.getHeight());

        Box analysisBox = Box.createHorizontalBox();
        AnalysisComboBox analysisComboBox = new AnalysisComboBox();
        analysisBox.add(analysisComboBox);
        ShowAnalysisButton showAnalysisButton = new ShowAnalysisButton();
        analysisBox.add(showAnalysisButton);


        this.playersBox = Box.createVerticalBox();

        Collection<SuiPlayer> values = suiManager.getPlayers().values();
        for (SuiPlayer value : values) {
            value.setSuiManager(suiManager);
            playersBox.add(value);
        }

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(new Logo());
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(controlsBox);
        add(Box.createRigidArea(new Dimension(0, 40)));

        roundbox.setText("Round: (0/0)");

        add(roundbox);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(playersBox);
    }

    public void setRound(int round, int rounds) {
        roundbox.setText(String.format("Round (%d/%d)", round, rounds));
    }

    public Box getPlayersBox() {
        return playersBox;
    }

    abstract class SuiTimeButton extends JButton {
        public SuiTimeButton(String s) {
            super(s);
            setPreferredSize(new Dimension(suiManager.getSuiConfiguration().getPanelWidth() / 3, 30));
            setSize(suiManager.getSuiConfiguration().getPanelWidth() / 3, 30);
            setMaximumSize(new Dimension(suiManager.getSuiConfiguration().getPanelWidth() / 3, getMinimumSize().height));
            setBorder(new LineBorder(new Color(200, 200, 200)));
            setBackground(new Color(230, 230, 230));
        }
    }

    private class SuiNextTurnButton extends SuiTimeButton {
        public SuiNextTurnButton() {
            super(">>");

            setToolTipText("Next Turn");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    SuiPanel.this.suiManager.nextStep();
                }
            });
        }
    }

    private class SuiPrevTurnButton extends SuiTimeButton {
        public SuiPrevTurnButton() {
            super("<<");

            setToolTipText("Previous Turn");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    suiManager.prevStep();
                }
            });
        }
    }

    private class SuiPauseStartButton extends SuiTimeButton {
        private static final String pauseText = "||";
        private static final String resumeText = ">";

        public SuiPauseStartButton() {
            super(resumeText);

            setToolTipText("Pause/Resume");

            final SuiPauseStartButton pauseStartButton = this;

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    SuiPanel.this.suiManager.getSuiConfiguration().setPlay(!SuiPanel.this.suiManager.getSuiConfiguration().isPlay());

                    pauseStartButton.refresh();
                }
            });

            refresh();
        }

        private void refresh() {
            if (suiManager.getSuiConfiguration().isPlay()) {
                setText(pauseText);
            } else {
                setText(resumeText);
            }
        }
    }

    private class AnalysisComboBox extends JComboBox {
        public final String[] TYPES = AnalysisType.valuesAsString();

        public AnalysisComboBox() {

            setMaximumSize(new Dimension(suiManager.getSuiConfiguration().getPanelWidth(), getPreferredSize().height));
            for (String type : TYPES) {
                addItem(type);
            }


        }
    }

    private class ShowAnalysisButton extends JButton {
        public ShowAnalysisButton() {
            setText("Show");

        }
    }

    private class Logo extends JComponent {
        public BufferedImage logo = null;

        public Logo() {
            try {
                logo = ImageIO.read(Logo.class.getResource("/SoltoonGUI.png"));
            } catch (IOException e) {
                // ignore
            }

            if (logo == null)
                return;

            int width = suiManager.getSuiConfiguration().getPanelWidth();
            Dimension dimension = new Dimension(width, width * logo.getHeight() / logo.getWidth());
            setMaximumSize(dimension);
            setMinimumSize(dimension);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.clearRect(0, 0, getWidth(), getHeight());
            g2d.drawImage(logo.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, getWidth(), getHeight(), null);
        }
    }

}
