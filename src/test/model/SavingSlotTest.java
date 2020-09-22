package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SavingSlotTest {

    private SavingSlot slot;
    private Archive archive = new Archive("archive");

    @BeforeEach
    public void runBefore() {
        slot = new SavingSlot();
    }

    @Test
    public void testAddArchive() {
        slot.addArchives(archive);
        assertTrue(slot.slot.containsValue(archive));
    }

    @Test
    public void testRemoveArchive() {
        slot.addArchives(archive);
        slot.removeArchive(archive);
        assertTrue(slot.slot.isEmpty());
    }

    @Test
    public void testGetterSetter() {
        slot.addArchives(archive);
        assertEquals(archive, slot.getArchive("archive"));
        assertEquals(1, slot.getSize());
    }

    @Test
    public void testContainsArchive() {
        slot.addArchives(archive);
        assertTrue(slot.containsArchive("archive"));
    }

    @Test
    public void testGetNameSet() {
        slot.addArchives(archive);
        assertTrue(slot.getNamesSet().contains("archive"));
        Archive archive1 = new Archive("archive1");
        slot.addArchives(archive1);
        assertTrue(slot.getNamesSet().contains("archive1"));
    }


}
