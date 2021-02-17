import hrw.webservice.logic.CalculateJHKeyFigures;
import hrw.webservice.model.JohnHDailyInfos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for Johns Hopkins key figure calculation
 *
 * @author Lars Karbach, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */
public class CalculateJHKeyFiguresTest {

    /**
     * This method tests the calculation of the average infections increase for n days
     */
    @Test
    @DisplayName("testCalcAvgRaiseN")
    public void testCalcAvgRaiseN() {
        List<JohnHDailyInfos> testJohnHDInfoList = new ArrayList<>();
        CalculateJHKeyFigures testCalcJHKeyFigures = new CalculateJHKeyFigures();
        testJohnHDInfoList.add(new JohnHDailyInfos("2021-2-13", 2336906, 64990, 2128002));
        testJohnHDInfoList.add(new JohnHDailyInfos("2021-2-14", 2341744, 65107, 2136933));
        testJohnHDInfoList.add(new JohnHDailyInfos("2021-2-15", 2346876, 65288, 2148269));

        double result = testCalcJHKeyFigures.calcAvgRaiseN(testJohnHDInfoList, 2);

        assertEquals(-5297.5, result);
    }

    /**
     * This method tests the calculation of the average decrease of infections for n days
     */
    @Test
    @DisplayName("testCalcAvgDecrease")
    public void testCalcAvgDecrease() {
        List<JohnHDailyInfos> testJohnHDInfoList = new ArrayList<>();
        CalculateJHKeyFigures testCalcJHKeyFigures = new CalculateJHKeyFigures();
        testJohnHDInfoList.add(new JohnHDailyInfos("2021-2-13", 2336906, 64990, 2128002));
        testJohnHDInfoList.add(new JohnHDailyInfos("2021-2-14", 2341744, 65107, 2136933));
        testJohnHDInfoList.add(new JohnHDailyInfos("2021-2-15", 2346876, 65288, 2148269));

        double result = testCalcJHKeyFigures.calcAvgDecrease(testJohnHDInfoList, 2);

        assertEquals(5297.5, result);
    }

}
