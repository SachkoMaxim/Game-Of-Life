package game;

import game.gameOfLife.GameOfLife;
import game.gameOfLife.FieldInteraction;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GameOfLifeTest {

    private GameOfLife gameOfLife;

    @ParameterizedTest
    @CsvSource({
            "., 1, 0",
            "., 0, 1",
            "x, 1, 1",
            "., 2, 2",
    })
    public void testGetFieldCellAfterOneGeneration(String expected, int x, int y) throws InterruptedException {
        String[][] initialField = createField(
                ".x.., x..., ..x., ...."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);
        assertEquals(expected, result.getCell(x, y));
    }

    @Test
    public void testGetTheSameFieldAfterZeroGenerations() throws InterruptedException {
        String[][] initialField = createField(
                ".x.., x..., ..x., ...."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(0, false);

        assertArrayEquals(initialField, result.getField());
    }

    @Test
    public void testGetFieldAfterOneGeneration() throws InterruptedException {
        String[][] initialField = createField(
                ".x.., x..., ..x., ...."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);

        String[][] expectedField = createField(
                "...., .x.., ...., ...."
        );

        assertArrayEquals(expectedField, result.getField());
    }

    @Test
    public void testGetDeadFieldIfFieldIsDeadAlready() throws InterruptedException {
        String[][] initialField = createField(
                "...., ...., ...., ...."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);

        assertArrayEquals(initialField, result.getField());
    }

    @ParameterizedTest
    @CsvSource({
            "'...., x..., ...., ....'",
            "'...., x... ,..x., ....'",
            "'...x, x..., ..x., ....'",
            "'x..x, xx.., ..xx, ....'",
            "'x..x, xx.., .xxx, ....'",
            "'xx.x, xx.., .xxx, ....'",
            "'xx.x, xx.x, .xxx, ....'",
            "'xx.x, xx.x, xxxx, ....'",
    })
    public void testGetDeadCellIfItDoesNotHaveTwoOrThreeNeighbours(String initialFieldStr) throws InterruptedException {
        String[][] initialField = createField(initialFieldStr);
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);

        assertEquals(".", result.getCell(0, 1));
    }

    @ParameterizedTest
    @CsvSource({
            "'x..x, x..., ..x., ....'",
            "'x..x, xx.., ..x., ....'",
    })
    public void testGetAliveCellIfItHasTwoOrThreeNeighbours(String initialFieldStr) throws InterruptedException {
        String[][] initialField = createField(initialFieldStr);
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);

        assertEquals("x", result.getCell(0, 1));
    }

    @ParameterizedTest
    @CsvSource({
            "'...., ...., ...., ....'",
            "'...., .... ,..x., ....'",
            "'...x, ...., ..x., ....'",
            "'x..x, ...., ..x., ....'",
            "'x..x, .x.., ..xx, ....'",
            "'x..x, .x.., .xxx, ....'",
            "'xx.x, .x.., .xxx, ....'",
            "'xx.x, .x.x, .xxx, ....'",
            "'xx.x, .x.x, xxxx, ....'",
    })
    public void testGetStillDeadCellIfItDoesNotHaveThreeNeighbours(String initialFieldStr) throws InterruptedException {
        String[][] initialField = createField(initialFieldStr);
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);

        assertEquals(".", result.getCell(0, 1));
    }

    @ParameterizedTest
    @CsvSource({
            "'x..x, .x.., ..x., ....'",
    })
    public void testGetNewlyAliveCellIfItHasThreeNeighbours(String initialFieldStr) throws InterruptedException {
        String[][] initialField = createField(initialFieldStr);
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(1, false);

        assertEquals("x", result.getCell(0, 1));
    }

    @Test
    public void testGetFieldAfterTwoGenerations() throws InterruptedException {
        String[][] initialField = createField(
                "...., .x.., .x.., .x.."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(2, false);

        assertArrayEquals(initialField, result.getField());
    }

    @Test
    public void testGetFieldAfterThreeGenerations() throws InterruptedException {
        String[][] initialField = createField(
                "...., .x.., .x.., .x.."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(3, false);

        String[][] expectedField = createField(
                "...., ...., xxx., ...."
        );

        assertArrayEquals(expectedField, result.getField());
    }

    @Test
    void testGetStaticAndSameBlockAfterFewGenerations() throws InterruptedException {
        String[][] initialField = createField(
                "...., .xx., .xx., ...."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(5, false);

        assertArrayEquals(initialField, result.getField());
    }

    @Test
    public void testGetALittleHarderFieldAfterThreeGenerations() throws InterruptedException {
        String[][] initialField = createField(
                "xx.., .x.., ...., ...x"
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(3, false);

        String[][] expectedField = createField(
                "..x., x.x., ...., x.xx"
        );

        assertArrayEquals(expectedField, result.getField());
    }

    @Test
    public void testGetAMuchHarderFieldAfterFewGenerations() throws InterruptedException {
        String[][] initialField = createField(
                "xx......, ....xx.., ......x., ..xx...., .x...x.."
        );
        gameOfLife = new GameOfLife(initialField);

        FieldInteraction result = gameOfLife.getFinalResultField(10, false);

        String[][] expectedField = createField(
                ".x..x..x, .x......, ....x..., .xx.xx.., ..x..x.x"
        );

        assertArrayEquals(expectedField, result.getField());
    }

    private String[][] createField(String fieldStr) {
        String[] rows = fieldStr.split(", ");
        String[][] field = new String[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            field[i] = rows[i].split("");
        }
        return field;
    }
}
