package model;

import model.characters.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for the Battle class
class ArchiveTest {
    private Archive archive;
    private List<String> spells;
    private Battle battle;
    Harry harry = new Harry("Harry");
    Quirrell quir = new Quirrell("Quirrel");
    Spell expelliarmus = new Spell("Expelliarmus", 10);

    @BeforeEach
    public void runBefore() {
        archive = new Archive("archive_1");
    }

    @Test
    public void testSelectWizard() {
        archive.selectWizard(harry);
        assertEquals(harry, archive.getSelectedWizard());
    }

    @Test
    public void testConstructor() {
        Archive archive2 = new Archive("archive2", "wizard", "0", "10", "20");
        assertEquals("archive2", archive2.getArchiveName());
        assertEquals("wizard", archive2.getSelectedWizard().getName());
        assertEquals(0, archive2.getCheckPoint());
        assertEquals(10, archive2.getSelectedWizard().getMaxHp());
        assertEquals(20, archive2.getSelectedWizard().getAtk());
    }

    @Test
    public void testAddSpells() {
        archive.addSpells(1, expelliarmus);
        assertEquals(expelliarmus, archive.getSpells(1));
    }

    @Test
    public void testGetSpells() {
        archive.addSpells(1,expelliarmus);
        assertTrue(archive.gottenSpells.containsValue(expelliarmus));
    }


    @Test
    public void testAddBattle() {
        battle = new Battle("1", harry, quir);
        archive.addBattle(battle);
        assertTrue(archive.battles.contains(battle));
    }

    @Test
    public void testArchiveName() {
        archive.setArchiveName("archive_test");
        assertEquals("archive_test", archive.getArchiveName());
    }

    @Test
    public void testCheckPoint() {
        archive.setCheckPoint(3);
        assertEquals(3, archive.getCheckPoint());
    }

    @Test
    public void testSectedWizard() {
        Wizards herm = new Hermione("herm");
        archive.setSelectedWizard(herm);
        assertEquals(herm, archive.getSelectedWizard());
    }

    @Test
    public void testSave() {
        SavingSlot slot = new SavingSlot();
        archive.save(slot);
        assertEquals(1, slot.getSize());
        assertTrue(slot.getSlot().containsKey(archive.archiveName));
        archive.save(slot);
        assertEquals(1, slot.getSize());
        assertTrue(slot.getSlot().containsKey(archive.archiveName));
    }

    @Test
    public void testToWrite() {
        archive.addSpells(1, expelliarmus);
        JSONObject object = archive.toWrite();
        assertTrue(object.containsValue(archive.getArchiveName()));
        List<String> list = (List<String>) object.get("Unlocked Spells");
        assertEquals("1. " + expelliarmus.getSpellsName() + "." + expelliarmus.getAtk(), list.get(0));
    }

    @Test
    public void testEquals() {
        Archive archive2 = new Archive("archive2");
        assertFalse(archive.equals(archive2));
    }







}