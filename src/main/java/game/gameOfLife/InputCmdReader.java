package game.gameOfLife;

public class InputCmdReader {

    private String inputFilePath;
    private String outputFilePath = "result.txt";
    private boolean terminalDisplay = false;

    public InputCmdReader(String[] args) {
        parseArgs(args);
    }

    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-input".equals(args[i])) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    inputFilePath = args[++i].trim();
                } else {
                    throw new IllegalArgumentException("No file path specified after \033[3m-input\033[0m.");
                }
            } else if ("-output".equals(args[i])) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    outputFilePath = args[++i].trim();
                }
            } else if ("-terminal_display".equals(args[i])) {
                if (i + 1 < args.length) {
                    String displayArg = args[++i].trim();
                    if ("true".equalsIgnoreCase(displayArg)) {
                        terminalDisplay = true;
                    } else if ("false".equalsIgnoreCase(displayArg)) {
                        terminalDisplay = false;
                    } else {
                        throw new IllegalArgumentException("You must specify \033[1mtrue\033[0m or \033[1mfalse\033[0m after \033[3m-terminal_display\033[0m.");
                    }
                } else {
                    terminalDisplay = false;
                }
            }
        }

        if (inputFilePath == null) {
            throw new IllegalArgumentException("You need to provide \033[3m-input <file_path>\033[0m because input file is required.");
        }
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public boolean isTerminalDisplay() {
        return terminalDisplay;
    }
}
