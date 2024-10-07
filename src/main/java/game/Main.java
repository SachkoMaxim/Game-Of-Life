package game;

import game.gameOfLife.*;

public class Main {

    public static void main(String[] args) {
        try {
            InputCmdReader cmdReader = new InputCmdReader(args);

            FileAnalyser analyser = new FileAnalyser(cmdReader.getInputFilePath());
            GameOfLife gameOfLife = new GameOfLife(analyser.getField());

            String[][] finalField = gameOfLife.getFinalResultField(analyser.getGenerations(), cmdReader.isTerminalDisplay()).getField();

            FileWriter.writeToFile(finalField, cmdReader.getOutputFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
