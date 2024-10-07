package game.gameOfLife;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriter {

    public static void writeToFile(String[][] field, String outputFilePath) throws IOException {
        String content = generateContent(field);
        Files.write(Paths.get(outputFilePath), content.getBytes());
    }

    public static String generateContent(String[][] field) {
        StringBuilder content = new StringBuilder();

        for (String[] row : field) {
            content.append(String.join("", row)).append(System.lineSeparator());
        }
        return content.toString();
    }
}
