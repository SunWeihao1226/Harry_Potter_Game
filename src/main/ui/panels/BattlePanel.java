package ui.panels;

import model.Archive;
import model.Battle;
import model.Spell;
import model.characters.*;
import ui.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BattlePanel extends GamePanel{

    private JLabel playerIcon;
    private JLabel enemyIcon;
    private JLabel gameInfo;
    private JLabel playerHp;
    private JLabel enemyHp;
    private List<JButton> spellButtons;
    private Battle battle;
    private Enemies quir;
    private Enemies deathEater;
    private Enemies basilisk;
    Wizards curWiz;
    Enemies curEne;

    File file = new File("./data/plot.txt");
    List plotDic = new ArrayList();

    // Constructor
    public BattlePanel(Run run, Archive archive) {
        super(run, archive);
        initializeContents();
        initializeInteraction();
        addToPanel();
    }

    // Setting background image
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ImageIcon img = new ImageIcon("./data/GamingBg.jpeg");
        setBounds(0, 0, 1000, 700);
        img.paintIcon(this, graphics, 0, 0);
    }

    // Initializing contents
    @Override
    public void initializeContents() {
        readPlot();
        battle = archive.getBattles().get(archive.getCheckPoint() - 1);
        curWiz = archive.getSelectedWizard();
        curEne = battle.getEnemy();
        playerIcon = new JLabel("");
        spellButtons = new ArrayList<>();
        initializePlayerIcon();
        enemyIcon = new JLabel("");
        enemyIcon.setIcon(new ImageIcon("./data/" + battle.getEnemy().getName()+".png"));
        enemyIcon.setSize(200,200);
        gameInfo = new JLabel("");
        setPlot();
        gameInfo.setFont(new Font("Serif", Font.ITALIC, 24));
        playerHp = new JLabel("HP: "+ curWiz.getHp() + "/" + curWiz.getMaxHp());
        enemyHp = new JLabel("HP: "+ curEne.getHp() + "/" + curEne.getMaxHP());
        playerHp.setFont(new Font("Serif", Font.ITALIC, 30));
        enemyHp.setFont(new Font("Serif", Font.ITALIC, 30));
        initializeButtons();
    }

    // Generating buttons
    private void initializeButtons() {
        for (Iterator iter = battle.getSpellToUse().values().iterator(); iter.hasNext();) {
            Spell spell = (Spell) iter.next();
            spellButtons.add(new JButton(spell.getSpellsName()));
        }
    }

    // Initializing wizard icons
    private void initializePlayerIcon() {
        if (curWiz.getName().equals("Harry")) {
            playerIcon.setIcon(new ImageIcon("./data/Harry.png"));
        } else if (curWiz.getName().equals("Ron")) {
            playerIcon.setIcon(new ImageIcon("./data/Ron.png"));
        } else if (curWiz.getName().equals("Hermione")) {
            playerIcon.setIcon(new ImageIcon("./data/Hermione.png"));
        }
    }

    // Initializing spell buttons interaction
    @Override
    public void initializeInteraction() {
        for (int i = 0; i < 4; i++) {
            JButton curButton = spellButtons.get(i);
            spellButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playerRound(curButton.getText());
                    endBattle();
                }
            });
        }
    }

    // One battle ends. Determining win or lose.
    private void endBattle() {
        if (curWiz.isDead) {
            loseDialog();
        } else if (curEne.isDead){
            archive.setCheckPoint(archive.getCheckPoint() + 1);
            int checkpnt = archive.getCheckPoint();
            int increasedHp = 5 * checkpnt;
            int increasedAtk = 2 * checkpnt + 1;
            archive.selectedWizard.increaseMaxHP(10);
            archive.selectedWizard.increaseATK(5);
            if (archive.getCheckPoint() < 3) {
                archive.unlockedSpells.put(checkpnt + 3, run.spellsToAdd.get(checkpnt - 2));
            }
            winDialog(increasedHp, increasedAtk);
        }
    }

    // Showing winning message. Continue or go back.
    private void winDialog(int hp, int att) {
        if (archive.getCheckPoint() >= 4) {
            endGame();
        } else {
            String newSpl = archive.getUnlockedSpells().get(archive.getCheckPoint() + 2).getSpellsName();
            String winInfo = "<html><body>Congratulations! You have defeat your enemy!<br>New spell unlocked: " +
                    newSpl + "<br>Wizard upgrade: HP +" + hp + "! Attack +" + att+ "!<br>Do you want to continue game?";
            int option = JOptionPane.showConfirmDialog(this,
                    winInfo, "Battle End", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                updateArchive();
                GamePanel selectSpell = new SelectSpellsPanel(run, archive);
                selectSpell.updatePanel();
                run.setContentPane(selectSpell);
                setVisible(false);
                selectSpell.setVisible(true);
                run.validate();
            } else {
                updateArchive();
                GamePanel startPanel = new StartPanel(run, archive);
                startPanel.updatePanel();
                run.setContentPane(startPanel);
                setVisible(false);
                startPanel.setVisible(true);
                run.validate();
            }
        }

    }

    // All the battles ended and won.
    private void endGame() {
        String message = "<html>Congratulations! You won all the battles!<br>But the amazing adventure just starts!<br>Be prepared! There will be more enemies...</html>";
        JOptionPane.showMessageDialog(this, message,
                "",JOptionPane.WARNING_MESSAGE);
        updateArchive();
        GamePanel startPanel = new StartPanel(run, archive);
        startPanel.updatePanel();
        run.setContentPane(startPanel);
        setVisible(false);
        startPanel.setVisible(true);
        run.validate();
    }

    // Showing lose message. Try again or go back.
    private void loseDialog() {
        int option = JOptionPane.showConfirmDialog(this,
                "<html><body>You Lose. <br>Do you want to try again?<body></html>", "Battle End",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            updatePanel();
        } else {
            updateArchive();
            GamePanel startPanel = new StartPanel(run, archive);
            startPanel.updatePanel();
            run.setContentPane(startPanel);
            setVisible(false);
            startPanel.setVisible(true);
            run.validate();
        }
    }

    // Updating the archive after winning or lose. Reset HP.
    private void updateArchive() {
        if (archive.getCheckPoint() == 1) {
            archive.selectedWizard.setHp(archive.selectedWizard.getMaxHp());
            battle.spellToUse = null;
            archive.battles.remove(0);
        } else {
            archive.selectedWizard.setHp(archive.selectedWizard.getMaxHp());
        }
    }

    // Set plot in gameInfo label.
    private void setPlot() { //!!!
        gameInfo.setText((String) plotDic.get(archive.getCheckPoint() - 1));
    }

    // Showing player and enemy's action.
    private void playerRound(String name) {
        Spell currSpell = findSpell(name);
        String playerSpell = "You used the spell " + currSpell.getSpellsName();
        String playerAtt = "You caused " + spellAttack(currSpell) + " damage to your enemy!";
        String enemyAtt = curEne.getName() + " attacked you!";
        String enemyDmg = "Caused " + battle.attackEnemyToWizard(curEne, curWiz) + " damage!";
        gameInfo.setText("<html><body>" + playerSpell + "<br>" + playerAtt + "<br>" +
                enemyAtt + "<br>" + enemyDmg+ "<body></html>");
        setHPs();
    }

    // returning caused damage using one spell.
    private int spellAttack(Spell currSpell) {
        return battle.attackWizardToEnemy(curWiz, curEne, getSpellIndex(battle.getSpellToUse(), currSpell));
    }

    // Helper method. Getting index of one spell.
    private int getSpellIndex(Map map, Object spell) {
        int ret = 0;
        for (Object index: map.keySet()) {
            if (map.get(index).equals(spell)) {
                ret = (Integer) index;
            }
        }
        return ret;
    }

    // Helper method. Find one spell in the map by it's name.
    private Spell findSpell(String name) { //?!
        Spell currSpell = null;
        if (name.equals("Stupefy")) {
            currSpell = battle.getSpellToUse().get(0);
        } else if (name.equals("Expelliarmus")) {
            currSpell = battle.getSpellToUse().get(1);
        } else if (name.equals("Petrificus Totalus")) {
            currSpell = battle.getSpellToUse().get(2);
        } else if (name.equals("Obliviate")) {
            currSpell = battle.getSpellToUse().get(3);
        } else if (name.equals("Reducto")) {
            currSpell = battle.getSpellToUse().get(4);
        }
        return currSpell;
    }

    // Setting hp of the wizard and enemy.
    private void setHPs() {
        if (curWiz.getHp() >= 0) {
            playerHp.setText("HP: "+ curWiz.getHp() + "/" + curWiz.getMaxHp());
        } else {
            playerHp.setText("HP: "+ 0 + "/" + curWiz.getMaxHp());
        }
        if (curEne.getHp() >= 0) {
            enemyHp.setText("HP: "+ curEne.getHp() + "/" + curEne.getMaxHP());
        } else {
            enemyHp.setText("HP: "+ 0 + "/" + curEne.getMaxHP());
        }
    }

    // Add elements to the panel.
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.ipadx = 15;
        gbc.ipady = 10;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        add(playerIcon, gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 6;
        gbc.gridheight = 10;
        add(playerHp, gbc);
        gbc.ipady = 15;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(gameInfo, gbc);
        gbc.ipady = 5;
        gbc.gridheight = 10;
        gbc.gridy = 15;
        for (int i = 0; i < spellButtons.size(); i++) {
            add(spellButtons.get(i), gbc);
            gbc.gridy += 10;
        }
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 10;
        add(enemyIcon, gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 11;
        gbc.gridheight = 1;
        add(enemyHp, gbc);

    }

    // Reading plot from the txt file.
    public List readPlot() {
        try {
            String str = new String();
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            do {
                str = br.readLine();
                plotDic.add(str);
            } while (str != null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plotDic;
    }

    // Update the panel.
    @Override
    public void updatePanel() {
        curWiz.setHp(curWiz.getMaxHp());
        curEne.setHp(curEne.getMaxHP());
        setHPs();
        setPlot();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
