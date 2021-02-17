package hrw.webservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Get the JsonProperty from the Robert Koch-Institute Coronavirus data
 *
 * @author Lars Karbach, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */
public class Attributes {
    @JsonProperty("cases7_bl")
    private int cases7bl;
    @JsonProperty("Fallzahl")
    private int cases;
    @JsonProperty("LAN_ew_EWZ")
    private int citizens;

    /** Basic constructor
     * @param cases7bl cases of states in 7 days
     * @param cases total cases of state
     * @param citizens count of state citizens
     */
    public Attributes(int cases7bl, int cases, int citizens) {
        this.cases7bl = cases7bl;
        this.cases = cases;
        this.citizens = citizens;
    }

    /**
     * Basic getter for citizens of germany
     *
     * @return citizens
     */
    public int getCitizens() {
        return citizens;
    }

    /**
     * Basic setter to set citizens
     *
     * @param citizens sets total citizens
     */
    public void setCitizens(int citizens) {
        this.citizens = citizens;
    }

    /**
     * Basic getter for cases
     *
     * @return cases
     */
    public int getCases() {
        return cases;
    }

    /**
     * Basic setter to set cases
     *
     * @param cases sets cases
     */
    public void setCases(int cases) {
        this.cases = cases;
    }

    /**
     * Basic getter for cases of the states per 7 days
     *
     * @return cases7bl
     */
    public int getCases7bl() {
        return cases7bl;
    }

    /**
     * Basic setter to set cases for each state for 7 days
     *
     * @param cases7bl sets cases of states for 7 days
     */
    public void setCases7bl(int cases7bl) {
        this.cases7bl = cases7bl;
    }
}
