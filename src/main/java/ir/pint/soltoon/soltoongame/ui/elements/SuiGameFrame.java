package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.actions.SuiAction;
import ir.pint.soltoon.soltoongame.ui.actions.SuiActionAdd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
