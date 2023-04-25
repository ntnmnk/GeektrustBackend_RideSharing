package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.geektrust.backend.AppConfig.ApplicationConfig;
import com.geektrust.backend.Commands.CommandInvoker;
import com.geektrust.backend.Exceptions.CommandNotFoundException;
import com.geektrust.backend.GlobalConstants.Constants;

public class App {

    private final CommandInvoker commandInvoker;
    private final String inputFile;

   
    // To run the application ./gradlew run --args="input.txt"
    public App(CommandInvoker commandInvoker, String inputFile) {
        this.commandInvoker = commandInvoker;
        this.inputFile = inputFile;
    }
    public void run() {
        try {
            List<String> lines = readInputFile(inputFile);
            for (String line : lines) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(Constants.INPUT_FILE_ARG_INDEX), tokens);
            }
        } catch (IOException e) {
           
            System.out.println(Constants.COMMAND_NOT_FOUND_MESSAGE);
        } catch (CommandNotFoundException e) {
           
            System.out.println(Constants.COMMAND_NOT_FOUND_MESSAGE);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    private List<String> readInputFile(String inputFile) throws IOException {
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void main(String[] args) {
        if (args.length < Constants.INPUT_FILE_ARG) {
            throw new IllegalArgumentException("Missing input file argument");
        }
        ApplicationConfig appConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = appConfig.getCommandInvoker();
        App app = new App(commandInvoker, args[Constants.INPUT_FILE_ARG_INDEX]);
        app.run();
    }

}
