package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellTest {
    private Spell spell;

    @BeforeEach
    public void runBefore() {
        spell = new Spell("spell", 10);
    }

    @Test
    public void testSpellConstructor() {
        assertEquals("spell", spell.getSpellsName());
        assertEquals(10, spell.getAtk());
    }

    @Test
    public void testGetterSetter() {
        spell.setAtk(20);
        spell.setSpellsName("newspell");
        assertEquals(20, spell.getAtk());
        assertEquals("newspell", spell.getSpellsName());
    }
}
