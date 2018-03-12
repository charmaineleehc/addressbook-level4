package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandWordException;

//@author yuhongherald
/**
 * A serializable data structure used to contain the mappings of a command to a word
 */
public class CommandWords implements Serializable {
    public static final String MESSAGE_INACTIVE = "%s is not an active command.";
    public static final String MESSAGE_DUPLICATE = "%s is already used.";
    public final HashMap<String, String> commands;
    /**
     * Creates a data structure to maintain used command words.
     */
    public CommandWords() {
        commands = new HashMap<>();
        for (String command : Command.COMMANDS) {
            commands.put(command, command);
        }
    }

    public CommandWords(CommandWords commandWords) {
        requireNonNull(commandWords);
        commands = new HashMap<>();
        commands.putAll(commandWords.commands);
    }

    /**
     * Moves (@code command from (@code COMMANDS) to (@code verifiedCommands). Creates a new entry if missing.
     */
    private void moveVerifiedWord(String command, HashMap<String, String> verifiedCommands) {
        verifiedCommands.put(command, commands.getOrDefault(command, command));
    }

    /**
     * Checks if hashmap contains invalid command keys and adds any missing
     * command keys
     */
    public void checkIntegrity() {
        HashMap<String, String> verifiedCommands = new HashMap<>();
        for (String command : Command.COMMANDS) {
            moveVerifiedWord(command, verifiedCommands);
        }
        commands.clear();
        commands.putAll(verifiedCommands);
    }

    /**
     * Retrieves a command word using a key
     * @param key
     * @return command
     * @throws CommandWordException
     */
    public String getCommandWord(String key) throws CommandWordException {
        String commandWord = commands.get(key);
        if (commandWord == null) {
            throw new CommandWordException(String.format(MESSAGE_INACTIVE, key));
        }
        return commandWord;
    }

    /**
     * Retrieves a command key using word
     * @param value
     * @return command
     * @throws CommandWordException
     */
    public String getCommandKey(String value) throws CommandWordException {
        Iterator<Map.Entry<String, String>> commandList = commands.entrySet().iterator();
        Map.Entry<String, String> currentCommand;
        while (commandList.hasNext()) {
            currentCommand = commandList.next();
            if (currentCommand.getValue().equals(value)) {
                return currentCommand.getKey();
            }
        }
        throw new CommandWordException(String.format(MESSAGE_INACTIVE, value));
    }

    /**
     * Sets currentWord to newWord
     * @param currentWord Active command word to be replaced
     * @param newWord Command word to be replaced with
     * @throws CommandWordException currentWord is not valid
     */
    public void setCommandWord(String currentWord, String newWord) throws CommandWordException {
        requireNonNull(currentWord, newWord);
        if (currentWord.equals(newWord)) {
            return;
        }
        if (commands.containsValue(newWord)) {
            throw new CommandWordException(String.format(MESSAGE_DUPLICATE, newWord));
        }
        Iterator<Map.Entry<String, String>> commandList = commands.entrySet().iterator();
        Map.Entry<String, String> currentCommand;
        while (commandList.hasNext()) {
            currentCommand = commandList.next();
            if (currentCommand.getValue().equals(currentWord)) {
                commands.remove(currentCommand.getKey());
                commands.put(currentCommand.getKey(), newWord);
                return;
            }
        }
        StringBuilder builder = new StringBuilder();
        throw new CommandWordException(String.format(MESSAGE_INACTIVE, currentWord));
    }

    /**
     * Resets the existing data of this {@code CommandWords} with {@code newCommandWords}.
     */
    public void resetData(CommandWords newCommandWords) {
        requireNonNull(newCommandWords);
        commands.clear();
        commands.putAll(newCommandWords.commands);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Commands: \n");
        Iterator<Map.Entry<String, String>> commandList = commands.entrySet().iterator();
        Map.Entry<String, String> currentCommand;
        while (commandList.hasNext()) {
            currentCommand = commandList.next();
            builder.append(currentCommand.getKey() + ":" + currentCommand.getValue() + "\n");
        }
        return builder.toString();
    }

}
