import hrw.webservice.logic.CalculateRKIKeyFigures;
import hrw.webservice.model.Attributes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for RKI key figure calculation
 *
 * @author Lars Karbach, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */
public class CalculateRKIKeyFiguresTest {

    /**
     * Instance of CalculateRKIKeyFigures
     */
    private static CalculateRKIKeyFigures calc;

    /**
     * This method creates the CalculateRKIKeyFigures instance
     * It must be called before any tests are run.
     */
    @BeforeAll
    public static void createCalculator() {

        calc = new CalculateRKIKeyFigures();

    }

    /**
     * This method tests the pre-calculation of RKI values
     */
    @Test
    @DisplayName("Test pre-calculate RKI values")
    public void testPreCalcRkiValues() {

        List<Attributes> dailyStateList = new ArrayList<>();
        dailyStateList.add(new Attributes(4837, 307166, 1000));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));
        dailyStateList.add(new Attributes(4837, 307166, 10));

        assertEquals(calc.preCalcRkiValues(dailyStateList).getIncidenceGermany(), 6729739.130434782);


    }

    /**
     * This method tests the remaining lockdown calculation
     */
    @Test
    @DisplayName("Test calculate remaining lockdown days")
    public void testCalcRemLockdown() {

        double avgDecrease = 50.5;
        double targetInfections = 87000;
        int trueInfections = 1300000;

        assertEquals(calc.calcRemLockdown(avgDecrease, targetInfections, trueInfections), 24019.80198019802);

    }

    /**
     * This method tests the target infection calculation
     */
    @Test
    @DisplayName("Test calculate target infection")
    public void testCalcTargetInfection() {

        int sumCases = 125837;
        double incidenceGermany = 57.03724414447506;

        assertEquals(calc.calcTargetInfection(sumCases, incidenceGermany), 77217.87870472744);

    }

}
