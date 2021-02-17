package hrw.webservice.model;

/**
 * Attributes for the Robert Koch-Institut Coronavirus data
 * All figures refer to Germany
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */

public class RKIDailyInfosTotal {
    private double incidenceGermany;
    private double targetInfection;
    private int remainingLock;
    private int sumCases;

    /**
     * @param incidenceGermany incidence calculation for germany
     * @param sumCases         sum of cases
     */
    public RKIDailyInfosTotal(double incidenceGermany, int sumCases) {
        this.incidenceGermany = incidenceGermany;
        this.sumCases = sumCases;
    }

    /**
     * getter to return the incidence calculation for germany
     *
     * @return incidenceGermany
     */
    public double getIncidenceGermany() {
        return incidenceGermany;
    }

    /**
     * Basic getter to return target infection
     *
     * @return target infection value
     */
    public double getTargetInfection() {
        return targetInfection;
    }
}
