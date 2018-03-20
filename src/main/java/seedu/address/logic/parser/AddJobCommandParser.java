package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNED_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VEHICLE_NUMBER;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author whenzei
/**
 * Parses the input arguments and creates a new AddJobCommand object
 */
public class AddJobCommandParser implements Parser<AddJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddJobCommand
     * and returns an AddJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_VEHICLE_NUMBER, PREFIX_ASSIGNED_EMPLOYEE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_VEHICLE_NUMBER, PREFIX_ASSIGNED_EMPLOYEE)) {
        }

        //More to be added
        return null; //Stub
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
