package game;

import game.gameOfLife.FieldInteraction;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FieldInteractionTest {

    private FieldInteraction fieldInteraction;

    @BeforeEach
    public void setUp() {
        String[][] initialField = {
                {".", "x", "a", "b"},
                {"c", "d", "e", "f"},
                {"g", "h", "i", "j"},
                {"k", "l", "m", "n"}
        };
        fieldInteraction = new FieldInteraction(initialField);
    }

    @ParameterizedTest
    @CsvSource({
            "x, 1, 0",
            "i, 2, 2",
            "n, -1, -1",
            "., 4, 0",
            "i, -2, -2",
    })
    public void testGetCellFromFieldIfItISInRangeOrOutOfIt(String expected, int x, int y) {
        assertEquals(expected, fieldInteraction.getCell(x, y));
    }

    @ParameterizedTest
    @CsvSource({
            "., 1, 0",
            "o, 2, 3",
            "x, -1, -1",
            "p, 6, 0",
            "i, -2, -2",
            "., 0, 0",
    })
    public void testSetCellForFieldIfItISInRangeOrOutOfIt(String expected, int x, int y) {
        fieldInteraction.setCell(x, y, expected);
        assertEquals(expected, fieldInteraction.getCell(x, y));
    }

    @Test
    public void testGetFieldHeightIfEverythingIsCorrect() {
        assertEquals(4, fieldInteraction.getHeight());
    }

    @Test
    public void testGetFieldWidthIfEverythingIsCorrect() {
        assertEquals(4, fieldInteraction.getWidth());
    }

    @Test
    public void testGetFieldIfEverythingIsCorrect() {
        String[][] expected = {
                {".", "x", "a", "b"},
                {"c", "d", "e", "f"},
                {"g", "h", "i", "j"},
                {"k", "l", "m", "n"}
        };
        assertArrayEquals(expected, fieldInteraction.getField());
    }

    @Test
    public void testGetFieldAfterModificationOfFirstCell() {
        fieldInteraction.setCell(0, 0, "q");
        String[][] expected = {
                {"q", "x", "a", "b"},
                {"c", "d", "e", "f"},
                {"g", "h", "i", "j"},
                {"k", "l", "m", "n"}
        };

        assertArrayEquals(expected, fieldInteraction.getField());
    }

    @Test
    public void testGetFieldAfterModificationOfNotFirstCell() {
        fieldInteraction.setCell(1, 2, "r");
        String[][] expected = {
                {".", "x", "a", "b"},
                {"c", "d", "e", "f"},
                {"g", "r", "i", "j"},
                {"k", "l", "m", "n"}
        };

        assertArrayEquals(expected, fieldInteraction.getField());
    }
}
