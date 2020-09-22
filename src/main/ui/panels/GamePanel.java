package ui.panels;

import model.Archive;
import model.SavingSlot;
import ui.Run;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// REFERENCE: https://github.com/def-not-ys/BCS-Degree-Navigator
public abstract class GamePanel extends JPanel {

    public Run run;
    protected List<JButton> buttons;
    protected List<GamePanel> panels;
    public static Archive archive;
    public SavingSlot slot;
    static final Dimension PANEL_DIMENSION = new Dimension(1000, 700);

    public GamePanel(Run run, Archive archive) {
        super(new GridBagLayout());
        this.archive = archive;
        this.slot = run.getSlot();
        this.run = run;
        this.buttons = new ArrayList<>();
        this.panels = new ArrayList<>();

        setPreferredSize(PANEL_DIMENSION);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ImageIcon img = new ImageIcon("./data/backGround.png");
        setBounds(0, 0, 1000, 700);
        img.paintIcon(this, graphics, 0, 0);
    }

    // MODIFIES: this, run
    // EFFECTS: initialize the contents.
    public abstract void initializeContents();

    // MODIFIES: this and run
    // EFFECTS: initialize the interaction
    public abstract void initializeInteraction();

    // MODIFIES: this
    // EFFECTS: add components to the panel.
    public abstract void addToPanel();

    // MODIFIES: this and run
    // EFFECTS: update the panel
    public abstract void updatePanel();

}
