package hrw.webservice.model;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Attributes for the John Hopkins daily Data
 * All figures refer to Germany
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */

public class JohnHDailyInfos {
    private String date;
    private int confirmed;
    private int deaths;
    private int recovered;

    /**
     * Default constructor
     */
    public JohnHDailyInfos() {
    }

    /**
     * Contructor creates JohnHDailyInfo object
     *
     * @param date      represents given date
     * @param confirmed represents confirmed covid cases
     * @param deaths    represents covid death cases
     * @param recovered represents recovered covid cases
     */
    public JohnHDailyInfos(String date, int confirmed, int deaths, int recovered) {
        this.date = date;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    /**
     * Basic getter to return date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Basic setter to set date
     *
     * @param date sets date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Basic getter for confirmed cases
     *
     * @return confirmed
     */
    public int getConfirmed() {
        return confirmed;
    }

    /**
     * Basic setter to set confirmed cases
     *
     * @param confirmed sets confirmed cases
     */
    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * Basic getter for deaths
     *
     * @return deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Basic setter to set deaths
     *
     * @param deaths sets deaths
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * Basic getter for recovered cases
     *
     * @return recovered
     */
    public int getRecovered() {
        return recovered;
    }

    /**
     * Basic setter to set recovered cases
     *
     * @param recovered sets recovered cases
     */
    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }
}
