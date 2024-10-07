package game.gameOfLife;

public class GameOfLife {
    private final int height;
    private final int width;
    private FieldInteraction field;

    public GameOfLife(String[][] field) {
        this.height = field.length;
        this.width = field[0].length;
        this.field = new FieldInteraction(field);
    }

    public FieldInteraction getFinalResultField(int generations, boolean terminalDisplay) throws InterruptedException {
        for (int i = 0; i < generations; i++) {
            field = getNextGenerationField();
            if (terminalDisplay) {
                clearTerminal();
                field.printField();
                Thread.sleep(400);
            }
        }
        return field;
    }

    private FieldInteraction getNextGenerationField() {
        String[][] nextGenerationField = new String[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nextGenerationField[y][x] = getNextGenerationCell(x, y);
            }
        }
        return new FieldInteraction(nextGenerationField);
    }

    private String getNextGenerationCell(int x, int y) {
        int aliveNeighbours = countAliveNeighbours(x, y);
        boolean isAlive = field.getCell(x, y).equals("x");

        if (isAlive && (aliveNeighbours < 2 || aliveNeighbours > 3)) {
            return ".";
        } else if (!isAlive && aliveNeighbours == 3) {
            return "x";
        }
        return isAlive ? "x" : ".";
    }

    private int countAliveNeighbours(int x, int y) {
        int aliveCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (field.getCell(x + i, y + j).equals("x")) {
                    aliveCount++;
                }
            }
        }
        return aliveCount;
    }

    public static void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
