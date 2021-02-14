package ui.panels;

import model.Archive;
import ui.Run;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// REFERENCE: https://github.com/def-not-ys/BCS-Degree-Navigator
public class ArchiveManagementPanel extends GamePanel {

    protected JList<Archive> archiveList;
    protected JScrollPane curUserPanel;
    protected JButton remove;
    protected JButton rename;
    protected JButton goBack;
    protected JLabel info = new JLabel();
    protected JLabel report = new JLabel();
    protected JLabel label;
    protected Archive selectedArchive;
    protected Archive curArchive;
    protected JTextField archiveRename;

    // MODIFIES: this
    // EFFECTS: construct the panels.
    public ArchiveManagementPanel(Run run, Archive archive) {
        super(run, archive);
        initializeList();
        initializeContents();
        initializeInteraction();
        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize the list
    public void initializeList() {
        archiveList = new JList<>();
        updateSlot();
    }

    // MODIFIES: this
    // EFFECTS: update the slot
    public void updateSlot() {
        DefaultListModel<Archive> defaultListModel = new DefaultListModel<>();
        defaultListModel.removeAllElements();
        HashMap<String, Archive> archives = slot.getSlot();
        for (String str : archives.keySet()) {
            defaultListModel.addElement(archives.get(str));
        }
        try {
            archiveList.setModel(defaultListModel);
        } catch (NullPointerException e) {
            // throw exception
        }
        info.setText("Please remove an archive!");
        curArchive = archive;
    }


    // MODIFIES: this
    // EFFECTS: initialize the contents of the panel
    @Override
    public void initializeContents() {
        updateSlot();
        curUserPanel = new JScrollPane(archiveList);
        curUserPanel.setPreferredSize(new Dimension(300, 600));
        curUserPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(1, 1));
        remove = new JButton("Remove");
        rename = new JButton("Rename");
        goBack = new JButton("back");
        label = new JLabel("Existing Archives: ");
        archiveRename = new JTextField();
    }

    // MODIFIES: this
    // EFFECTS: initialize the interaction
    @Override
    public void initializeInteraction() {
        initializeListInteraction();
        initializeButtons();
    }


    // MODIFIES: this
    // EFFECTS: initialize the remove button
    public void initializeButtons() {
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (curArchive.getArchiveName() == selectedArchive.getArchiveName()) {
//                    makeSureDelete();
//                    archive = new Archive("noname", "wizard", "0", "0", "0");
//                    removeArchive();
//                }
                if (selectedArchive != null) {
                    makeSureDelete();
//                    removeArchive();
                } else if (selectedArchive == null) {
                    errorMessage();
                }
                updatePanel();
            }
        });

        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedArchive == null) {
                    errorMessage();
                } else {
                    renameArchive();
                }
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel startPanel = new StartPanel(run, archive);
                startPanel.updatePanel();
                run.setContentPane(startPanel);
                setVisible(false);
                startPanel.setVisible(true);
                run.validate();
            }
        });
    }

    // Rename one archive, and the feedback.
    private void renameArchive() {
        Object[] message = {"Please enter new archive name:", archiveRename};
        int num = JOptionPane.showConfirmDialog(this, message,
                "Rename", JOptionPane.OK_CANCEL_OPTION);
        if (num == JOptionPane.OK_OPTION) {
            String newName = archiveRename.getText();
            if (slot.getSlot().containsKey(newName)) {
                JOptionPane.showMessageDialog(this,
                        "This name is already occupied. Please enter another name.",
                        "existing name", JOptionPane.PLAIN_MESSAGE);
                renameArchive();
            } else {
                JOptionPane.showMessageDialog(this,
                        "You have successfully renamed your archive:" + newName,
                        "successful rename", JOptionPane.PLAIN_MESSAGE);
                selectedArchive.setArchiveName(newName);
            }
        }
    }

    // show error message
    private void errorMessage() {
        JOptionPane.showMessageDialog(this,
                "Please select an archive!",
                "Error!", JOptionPane.PLAIN_MESSAGE);
    }

    // show confirmation of deleting
    private void makeSureDelete() {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete your archive: " + selectedArchive.getArchiveName() + "?", "Warning",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.YES_OPTION) {
//            archive = new Archive("noname", "wizard", "0", "0", "0");
            removeArchive();
        } else {
            updatePanel();
        }
    }

    // remove one archive
    public void removeArchive() {
        slot.removeArchive(selectedArchive);
        report.setText(selectedArchive.getArchiveName() + " has been deleted.");
    }

    // MODIFIES: this
    // EFFECTS: initialize the interaction with the list
    protected void initializeListInteraction() {
        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedArchive = archiveList.getSelectedValue();
            }
        };
        archiveList.addListSelectionListener(listener);

    }

    // MODIFIES:  this
    // EFFECTS: add the components to the panel
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(20, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.insets = new Insets(0, 20, 20, 10);
        gbc.gridy = 1;
        gbc.gridheight = 100;
        add(curUserPanel, gbc);

        gbc.insets = new Insets(20, 40, 0, 20);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(info, gbc);
        gbc.gridy = 2;
        add(report, gbc);
        gbc.gridy = 3;
        add(remove, gbc);
        gbc.gridy = 4;
        add(rename, gbc);
        gbc.gridy = 5;
        add(goBack, gbc);

    }

    // MODIFIES: this
    // EFFECTS: update the panel
    @Override
    public void updatePanel() {
        updateSlot();
        info.setText("Hi! You can rename or delete your archives here!");
    }
}
