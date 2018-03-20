package seedu.address.model.job;

//@author owzhenwei
/**
 * Represent a job number in the servicing manager
 */
public class JobNumber {
    private static int nextJobNumber;

    public final String jobNumber;

    public JobNumber() {
        jobNumber = Integer.toString(nextJobNumber);
        incrementNextJobNumber();
    }

    /**
     * Initialize the next job number of the car servicing manager
     */
    public static void initialize(String args) {
        nextJobNumber = Integer.parseInt(args);
    }

    public static void incrementNextJobNumber() {
        nextJobNumber++;
    }

    @Override
    public String toString() {
        return jobNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobNumber // instanceof handles nulls
                && this.jobNumber.equals(((JobNumber) other).jobNumber)); // state check
    }

    @Override
    public int hashCode() {
        return jobNumber.hashCode();
    }
}
