import Interfaces.IChecker;
import Interfaces.IInputProcessor;
import Interfaces.IOutputProcessor;
import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

@CommandLine.Command(
        name = "java -jar checker-0.1-all.jar",
        description = "Checks your steam accounts from stdin or files and outputs them to stdout or files",
        version = "Steam Checker Alpha v0.1",
        footer = "by Vekotov & karnain1803 (C)"
)
public class Main implements Runnable {
    @CommandLine.Option(
            names = {"-i", "--input"},
            description = "Input file with steam accounts, format is login:pass. If none - reading from stdin."
    )
    private String inputFile;

    @CommandLine.Option(
            names = {"-g", "--good"},
            description = "Output file with good steam accounts. If none - output to stdout."
    )
    private String goodOutputFile;

    @CommandLine.Option(
            names = {"-b", "--bad"},
            description = "Output file with bad steam accounts. If none - output to stdout."
    )
    private String badOutputFile;

    @CommandLine.Option(
            names = {"-o", "--output"},
            description = "Output file with results, same as setting both --good and --bad to same file."
    )
    private String allOutputFile;

    @CommandLine.Option(
            names = {"-h", "--help", "-?", "-help"},
            usageHelp = true,
            description = "Displays this help and exits."
    )
    private boolean help;

    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }

    @Override
    public void run() {
        // TODO: Refactor this

        Scanner inputScanner;
        if (inputFile == null) {
           inputScanner = new Scanner(System.in);
        } else {
            try {
                inputScanner = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("Error! Input file not found!");
                return;
            }
        }
        IInputProcessor inputProcessor = new InputProcessor(inputScanner);
        IChecker checker = new Checker();


        PrintStream goodOutput;
        PrintStream badOutput;

        if (allOutputFile != null) { // if one file
            try {
                goodOutput = new PrintStream(allOutputFile);
            } catch (FileNotFoundException e) {
                System.out.println("Error on creating output file! (" + allOutputFile + ")");
                return;
            }
            badOutput = goodOutput;
        } else if (goodOutputFile != null && goodOutputFile.equals(badOutputFile)) { // if filenames same
            try {
                goodOutput = new PrintStream(goodOutputFile);
            } catch (FileNotFoundException e) {
                System.out.println("Error on creating output file! (" + goodOutputFile + ")");
                return;
            }
            badOutput = goodOutput;
        } else {
            if (goodOutputFile != null) {
                try {
                    goodOutput = new PrintStream(goodOutputFile);
                } catch (FileNotFoundException e) {
                    System.out.println("Error on creating good output file! (" + goodOutputFile + ")");
                    return;
                }
            } else {
                goodOutput = System.out;
            }

            if (badOutputFile != null) {
                try {
                    badOutput = new PrintStream(badOutputFile);
                } catch (FileNotFoundException e) {
                    System.out.println("Error on creating bad output file! (" + badOutputFile + ")");
                    return;
                }
            } else {
                badOutput = System.out;
            }
        }

        IOutputProcessor outputProcessor = new OutputProcessor(goodOutput, badOutput);

        outputProcessor.processResultToOutput(checker.checkUsers(inputProcessor.getProcessedInputStream()));
    }
}
