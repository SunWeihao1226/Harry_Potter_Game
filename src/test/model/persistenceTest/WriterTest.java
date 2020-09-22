package model.persistenceTest;

import model.Archive;
import model.SavingSlot;
import model.persistence.Reader;
import model.persistence.Writer;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {

    private Writer writer;
    private Writer writer2;
    private Archive archive;
    private SavingSlot slot;
    private String fileName ="./data/writerTest.json";

    @BeforeEach
    public void runBefore() throws IOException {
        writer = new Writer(fileName);
        archive = new Archive ("name", "wizard", "0", "0", "0");
        slot = new SavingSlot();
        slot.addArchives(archive);
    }

    @Test
    public void testWriteOneArchive() {
        try {
            writer.write(slot);
        } catch (IOException e) {
            fail("Unexpected exception");
        }
        try {
            Reader reader = new Reader(fileName);
            SavingSlot readSlot = reader.read();
            assertEquals(1, readSlot.getSlot().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            fail("fail to parse");
        } catch (IOException e) {
            fail("fail to read");
        }
    }

    @Test
    public void testWriteManyArchives() {
        Archive archive1 = new Archive("archive1", "wizard1", "0", "0", "0");
        Archive archive2 = new Archive("archive2", "wizard2", "0", "0", "0");
        slot.addArchives(archive1);
        slot.addArchives(archive2);
        try {
            writer.write(slot);
            writer.closeWriting();
        } catch (IOException e) {
            fail("Unexpected Exception");
        }

        try {
            Reader reader = new Reader(fileName);
            SavingSlot readSlot = reader.read();
            assertEquals(3, readSlot.getSlot().size());
            assertTrue(readSlot.getSlot().containsKey("name"));
            assertTrue(readSlot.getSlot().containsKey("archive1"));
            assertTrue(readSlot.getSlot().containsKey("archive2"));
            assertFalse(readSlot.getSlot().containsKey("archive3"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            fail("fail to parse");
        } catch (IOException e) {
            fail("fail to read");
        }
    }
}
