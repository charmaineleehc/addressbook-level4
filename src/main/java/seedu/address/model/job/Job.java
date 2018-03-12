package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Employee;

//@author owzhenwei
/**
 * Represents a Job in the car servicing manager
 */
public class Job {
    private final Employee customer;
    private final VehicleNumber vehicleNumber;
    private final JobNumber jobNumber;

    public Job(Employee customer, VehicleNumber vehicleNumber, JobNumber jobNumber) {
        requireAllNonNull(customer, vehicleNumber);
        this.customer = customer;
        this.vehicleNumber = vehicleNumber;
        this.jobNumber = jobNumber;
    }

    public JobNumber getJobNumber() {
        return jobNumber;
    }

    public VehicleNumber getVehicleNumber() {
        return vehicleNumber;
    }

    public Employee getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return otherJob.getCustomer().equals(this.getCustomer())
                && otherJob.getVehicleNumber().equals(this.getVehicleNumber())
                && otherJob.getJobNumber() == this.getJobNumber();

    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, vehicleNumber, jobNumber);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Job Number: ")
                .append(getJobNumber())
                .append(" Vehicle ID: ")
                .append(getVehicleNumber())
                .append(" Name: ")
                .append(customer.getName())
                .append(" Email: ")
                .append(customer.getEmail());
        return builder.toString();
    }
}
