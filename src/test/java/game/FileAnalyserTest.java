package game;

import game.gameOfLife.FileAnalyser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyserTest {
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete the temporary file
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testGetGenerationsIfValidDataInFile() throws IOException {
        String content = "3\n4 3\n..x.\nx...\n.x..\n";
        writeTempFile(content);

        FileAnalyser analyser = new FileAnalyser(tempFile.toString());

        assertEquals(3, analyser.getGenerations());
    }

    @Test
    void testGetWidthIfValidDataInFile() throws IOException {
        String content = "3\n4 3\n..x.\nx...\n.x..\n";
        writeTempFile(content);

        FileAnalyser analyser = new FileAnalyser(tempFile.toString());

        assertEquals(4, analyser.getWidth());
    }

    @Test
    void testGetHeightIfValidDataInFile() throws IOException {
        String content = "3\n4 3\n..x.\nx...\n.x..\n";
        writeTempFile(content);

        FileAnalyser analyser = new FileAnalyser(tempFile.toString());

        assertEquals(3, analyser.getHeight());
    }

    @Test
    void testGetFieldIfValidDataInFile() throws IOException {
        String content = "3\n4 3\n..x.\nx...\n.x..\n";
        writeTempFile(content);

        FileAnalyser analyser = new FileAnalyser(tempFile.toString());

        assertArrayEquals(new String[][]{
                {".", ".", "x", "."},
                {"x", ".", ".", "."},
                {".", "x", ".", "."},
        }, analyser.getField());
    }

    @Test
    void testGetFieldIfValidDataInFileButFieldIsSquare() throws IOException {
        String content = "3\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n";
        writeTempFile(content);

        FileAnalyser analyser = new FileAnalyser(tempFile.toString());

        assertArrayEquals(new String[][]{
                {".", ".", ".", ".", "."},
                {"x", ".", ".", ".", "x"},
                {".", "x", ".", ".", "x"},
                {".", ".", ".", "x", "."},
                {"x", "x", ".", ".", "."},
        }, analyser.getField());
    }

    @ParameterizedTest
    @CsvSource({
            "-1\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "a\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "a3\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3a\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "10 a\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
    })
    void testThrowsErrorIfGenerationsIsIncorrectInFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            "3\n0 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n-5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\nb 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\nb5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "10\n5b 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "10\n5 b 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
    })
    void testThrowsErrorIfWidthIsIncorrectInFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            "3\n5 0\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 -5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 c\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 c5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "10\n5 5c\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "10\n5 5 c\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 \n.....\nx...x\n.x..x\n...x.\nxx...\n",
    })
    void testThrowsErrorIfHeightIsIncorrectInFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            " 3\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3 \n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n 5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 5 \n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\n x...x\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\nx...x\n.x..x \n...x.\nxx...\n",
    })
    void testThrowsErrorIfLinesHasSomeUnnecessaryThingInFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @Test
    void testThrowsErrorIfSecondLineHasOnlyOneArgInFile() {
        String content = "3\n5\n.....\nx...x\n.x..x\n...x.\nxx...\n";
        writeTempFile(content);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            "\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n",
            "5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n.....\nx...x\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\n\n.x..x\n...x.\nxx...\n",
    })
    void testThrowsErrorIfLinesAreMissingInFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            "3\n5 5\n....\nx...\n.x..\n...x\nxx..\n",
            "3\n5 5\n.....x\nx...x.\n.x..xx\n...x..\nxx....\n",
            "3\n5 5\n.....\nx...x\n.x..\n...x.\nxx...\n",
    })
    void testThrowsErrorIfFieldHasWidthProblemsFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            "3\n5 5\n.....\nx...x\n.x..x\n...x.\n",
            "3\n5 5\n.....\nx...x\n.x..x\n...x.\nxx...\n..x.x\n",
    })
    void testThrowsErrorIfFieldHasHeightProblemsFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    @ParameterizedTest
    @CsvSource({
            "3\n5 5\n.....\nx...d\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\nx...xd\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\nx...x d\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\nd x...x\n.x..x\n...x.\nxx...\n",
            "3\n5 5\n.....\nd\n.x..x\n...x.\nxx...\n",
    })
    void testThrowsErrorIfFieldHasInvalidCharactersInFile(String string) {
        writeTempFile(string);

        assertThrows(IllegalArgumentException.class, () -> new FileAnalyser(tempFile.toString()));
    }

    private void writeTempFile(String content) {
        try {
            Files.writeString(tempFile, content);
        } catch (IOException e) {
            fail("Failed to write to temporary file: " + e.getMessage());
        }
    }
}
