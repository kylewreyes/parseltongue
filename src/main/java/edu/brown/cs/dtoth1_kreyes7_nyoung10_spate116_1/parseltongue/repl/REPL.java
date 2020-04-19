package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.repl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * General purpose Read, Eval, Print, Loop that can read and write to the
 * console.
 *
 */
public class REPL {

  public static final String DECIMAL_PATTERN = "((\\-?)(\\d*\\.)?\\d+)";
  public static final String NON_NEG_DECIMAL_PATTERN = "((\\d*\\.)?\\d+)";

  /**
   * Command interface for different types of commands that will be given to the
   * REPL to execute.
   *
   */
  public interface Command {

    /**
     * Call execute on command.
     * @param args parameters to execute the command
     * @return String that REPL will print to console
     */
    String execute(String[] args);
  }

  private HashMap<String, HashMap<String, Command>> commands;
  private InputStreamReader source;
  private PrintWriter printWriter;

  /**
   * Creates new REPL object that initializes HashMap commands.
   *
   */
  public REPL() {
    commands = new HashMap<String, HashMap<String, Command>>();
    try {
      source = new InputStreamReader(System.in);
      printWriter = new PrintWriter(System.out);
    } catch (Exception e) {
      System.out.println("ERROR: Input incorrect");
    }
  }

  /**
   * Creates new REPL object that initializes HashMap commands.
   *
   * @param in  the inputstreamreader that the REPL will read from
   * @param out the printwriter that the REPL will print to
   *
   */
  public REPL(InputStreamReader in, PrintWriter out) {
    commands = new HashMap<String, HashMap<String, Command>>();
    source = in;
    printWriter = out;
  }

  /**
   * Registers a new command object with the REPL with a specific name and arguments.
   * @param commandName String representing name of the new command
   * @param commandArgs String representing REGEX pattern of arguments the command takes
   * @param func Command Object to be linked to the name and arguments
   */
  public void addCommand(String commandName, String commandArgs, Command func) {
    if (commands.containsKey(commandName)) {
      HashMap<String, Command> comms = commands.get(commandName);
      comms.put(commandArgs, func);
      commands.replace(commandName, comms);
    } else {
      HashMap<String, Command> newCommandArgs = new HashMap<String, Command>();
      newCommandArgs.put(commandArgs, func);
      commands.put(commandName, newCommandArgs);
    }
  }

  /**
   * Runs the CLI by taking in user input and executing the correct commands.
   */
  public void process() {
    try (BufferedReader br = new BufferedReader(source)) {
      String input;
      while ((input = br.readLine()) != null) {
        String commandName;
        String commandArgs;
        if (input.split(" ", 2).length == 2) {
          commandName = input.split(" ", 2)[0];
          commandArgs = input.split(" ", 2)[1];
        } else {
          commandName = input;
          commandArgs = "";
        }
        if (commands.containsKey(commandName)) {
          Map<String, Command> funcOptions = commands.get(commandName);
          List<Command> resultCommandList = funcOptions.entrySet().stream()
              .filter(argMap -> Pattern.matches(argMap.getKey(), commandArgs))
              .map(argMap -> argMap.getValue()).collect(Collectors.toList());
          if (resultCommandList.size() == 0) {
            printWriter.println(
                "ERROR: No valid argument patterns found for " + input);
          } else if (resultCommandList.size() > 1) {
            printWriter
                .println("ERROR: Multiple valid argument patterns found for "
                    + commandName);
          } else {
            Command resultCommand = resultCommandList.get(0);
            try {
              String[] parsedInput = parseRawCommandLine(commandArgs);
              String result = resultCommand.execute(parsedInput);
              if (!result.equals("")) {
                printWriter.println(result);
              }
            } catch (IllegalArgumentException e) {
              printWriter.println(e.getMessage());
            }
          }
        } else {
          printWriter.println("ERROR: Command not found");
        }
        printWriter.flush();
      }
      br.close();
    } catch (Exception e) {
      printWriter.println("ERROR: Input incorrect");
    }
  }

  /**
   * Takes in raw input from commandline and parses spaces and quotations. into
   * format which can be taken by Command Objects.
   *
   * @param rawInput raw command line string input
   * @return parsed String[] ready for use in Command Objects
   * @throws IllegalArgumentException when the input has multiple spaces in a row
   */
  public static String[] parseRawCommandLine(String rawInput)
      throws IllegalArgumentException {
    boolean inString = false;
    boolean justLeftString = false;
    List<String> output = new LinkedList<>();
    StringBuilder sb = new StringBuilder();
    for (char c : rawInput.toCharArray()) {
      if (c == '"') {
        if (inString) {
          output.add(sb.toString());
          sb.delete(0, sb.length());
          inString = false;
          justLeftString = true;
        } else if (sb.length() == 0) {
          inString = true;
        } else {
          inString = true;
          output.add(sb.toString());
          sb.delete(0, sb.length());
        }
      } else if (c == ' ') {
        if (inString) {
          sb.append(c);
        } else {
          if (sb.length() == 0) {
            if (!justLeftString) {
              throw new IllegalArgumentException(
                      "ERROR: Double spaces in input.");
            }
          } else {
            output.add(sb.toString());
            sb.delete(0, sb.length());
          }
        }
      } else {
        sb.append(c);
      }
    }
    if (sb.length() != 0) {
      output.add(sb.toString());
    }
    return output.toArray(new String[0]);
  }
}
