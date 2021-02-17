package hrw.webservice.logic;

import hrw.webservice.model.Attributes;
import hrw.webservice.model.RKIDailyInfosTotal;

import java.util.List;

/**
 * Class calculates the data provided by Robert Koch Institute
 * All figures refer to Germany
 * Infection incidences
 * Remaining Lockdown days
 * Target Infection count
 *
 * @author Lars Karbach, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */
public class CalculateRKIKeyFigures {

    /**
     * Pre-calculations to offset the data obtained for each state, to total values.
     *
     * @param dailyStateList List contains all daily data for each state.
     *                       The more detailed description of the contained properties, can be found in the Attributes class.
     * @return The RKI daily data for all of Germany. (RKIDailyInfosTotal)
     */
    public RKIDailyInfosTotal preCalcRkiValues(List<Attributes> dailyStateList) {
        // Incidence value for all of Germany
        double incidenceGermany;
        // Summation of german citizens
        int sumCitizens = 0;
        // Summation of cases within 7 days
        int sumCases7Bl = 0;
        // Summation of cases for all of Germany
        int sumCases = 0;

        // For-each sums up each state to a value for all of Germany.
        for (Attributes item : dailyStateList) {
            sumCases7Bl += item.getCases7bl();
            sumCitizens += item.getCitizens();
            sumCases += item.getCases();
        }

        // Daily incidence calculation for Germany
        incidenceGermany = calcIncidenceGermany(sumCitizens, sumCases7Bl);

        return new RKIDailyInfosTotal(incidenceGermany, sumCases);
    }

    /**
     * Realizes task 2.a of the task description.
     * Method calculates the incidence value for all of Germany,
     * using the formula provided by the Robert Koch Institute (sum of cases in 7 days / number of inhabitants of Germany ) * 100000.
     *
     * @param sumCitizens Number of citizens for all of Germany
     * @param sumCasesBl  Number of cases within 7 days nationwide
     * @return Incidence value nationwide (Double)
     */
    private double calcIncidenceGermany(int sumCitizens, int sumCasesBl) {
        return (((double) sumCasesBl) / sumCitizens) * 100000;

    }

    /**
     * Realizes task 2.c of the task description.
     * Method calculates the expected remaining lockdown in Germany.
     *
     * @param avgDecrease      Average infections decrease of n days
     * @param targetInfections Number of target infection
     * @param trueInfections   Number of genuinely infections
     * @return remaining lockdown days (Double)
     */
    public double calcRemLockdown(double avgDecrease, double targetInfections, int trueInfections) {
        return ((double) (trueInfections - targetInfections)) / avgDecrease;
    }

    /**
     * Realizes task 2.b of the task description.
     * Method calculates the target total infection of Germany.
     * More precisely, this value describes from which incidence value, which number of infected persons is reached.
     *
     * @param sumCases         Number of all cases within Germany
     * @param incidenceGermany Today's incidence value
     * @return Number of target infection (Double)
     */
    public double calcTargetInfection(int sumCases, double incidenceGermany) {
        System.out.println((((double) sumCases) / incidenceGermany) * 35);
        return (((double) sumCases) / incidenceGermany) * 35;
    }
}