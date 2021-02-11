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

    private JLabel temp;
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
    String plotFile = new String("./data/plot.txt");
    List plotDic = new ArrayList();

    public BattlePanel(Run run, Archive archive) {
        super(run, archive);
        initializeContents();
        initializeInteraction();
        addToPanel();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ImageIcon img = new ImageIcon("./data/fieldImg.png");
        setBounds(0, 0, 1000, 700);
        img.paintIcon(this, graphics, 0, 0);
    }

    @Override
    public void initializeContents() {
        readPlot();
        battle = archive.getBattles().get(archive.getCheckPoint() - 1);
        temp = new JLabel(archive.getSelectedWizard().getName());
        curWiz = archive.getSelectedWizard();
        curEne = battle.getEnemy();
//        quir = new Quirrell("Quirrell");
//        deathEater = new DeathEater("DeathEater");
//        basilisk = new Basilisk("Basilisk");
//        initializeBattle();
        playerIcon = new JLabel("");
        spellButtons = new ArrayList<>();
        initializePlayerIcon();
        enemyIcon = new JLabel("");
//        setEnemyIcon();
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

//    private void setEnemyIcon() {
//        int i = archive.getCheckPoint();
//        if (i == 1) {
//            enemyIcon.setIcon(new ImageIcon("./data/DeathEater.png"));
//        } else if (i == 2) {
//            enemyIcon.setIcon(new);
//        }
//    }

    private void initializeButtons() {
//        for (int i = 0; i < 4; i++) {
//            List<Integer> indexList = new ArrayList<>();
//            battle.getSpellToUse().values().iterator();
//            spellButtons.add(new JButton(battle.getSpellToUse().get(i).getSpellsName()));
//        }
        for (Iterator iter = battle.getSpellToUse().values().iterator(); iter.hasNext();) {
            Spell spell = (Spell) iter.next();
            spellButtons.add(new JButton(spell.getSpellsName()));
        }
    }


    private void initializePlayerIcon() {
        if (curWiz.getName().equals("Harry")) {
            playerIcon.setIcon(new ImageIcon("./data/Harry.png"));
        } else if (curWiz.getName().equals("Ron")) {
            playerIcon.setIcon(new ImageIcon("./data/Ron.png"));
        } else if (curWiz.getName().equals("Hermione")) {
            playerIcon.setIcon(new ImageIcon("./data/Hermione.png"));
        }
    }

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

    private void updateArchive() {//!!!
        if (archive.getCheckPoint() == 1) {
            archive.selectedWizard.setHp(archive.selectedWizard.getMaxHp());
            battle.spellToUse = null;
            archive.battles.remove(0);
        } else {
            archive.selectedWizard.setHp(archive.selectedWizard.getMaxHp());
        }
    }

//    private void enemyRound() {
//        String enemyAtt = curEne.getName() + "attacked you!";
//        String enemyDmg = "Caused " + battle.attackEnemyToWizard(curEne, curWiz) + "damage!";
//        gameInfo.setText("<>");
//    }


    private void setPlot() { //!!!
        gameInfo.setText((String) plotDic.get(archive.getCheckPoint() - 1));
    }

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

    private int spellAttack(Spell currSpell) {
//        Spell curSpell = findSpell(name);
        return battle.attackWizardToEnemy(curWiz, curEne, getSpellIndex(battle.getSpellToUse(), currSpell));
    }

    private int getSpellIndex(Map map, Object spell) {
        int ret = 0;
        for (Object index: map.keySet()) {
            if (map.get(index).equals(spell)) {
                ret = (Integer) index;
            }
        }
        return ret;
    }

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
