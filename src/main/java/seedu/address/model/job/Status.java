package seedu.address.model.job;

/**
 * Represents the status of a car servicing job
 */
public class Status {
    public static final String STATUS_ONGOING = "ongoing";
    public static final String STATUS_CLOSED = "closed";

    public final String status;

    public Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
