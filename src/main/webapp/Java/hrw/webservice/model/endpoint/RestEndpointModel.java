package hrw.webservice.model.endpoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST endpoint model with the description for the Swagger-UI.
 * Model holds all values that the web service can return.
 *
 * @author Lars Karbach, Furkan Kilic
 * @version 1.0
 * @since 2021-02-16
 */
@ApiModel(description = "Details about the Endpoints")
public class RestEndpointModel {
    /**
     * Holds the calculated incidence value.
     */
    @ApiModelProperty(notes = "Incidences")
    private double incidence;

    /**
     * Holds the calculated target infection.
     */
    @ApiModelProperty(notes = "target infection")
    private double targetInfection;

    /**
     * Holds the calculated number of remaining lockdown days.
     */
    @ApiModelProperty(notes = "remaining lockdown days")
    private double remainingLockdown;

    /**
     * Holds the calculated number of new infections in the last 24 hours.
     */
    @ApiModelProperty(notes = "new infections in the last 24 hours")
    private int newInfections24H;

    /**
     * Holds the calculated number of total infections.
     */
    @ApiModelProperty(notes = "total infections")
    private int totalInfections;

    /**
     * Holds the calculated number of increased infections.
     */
    @ApiModelProperty(notes = "raised infections")
    private int raisedInfections;

    /**
     * Holds the average increase in infections for n days.
     */
    @ApiModelProperty(notes = "average raise of the infections")
    private double averageRaise;

    /**
     * Standard constructor RestEndpointModel
     */
    public RestEndpointModel() {

    }

    /**
     * Constructor of RestEndpointModel
     *
     * @param incidence represents incidence value
     */
    public RestEndpointModel(double incidence) {
        this.incidence = incidence;
    }

    /**
     * Basic getter to return incidence
     *
     * @return incidence
     */
    public double getIncidence() {
        return incidence;
    }

    /**
     * Sets the incidence value.
     *
     * @param incidence calculated incidence value.
     */
    public void setIncidence(double incidence) {
        this.incidence = incidence;
    }

    /**
     * Basic getter to return target infection
     *
     * @return target infection
     */
    public double getTargetInfection() {
        return targetInfection;
    }

    /**
     * Sets the target infections value.
     *
     * @param targetInfection calculated target infections value.
     */
    public void setTargetInfection(double targetInfection) {
        this.targetInfection = targetInfection;
    }

    /**
     * Basic getter to return remaining lockdown days
     *
     * @return remaining lockdown days
     */
    public double getRemainingLockdown() {
        return remainingLockdown;
    }

    /**
     * Sets the number of remaining lockdown days.
     *
     * @param remainingLockdown calculated number of remaining lockdown days.
     */
    public void setRemainingLockdown(double remainingLockdown) {
        this.remainingLockdown = remainingLockdown;
    }

    /**
     * Basic getter to return new infections in last 24h
     *
     * @return new infections in last 24h
     */
    public int getNewInfections24H() {
        return newInfections24H;
    }

    /**
     * Sets the number of new infections in 24 hours.
     *
     * @param newInfections24H calculated number of new infections in 24 hours.
     */
    public void setNewInfections24H(int newInfections24H) {
        this.newInfections24H = newInfections24H;
    }

    /**
     * Basic getter to return total infections
     *
     * @return total infections
     */
    public int getTotalInfections() {
        return totalInfections;
    }

    /**
     * Sets the number of total infections.
     *
     * @param totalInfections calculated number of total infections.
     */
    public void setTotalInfections(int totalInfections) {
        this.totalInfections = totalInfections;
    }

    /**
     * Basic getter to return raised infections
     *
     * @return raised infections
     */
    public int getRaisedInfections() {
        return raisedInfections;
    }

    /**
     * Sets the increase of infections.
     *
     * @param raisedInfections calculated increase of infections.
     */
    public void setRaisedInfections(int raisedInfections) {
        this.raisedInfections = raisedInfections;
    }

    /**
     * Basic getter to return average raise
     *
     * @return average raise
     */
    public double getAverageRaise() {
        return averageRaise;
    }

    /**
     * Sets the average increase of new infection for n days.
     *
     * @param averageRaise calculated average increase of new infection.
     */
    public void setAverageRaise(double averageRaise) {
        this.averageRaise = averageRaise;
    }
}