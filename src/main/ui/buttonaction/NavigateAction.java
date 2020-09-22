package ui.buttonaction;

import ui.Run;
import ui.panels.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigateAction implements ActionListener {
    private Run run;
    private GamePanel curPanel;
    private GamePanel nextPanel;

    public NavigateAction(Run run, GamePanel curPanel, GamePanel nextPanel) {
        this.run = run;
        this.curPanel = curPanel;
        this.nextPanel = nextPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextPanel.updatePanel();
        nextPanel.setVisible(true);
        run.setContentPane(nextPanel);
        curPanel.setVisible(false);
        run.validate();
    }
}
