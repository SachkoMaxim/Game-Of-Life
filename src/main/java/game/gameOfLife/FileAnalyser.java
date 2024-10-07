package game.gameOfLife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileAnalyser {

    private int generations;
    private int width;
    private int height;
    private String[][] field;

    public FileAnalyser(String inputFilePath) throws IOException {
        analyzeFile(inputFilePath);
    }

    void analyzeFile(String inputFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String generationsLine = reader.readLine();
            if (generationsLine == null) {
                throw new IllegalArgumentException("File is missing the number of generations.");
            }

            generations = Integer.parseInt(generationsLine);
            if (generations < 0) {
                throw new IllegalArgumentException("Number of generations cannot be negative.");
            }

            String dimensionsLine = reader.readLine();
            if (dimensionsLine == null || !dimensionsLine.matches("\\d+ \\d+")) {
                throw new IllegalArgumentException("Second line must contain only two positive integers (width and height).");
            }

            String[] dimensions = dimensionsLine.split(" ");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);

            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive numbers.");
            }

            field = new String[height][width];

            for (int i = 0; i < height; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IllegalArgumentException("Field has fewer rows than expected.");
                }

                if (line.length() != width) {
                    throw new IllegalArgumentException("Field width doesn't match the specified width.");
                }

                if (!line.matches("[.x]+")) {
                    throw new IllegalArgumentException("Field contains invalid characters. Only '.' and 'x' are allowed.");
                }

                field[i] = line.split("");
            }

            if (reader.readLine() != null) {
                throw new IllegalArgumentException("Field has more rows than expected.");
            }
        }
    }

    public int getGenerations() {
        return generations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String[][] getField() {
        return field;
    }
}
