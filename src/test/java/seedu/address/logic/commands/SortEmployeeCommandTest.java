package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookNonAlphabetically;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortEmployeeCommandTest {
    private Model model;
    private Model expectedModel;
    private SortEmployeeCommand sortEmployeeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookNonAlphabetically(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        sortEmployeeCommand = new SortEmployeeCommand();
        sortEmployeeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSuccess() {
        assertCommandSuccess(sortEmployeeCommand, model, SortEmployeeCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
