package model;

import model.characters.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {
    private Battle battle;
    private Wizards harry = new Harry("Harry");
    private Enemies quir = new Quirrell("Quirrell");
    Spell expelliarmus = new Spell("Expelliarmus", 10);

    @BeforeEach
    public void runBefore() {
        battle = new Battle("battle", harry, quir);
    }

    @Test
    public void testIsWinTrue() {
        quir.setDead(true);
        assertTrue(battle.isWin());
    }

    @Test
    public void testIsWinFalse() {
        quir.setDead(false);
        assertFalse(battle.isWin());
    }



    @Test
    public void testAddSpellsToUse() {
        battle.addSpellsToUse(1, expelliarmus);
        assertTrue(battle.spellToUse.containsValue(expelliarmus));
    }

    @Test
    public void testAddSpellsToChoose() {
        battle.addSpellsToChoose(1, expelliarmus);
        assertTrue(battle.spellsToChoose.containsValue(expelliarmus));
    }

    @Test
    public void testchooseSpells() {
        Spell spell1 = new Spell("spell1", 10);
        Spell spell2 = new Spell("spell2", 10);
        Spell spell3 = new Spell("spell3", 10);
        Spell spell4 = new Spell("spell4", 10);
        battle.spellsToChoose.put(1, spell1);
        battle.spellsToChoose.put(2, spell2);
        battle.spellsToChoose.put(3, spell3);
        battle.spellsToChoose.put(4, spell4);
        battle.chooseSpells(1, 2, 3, 4);
        assertTrue(battle.spellToUse.containsValue(spell1));
        assertTrue(battle.spellToUse.containsValue(spell2));
        assertTrue(battle.spellToUse.containsValue(spell3));
        assertTrue(battle.spellToUse.containsValue(spell4));
    }

    @Test
    public void testAttackWizardToEnemy() {
        harry.setAtk(10);
        harry.setHp(100);
        quir.setHp(80);
        quir.setAtk(12);
        battle.addSpellsToUse(1, expelliarmus);
        battle.attackWizardToEnemy(harry, quir, 1);
        assertTrue(quir.getHp() <= 60 && quir.getHp() > 50);
        assertEquals(100, harry.getHp());
    }

    @Test
    public void testAttackWizardToEnemyZero() {
        assertEquals(0, battle.attackWizardToEnemy(harry, quir, battle.spellAtk(5)));;
    }

    @Test
    public void testAttackEnemyToWizard() {
        harry.setAtk(10);
        harry.setHp(100);
        quir.setHp(80);
        quir.setAtk(12);
        battle.attackEnemyToWizard(quir, harry);
        assertEquals(80, quir.getHp());
        assertTrue(harry.getHp() <= 88 && harry.getHp() > 78);
    }

    @Test
    public void testSpellAtk() {
        Spell spell1 = new Spell("spell1", 10);
        Spell spell2 = new Spell("spell2", 9);
        Spell spell3 = new Spell("spell3", 8);
        Spell spell4 = new Spell("spell4", 7);
        battle.addSpellsToUse(1, spell1);
        battle.addSpellsToUse(2, spell2);
        battle.addSpellsToUse(3, spell3);
        battle.addSpellsToUse(4, spell4);
        assertEquals(10, battle.spellAtk(1));
        assertEquals(9, battle.spellAtk(2));
        assertEquals(8, battle.spellAtk(3));
        assertEquals(7, battle.spellAtk(4));
    }

    @Test
    public void testBattleName() {
        battle.setBattleName("battle_test");
        assertEquals("battle_test", battle.getBattleName());
    }



    @Test
    public void testSpellToChoose() {
        Spell sptest = new Spell("test", 10);
        HashMap<Integer, Spell> test = new HashMap<>();
        battle.setSpellsToChoose(test);
        assertEquals(test, battle.getSpellsToChoose());
    }

    @Test
    public void testSpellsToUse() {
        Spell sptest = new Spell("test", 10);
        HashMap<Integer, Spell> test = new HashMap<>();
        battle.setSpellToUse(test);
        assertEquals(test, battle.getSpellToUse());
    }

    @Test
    public void testPlayer() {
        Wizards player = new Wizards("player");
        battle.setPlayer(player);
        assertEquals(player, battle.getPlayer());
    }

    @Test
    public void testEnemy() {
        Enemies enemy = new Enemies("enemy");
        battle.setEnemy(enemy);
        assertEquals(enemy, battle.getEnemy());
    }

    @Test
    public void testEquals() {
        Battle battle2 = new Battle("battle2", harry, quir);
        assertFalse(battle.equals(battle2));
        Ron ron = new Ron("Ron");
        DeathEater deathEater = new DeathEater("DeathEater");
        Battle battle3 = new Battle("battle3", ron, deathEater);
        Battle battle4 = new Battle("battle4", harry, deathEater);
        Battle battle5 = new Battle("battle1", ron, quir);
        assertFalse(battle2.equals(battle3));
        assertFalse(battle4.equals(battle3));
        assertFalse(battle5.equals(battle3));
        assertFalse(battle.equals(battle4));
        assertFalse(battle3.equals(battle4));
        assertFalse(battle2.equals(battle4));
        assertFalse(battle2.equals(battle5));
        assertFalse(battle4.equals(battle5));
    }



}
