package model.charactertest;

import model.characters.Basilisk;
import model.characters.DeathEater;
import model.characters.Enemies;
import model.characters.Quirrell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemiesTest {

    private Enemies enemies;

    @BeforeEach
    public void runBefore() {
        enemies = new Enemies("enemy");
    }


    @Test
    public void testDecreaseHPNotReachZero() {
        enemies.setHp(20);
        enemies.decreaseHP(10);
        assertEquals(10, enemies.getHp());
    }

    @Test
    public void testDecreaseHPReachZero()  {
        enemies.setHp(5);
        enemies.decreaseHP(10);
        assertEquals(-5, enemies.getHp());
    }

    @Test
    public void testIsDead() {
        enemies.setHp(0);
        enemies.isDead();
        assertTrue(enemies.isDead);
    }

    @Test
    public void testIsDeadAlive() {
        enemies.setHp(15);
        enemies.isDead();
        assertFalse(enemies.isDead);
    }

    @Test
    public void testIsDeadDead() {
        enemies.setHp(-5);
        enemies.isDead();
        assertTrue(enemies.isDead);
    }

    @Test
    public void testName() {
        enemies.setName("test");
        assertEquals("test", enemies.getName());
    }

    @Test
    public void testQuir() {
        Quirrell quirrell = new Quirrell("quir");
        quirrell.setHp(20);
        quirrell.setAtk(10);
        quirrell.setName("quirrell");
        quirrell.setDead(true);
        assertTrue(quirrell.isDead);
        assertEquals(20, quirrell.getHp());
        assertEquals(10, quirrell.getAtk());
        assertEquals("quirrell", quirrell.getName());
    }

    @Test
    public void testDeathEater() {
        DeathEater deathEater = new DeathEater("DeathEater");
        deathEater.setHp(20);
        deathEater.setAtk(10);
        deathEater.setName("death eater");
        deathEater.setDead(true);
        assertTrue(deathEater.isDead);
        assertEquals(20, deathEater.getHp());
        assertEquals(10, deathEater.getAtk());
        assertEquals("death eater", deathEater.getName());
    }

    @Test
    public void testBasilisk() {
        Basilisk basilisk = new Basilisk("Basilisk");
        basilisk.setHp(20);
        basilisk.setAtk(10);
        basilisk.setName("basilisk");
        basilisk.setDead(true);
        assertTrue(basilisk.isDead);
        assertEquals(20, basilisk.getHp());
        assertEquals(10, basilisk.getAtk());
        assertEquals("basilisk", basilisk.getName());
    }
}
