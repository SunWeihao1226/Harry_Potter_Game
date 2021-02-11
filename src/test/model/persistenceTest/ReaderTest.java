package model.persistenceTest;

import model.Archive;
import model.SavingSlot;
import model.Spell;
import model.persistence.Reader;
import model.persistence.Writer;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    private Archive archive;
    private Writer writer;
    private Reader reader;
    private SavingSlot slot;
    private String fileName = "./data/readerTest.json";

    @BeforeEach
    public void runBefore() throws FileNotFoundException {
        archive = new Archive("archive", "wizard", "0", "0", "0");
        slot = new SavingSlot();
    }


    @Test
    public void testReadOneArchive() {
        try {
            slot.addArchives(archive);
            writer = new Writer(fileName);
            writer.write(slot);
        } catch (IOException e) {
            fail("fail to write");
        }
        try {
            reader = new Reader(fileName);
            slot = reader.read();
            assertEquals(1, slot.getSlot().size());
            assertTrue(slot.getSlot().containsKey("archive"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            fail("fail to parse");
        } catch (IOException e) {
            fail("fail to read");
        }
    }

    @Test
    public void testReadManyArchives() {
        Archive archive1 = new Archive("archive1", "wizard1", "0", "0", "0");
        Archive archive2 = new Archive("archive2", "wizard2", "0", "0", "0");
        Spell spell1 = new Spell("spell1", 10);
        Spell spell2 = new Spell("spell2", 20);
        Spell spell3 = new Spell("spell3", 30);
        archive1.addSpells(1, spell1);
        archive1.addSpells(2, spell2);
        archive1.addSpells(3, spell3);
        slot.addArchives(archive1);
        slot.addArchives(archive2);
        try {
            slot.addArchives(archive);
            writer = new Writer(fileName);
            writer.write(slot);
        } catch (IOException e) {
            fail("fail to write");
        }
        try {
            reader = new Reader(fileName);
            slot = reader.read();
            assertEquals(3, slot.getSlot().size());
            assertTrue(slot.getSlot().containsKey("archive"));
            assertTrue(slot.getSlot().containsKey("archive1"));
            assertTrue(slot.getSlot().containsKey("archive2"));
            assertFalse(slot.getSlot().containsKey("archive4"));
            Archive newArchive = slot.getArchive("archive1");
            assertEquals(3, newArchive.unlockedSpells.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            fail("fail to parse");
        } catch (IOException e) {
            fail("fail to read");
        }
    }

}
