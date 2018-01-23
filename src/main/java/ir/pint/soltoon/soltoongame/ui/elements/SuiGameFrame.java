package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;

import javax.swing.*;
import java.awt.*;

public class SuiGameFrame extends JFrame {

    private SuiManager suiManager;

    public SuiGameFrame(SuiManager suiManager) throws HeadlessException {
        this.suiManager = suiManager;

        setTitle("Soltoon - Game viewer");

        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();
        setBounds(suiConfiguration.getFrameX(), suiConfiguration.getFrameY(), suiConfiguration.getFrameWidth(), suiConfiguration.getFrameHeight());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void forceRepaint() {

    }
}
