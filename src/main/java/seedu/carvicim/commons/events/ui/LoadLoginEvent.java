package seedu.carvicim.commons.events.ui;

import seedu.carvicim.commons.events.BaseEvent;

//@@author charmaineleehc
/**
 * Represents a selection change in the Job Display Panel
 */
public class LoadLoginEvent extends BaseEvent {

    private final String loginUrl;

    public LoadLoginEvent(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getAuthenticationUrl() {
        return loginUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
