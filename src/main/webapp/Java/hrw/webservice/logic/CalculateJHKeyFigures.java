package hrw.webservice.logic;

import hrw.webservice.model.JohnHDailyInfos;

import java.util.List;
import java.io.*;

/**
 * Class calculates the data provided by John Hopkins University
 * All figures refer to Germany
 * Key calculations:
 * new infections in the last 24h
 * total infections
 * increase in the last 24h
 * average increase in the last n day
 *
 * @author Lars Karbach, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */

public class CalculateJHKeyFigures {
    /**
     * Integer variable describes the new infections in all of Germany within 24 hours
     */
    private int newInf24H;

    /**
     * Integer variable describes the increase in infections in all of Germany within 24 hours
     */
    private int raisedInf24H;

    /**
     * Integer Variable describes the number of infected persons within Germany.
     * The value results from the total number of infections - deaths - recovered.
     */
    private int trueInfected;

    /**
     * UpdateKeyFigures realizes an initial calculation of the new infections in 24 hours,
     * just infected persons in Germany and the increase of infections in Germany.
     *
     * @param dailyInfosList The list represents all days since 22.01.2020.
     *                       A detailed description can be found in the JohnHDailyInfos class.
     */
    public void updateKeyFigures(List<JohnHDailyInfos> dailyInfosList) {
        this.newInf24H = calcNewInf24H(dailyInfosList, 1);
        this.trueInfected = calcTrueInfected(dailyInfosList, 1);
        this.raisedInf24H = calcRaisedInf24H(dailyInfosList, 1);
    }

    /**
     * Realizes task 1.a of the task description.
     * The method calculates the new infections in the whole of Germany within 24 hours.
     * The calculation is based on the difference of the confirmed falls from today and the previous day.
     *
     * @param dailyInfosList The list represents all days since 22.01.2020.
     *                       A detailed description can be found in the JohnHDailyInfos class.
     * @param idx            Index describes the list item index to get the respective entry of the John Hopkins data
     * @return new infections in the last 24 hours (Integer)
     */
    private int calcNewInf24H(List<JohnHDailyInfos> dailyInfosList, int idx) {
        int newInf24H;
        newInf24H = dailyInfosList.get(dailyInfosList.size() - idx).getConfirmed() - dailyInfosList.get(dailyInfosList.size() - (idx + 1)).getConfirmed();
        return newInf24H;
    }

    /**
     * Realizes task 1.b of the task description.
     * In this method, the genuinely infected individuals within Germany are calculated.
     * The calculation is based on the difference of confirmed cases, recovered cases and deaths.
     *
     * @param dailyInfosList The list represents all days since 19.03.2020.
     *                       A detailed description can be found in the JohnHDailyInfos class.
     * @param idx            Index describes the list item index to get the respective entry of the John Hopkins data
     * @return genuinely infected individuals within Germany (Integer)
     */
    private int calcTrueInfected(List<JohnHDailyInfos> dailyInfosList, int idx) {
        int day;
        day = dailyInfosList.size() - idx;
        return (dailyInfosList.get(day).getConfirmed() - dailyInfosList.get(day).getRecovered() - dailyInfosList.get(day).getDeaths());
    }

    /**
     * Realizes task 1.c of the task description.
     * In this method, the calculation of the increase of real infected people within one day takes place.
     * The method can calculate any day and its predecessor using the list index.
     *
     * @param dailyInfosList The list represents all days since 19.03.2020.
     *                       A detailed description can be found in the JohnHDailyInfos class.
     * @param days           The Days parameter describes for which day and its predecessor the increase in infections should be calculated.
     * @return Increase in infections in the last 24 hours within Germany (Integer)
     */
    private int calcRaisedInf24H(List<JohnHDailyInfos> dailyInfosList, int days) {
        int raisedInf24;

        raisedInf24 = calcTrueInfected(dailyInfosList, days) - calcTrueInfected(dailyInfosList, days + 1);
        return raisedInf24;
    }

    /**
     * Realizes task 1.d of the task description.
     * The method calculates the average increase in infections within n days, within Germany.
     * For this purpose, a value of the real infected persons is calculated for every 24 hours of the n days and then divided by the n days.
     *
     * @param dailyInfosList The list represents all days since 19.03.2020.
     *                       A detailed description can be found in the JohnHDailyInfos class.
     * @param days           The Days parameter describes for which day and its predecessor the increase in infections should be calculated.
     * @return Average increase in infection within Germany in n days (Double)
     */
    public double calcAvgRaiseN(List<JohnHDailyInfos> dailyInfosList, int days) {
        double avgRaiseN;
        int raisedInfSum = 0;

        for (int idx = 1; idx <= days; idx++) {
            raisedInfSum += calcRaisedInf24H(dailyInfosList, idx);
        }
        avgRaiseN = ((double) raisedInfSum) / days;

        return avgRaiseN;
    }

    /**
     * Method calculates the average decrease in cases in n days.
     * Is needed for the calculation of the lockdown prediction.
     *
     * @param dailyInfosList The list represents all days since 19.03.2020.
     *                       A detailed description can be found in the JohnHDailyInfos class.
     * @param days           The Days parameter describes for which day and its predecessor the increase in infections should be calculated.
     * @return Average decrease of infections (Double)
     */
    public double calcAvgDecrease(List<JohnHDailyInfos> dailyInfosList, int days) {
        int sumTrueInfected = 0;

        for (int i = 1; i <= days; i++) {
            sumTrueInfected += (calcTrueInfected(dailyInfosList, i + 1) - calcTrueInfected(dailyInfosList, i));
        }
        return ((double) sumTrueInfected) / days;

    }

    /**
     * Basic getter to return the number of new infections within 24 hours.
     * Maintains encapsulation
     *
     * @return Integer variable describes the new infections in all of Germany within 24 hours (Integer)
     */
    public int getNewInf24H() {
        return newInf24H;
    }

    /**
     * Basic getter to return the increase of infections within 24 hours.
     * Maintains encapsulation
     *
     * @return Integer variable describes the increase in infections in all of Germany within 24 hours (Integer)
     */
    public int getRaisedInf24H() {
        return raisedInf24H;
    }

    /**
     * Basic getter to return the number of genuinely infections within Germany.
     * Maintains encapsulation
     *
     * @return Integer Variable describes the number of infected persons within Germany. (Integer)
     */
    public int getTrueInfected() {
        return trueInfected;
    }
}
