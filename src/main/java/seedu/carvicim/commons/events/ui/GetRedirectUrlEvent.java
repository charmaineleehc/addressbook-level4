package seedu.carvicim.commons.events.ui;

import seedu.carvicim.commons.events.BaseEvent;

//@@author charmaineleehc
/**
 * Represents a selection change in the Job Display Panel
 */
public class GetRedirectUrlEvent extends BaseEvent {

    private String redirectUrl;

    public GetRedirectUrlEvent() {
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
