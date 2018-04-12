package seedu.carvicim.commons.events.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import seedu.carvicim.commons.core.LogsCenter;
import seedu.carvicim.commons.events.BaseEvent;
import seedu.carvicim.model.job.Job;

import java.util.logging.Logger;

//@@author whenzei
/**
 * Indicates a request to update the job display panel
 */
public class JobDisplayPanelUpdateRequestEvent extends BaseEvent {

    private final Job job;

    public JobDisplayPanelUpdateRequestEvent(Job job) {
        this.job = job;
    }

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Job getJob() {
        return this.job;
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    @Subscribe
    private void loadLoginUrl(LoadLoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(event.getAuthenticationUrl());
    }

    @Subscribe
    private void getRedirectUrlEvent(GetRedirectUrlEvent event) {
        logger.info((LogsCenter.getEventHandlingLogMessage(event)));
        event.setRedirectUrl(browser.getEngine().getLocation());
    }
}
