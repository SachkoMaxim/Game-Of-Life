package game.gameOfLife;

public class FieldInteraction {
    private final String[][] field;
    private final int height;
    private final int width;

    public FieldInteraction(String[][] field) {
        this.field = field;
        this.height = field.length;
        this.width = field[0].length;
    }

    private int getWrappedIndex(int index, int max) {
        return (index % max + max) % max;
    }

    public String getCell(int x, int y) {
        int wrappedX = getWrappedIndex(x, width);
        int wrappedY = getWrappedIndex(y, height);
        return field[wrappedY][wrappedX];
    }

    public void setCell(int x, int y, String value) {
        int wrappedX = getWrappedIndex(x, width);
        int wrappedY = getWrappedIndex(y, height);
        field[wrappedY][wrappedX] = String.valueOf(value);
    }

    public void printField() {
        for (String[] row : field) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    public String[][] getField() {
        return field;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
