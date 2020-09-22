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
    protected JLabel info = new JLabel();
    protected JLabel report = new JLabel();
    protected JLabel label;
    protected Archive selectedArchive;
    protected Archive curArchive;

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
        remove = new JButton("Remove Archive");
        label = new JLabel("Existing Archives: ");

    }

    // MODIFIES: this
    // EFFECTS: initialize the interaction
    @Override
    public void initializeInteraction() {
        initializeListInteraction();
        initializeRemoveButton();
    }


    // MODIFIES: this
    // EFFECTS: initialize the remove button
    public void initializeRemoveButton() {
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curArchive.getArchiveName() == selectedArchive.getArchiveName()) {
                    archive = new Archive("noname", "wizard", "0", "0", "0");
                    removeArchive();
                } else if (selectedArchive != null) {
                    removeArchive();
                }
                updatePanel();
            }
        });
    }

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
        gbc.gridheight = 3;
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

    }

    // MODIFIES: this
    // EFFECTS: update the panel
    @Override
    public void updatePanel() {
        updateSlot();
        info.setText("Hi! Please delete an archive!");
    }
}
