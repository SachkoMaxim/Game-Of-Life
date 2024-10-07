package game;

import game.gameOfLife.InputCmdReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class InputCmdReaderTest {

    @ParameterizedTest
    @CsvSource({
            "-input, input.txt",
            "-input, my.txt",
            "-input, input.java",
            "-input, '  input.txt'",
    })
    void testGetInputFilePathIfInputCommandPassed(String arg1, String expected) {
        String[] args = {arg1, expected};
        InputCmdReader cmdReader = new InputCmdReader(args);

        assertEquals(expected.trim(), cmdReader.getInputFilePath());
    }

    @ParameterizedTest
    @CsvSource({
            "-input, input.txt, -output, output.txt",
            "-input, my.txt, -output, your.txt",
            "-input, input.java, -output, output.java",
            "-input, input.txt, -output, '  output.txt'",
    })
    void testGetOutputFilePathIfOutputCommandPassed(String arg1, String arg2, String arg3, String expected) {
        String[] args = {arg1, arg2, arg3, expected};
        InputCmdReader cmdReader = new InputCmdReader(args);

        assertEquals(expected.trim(), cmdReader.getOutputFilePath());
    }

    @ParameterizedTest
    @CsvSource({
            "-input, input.txt, -terminal_display, true",
            "-input, my.txt, -terminal_display, false",
            "-input, my.java, -terminal_display, '  true'",
            "-input, input.txt, -terminal_display, '  false'",
    })
    void testGetTermDisplayIfTermDisplayCommandPassed(String arg1, String arg2, String arg3, String expected) {
        String[] args = {arg1, arg2, arg3, expected};
        InputCmdReader cmdReader = new InputCmdReader(args);

        assertEquals(Boolean.parseBoolean(expected.trim()), cmdReader.isTerminalDisplay());
    }

    @Test
    void testGetReadyOutputFilePathIfNoOutputCommandPassed() {
        String[] args = {"-input", "input.txt"};
        InputCmdReader cmdReader = new InputCmdReader(args);

        assertEquals("result.txt", cmdReader.getOutputFilePath());
    }

    @Test
    void testGetFalseTerminalDisplayIfNoTerminalDisplayCommandPassed() {
        String[] args = { "-input", "input.txt"};
        InputCmdReader cmdReader = new InputCmdReader(args);

        assertFalse(cmdReader.isTerminalDisplay());
    }

    @Test
    void testGetFalseTerminalDisplayIfTermDisplayCommandHasSpace() {
        String[] args = {"-input", "input.txt", "-terminal_display   "};
        InputCmdReader cmdReader = new InputCmdReader(args);

        assertFalse(cmdReader.isTerminalDisplay());
    }

    @Test
    void testThrowsErrorIfNoCommandsPassed() {
        assertThrows(IllegalArgumentException.class, () -> new InputCmdReader(new String[] {}));
    }

    @Test
    void testThrowsErrorIfInputCommandNotPassed() {
        String[] args = {"-output", "output.txt"};
        assertThrows(IllegalArgumentException.class, () -> new InputCmdReader(args));
    }

    @Test
    void testThrowsErrorIfNoFilePathPassedAfterInputCommand() {
        String[] args = {"-input"};
        assertThrows(IllegalArgumentException.class, () -> new InputCmdReader(args));
    }

    @Test
    void testThrowsErrorIfNoFilePathPassedAfterInputWithOtherCommands() {
        String[] args = {"-input", "-output", "output.txt"};
        assertThrows(IllegalArgumentException.class, () -> new InputCmdReader(args));
    }

    @Test
    void testThrowsErrorIfInvalidTermDisplayCommandPassed() {
        String[] args = {"-input", "input.txt", "-terminal_display", "maybe"};
        assertThrows(IllegalArgumentException.class, () -> new InputCmdReader(args));
    }
}
