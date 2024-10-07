package game;

import game.gameOfLife.FileWriter;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    @Test
    void testWriteToFileIfDataIsCorrect() {
        String[][] field = {
                {".", "x",},
                {".", ".",},
        };

        String expectedContent = ".x" + System.lineSeparator() +
                ".." + System.lineSeparator();

        String actualContent = FileWriter.generateContent(field);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testWriteFieldIfDataHasOneCell() {
        String[][] field = {{"."}};

        String expectedContent = "." + System.lineSeparator();
        String actualContent = FileWriter.generateContent(field);

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testShowIOExceptionIfPathIsNotReachable() {
        String[][] field = {{"x"}};
        String invalidOutputPath = "/invalid_path/output.txt";

        IOException exception = assertThrows(IOException.class, () -> {
            FileWriter.writeToFile(field, invalidOutputPath);
        });

        assertNotNull(exception);
    }
}
