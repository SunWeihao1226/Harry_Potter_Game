package ui.panels;

import model.Archive;
import ui.Run;
import ui.buttonaction.NavigateAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


// REFERENCE: https://blog.csdn.net/niaonao/article/details/53670337
//            https://docs.oracle.com/javase/7/docs/api/javax/swing/ImageIcon.html
public class StartPanel extends GamePanel {

    private JLabel title;
    private JLabel intro1;
    private JLabel intro2;
    private JLabel intro3;
    private JLabel intro4;
    private GridBagConstraints constraints;
    private List<JButton> backbuttons;

    // MODIFIES: this
    // EFFECTS: construct the panel.
    public StartPanel(Run run, Archive archive) {
        super(run, archive);
        backbuttons = new ArrayList<>();
        initializeContents();
        initializeInteraction();
        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes and add buttons on the start panel, adn add then to the buttons list
    public void addButtons() {
        JButton button1 = new JButton("New Game");
        JButton button2 = new JButton("Continue Game");
        JButton button3 = new JButton("Delete Archive");
        JButton button4 = new JButton("Save Game");
        JButton button5 = new JButton("Quit Game");
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run.save();
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Initialize the panels
    public void initializePanels() {
        panels.add(new NewArchivePanel(run, archive));
        panels.add(new ContinueGamePanel(run, archive));
        panels.add(new ArchiveManagementPanel(run, archive));
        constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.insets = new Insets(10, 20, 5, 20);
        constraints.gridx = 4;
        constraints.gridy = 3;

        for (GamePanel panel : panels) {
            JButton getBack = new JButton("back");
            backbuttons.add(getBack);
            panel.add(getBack, constraints);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes
    public void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            backbuttons.get(i).addActionListener(new NavigateAction(run, panels.get(i), this));
        }
    }


    // EFFECTS: initialize contents in the panel.
    @Override
    public void initializeContents() {
        addButtons();
        title = new JLabel();
        Icon titlePic = new ImageIcon("./data/title.png");
        title.setIcon(titlePic);
        intro1 = new JLabel("Welcome to the Harry Potter the Magic World!");
        intro1.setFont(new Font("Serif", Font.ITALIC, 18));
        intro2 = new JLabel("You are a brave wizard of Hogwarts,");
        intro2.setFont(new Font("Serif", Font.ITALIC, 18));
        intro3 = new JLabel("and you will fight against evil enemies.");
        intro3.setFont(new Font("Serif", Font.ITALIC, 18));
        intro4 = new JLabel("The adventure starts from NOW!");
        intro4.setFont(new Font("Serif", Font.ITALIC, 18));
        initializePanels();
        initializeButtons();
    }


    // MODIFIES: this
    // EFFECTS: initialize the interactions
    @Override
    public void initializeInteraction() {
        for (int i = 0; i < panels.size(); i++) {
            NavigateAction next = new NavigateAction(run, this, panels.get(i));
            buttons.get(i).addActionListener(next);
        }
        NavigateAction newArchivePanel = new NavigateAction(run, this, panels.get(0));
        buttons.get(0).addActionListener(newArchivePanel);
        NavigateAction read = new NavigateAction(run, this, panels.get(1));
        buttons.get(1).addActionListener(read);
        NavigateAction remove = new NavigateAction(run, this, panels.get(2));
        buttons.get(2).addActionListener(remove);
        buttons.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDialogPane();
                run.save();
            }
        });
        buttons.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: end the game and save the data
    private void endGame() {
        run.save();
        System.out.println("Save data!");
        System.exit(0);
    }

    // EFFECTS: set the dialog window
    private void saveDialogPane() {
        JOptionPane.showMessageDialog(this,"Saved successfully!",
                "successful saved", JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: add the components to the panel
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 5, 50, 3);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.insets = new Insets(5, 400, 10, 500);
        int y = 5;
        for (JButton button : buttons) {
            gbc.gridy = y;
            this.add(button, gbc);
            y++;
        }
        addIntro(gbc);
    }

    // EFFECTS: add the intro to the panel
    public void addIntro(GridBagConstraints gbc) {
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 600, 5, 5);
        add(intro1, gbc);
        gbc.gridy = 2;
        add(intro2, gbc);
        gbc.gridy = 3;
        add(intro3, gbc);
        gbc.gridy = 4;
        add(intro4, gbc);
    }

    // MODIFIES: this
    // EFFECTS: update the panel
    @Override
    public void updatePanel() {
        intro1.setText("Welcome to the Harry Potter the Magic World!");
    }
}
