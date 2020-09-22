package model.charactertest;

import model.characters.Harry;
import model.characters.Hermione;
import model.characters.Ron;
import model.characters.Wizards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WizardsTest {

    private Wizards wizards;

    @BeforeEach
    public void runBefore() {
        wizards = new Wizards("wizard");
    }

    @Test
    public void testIncreaseMaxHP()  {
        wizards.setMaxHp(80);
        wizards.increaseMaxHP(10);
        assertEquals(90, wizards.getMaxHp());
    }

    @Test
    public void testDecreaseHPNotReachZero() {
        wizards.setHp(20);
        wizards.decreaseHP(10);
        assertEquals(10, wizards.getHp());
    }

    @Test
    public void testDecreaseHPReachZero() {
        wizards.setHp(5);
        wizards.decreaseHP(10);
        assertEquals(-5, wizards.getHp());
    }

    @Test
    public void testIncreaseATK() {
        wizards.setAtk(10);
        wizards.increaseATK(10);
        assertEquals(20, wizards.getAtk());
    }

    @Test
    public void testIsDead() { //!!!
        wizards.setHp(0);
        wizards.isDead();
        assertTrue(wizards.isDead);
    }

    @Test
    public void testIsDeadDead() {
        wizards.setHp(-5);
        wizards.isDead();
        assertTrue(wizards.isDead);
    }

    @Test
    public void testIsDeadAlive() {
        wizards.setHp(15);
        wizards.isDead();
        assertFalse(wizards.isDead);
    }

    @Test
    public void testName() {
        wizards.setName("test");
        assertEquals("test", wizards.getName());
    }

    @Test
    public void testharry() {
        Harry harry = new Harry("Harry");
        harry.setHp(20);
        harry.setAtk(10);
        harry.setMaxHp(30);
        harry.setDead(false);
        harry.setName("harry");
        assertEquals(20, harry.getHp());
        assertEquals(10, harry.getAtk());
        assertEquals(30, harry.getMaxHp());
        assertFalse(harry.isDead);
        assertEquals("harry", harry.getName());
    }

    @Test
    public void testRon() {
        Ron ron = new Ron("Ron");
        ron.setHp(20);
        ron.setAtk(10);
        ron.setMaxHp(30);
        ron.setDead(false);
        ron.setName("ron");
        assertEquals(20, ron.getHp());
        assertEquals(10, ron.getAtk());
        assertEquals(30, ron.getMaxHp());
        assertFalse(ron.isDead);
        assertEquals("ron", ron.getName());
    }

    @Test
    public void testHermione() {
        Hermione hermione = new Hermione("Hermione");
        hermione.setHp(20);
        hermione.setAtk(10);
        hermione.setMaxHp(30);
        hermione.setDead(false);
        hermione.setName("hermione");
        assertEquals(20, hermione.getHp());
        assertEquals(10, hermione.getAtk());
        assertEquals(30, hermione.getMaxHp());
        assertFalse(hermione.isDead);
        assertEquals("hermione", hermione.getName());
    }




}
