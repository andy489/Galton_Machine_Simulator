package galton;

import galton.response.Result;
import galton.specifics.RiskLevel;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertTrue;

public class BeamMachineTest {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    @Test
    public void testBeamMachineInvalidPinRowsCount() {
        final int INVALID_PIN_ROWS = 7;
        assertThrows(IllegalArgumentException.class, () -> new BeamMachine(INVALID_PIN_ROWS, RiskLevel.HIGH));
    }

    @Test
    public void testBeamMachine() {
        final int MIN_PIN_ROWS = 8;
        final int MAX_PIN_ROWS = 16;
        final int SPINS = 1_000_000;
        final int BET = 1;

        for (final RiskLevel riskLevel : RiskLevel.values()) {
            for (int pinRows = MIN_PIN_ROWS; pinRows <= MAX_PIN_ROWS; pinRows++) {
                double totalWin = 0.0d;

                BeamMachine bm = new BeamMachine(pinRows, riskLevel);

                for (int i = 0; i < SPINS; i++) {
                    Result res = bm.dropBeam(BET);

                    double currentWin = res.returnAmount();

                    totalWin += currentWin;
                }

                double totalBet = SPINS * BET;
                final double RTP = totalWin / totalBet;

                System.out.printf("#Pin Rows: %s%2d%s; RTP: %s%.5f %% %s%n",
                        ANSI_YELLOW,
                        pinRows,
                        ANSI_RESET,

                        ANSI_YELLOW,
                        RTP * 100,
                        ANSI_RESET
                );

                assertTrue(RTP < 1);
                assertTrue(RTP > 0.97);
            }
            System.out.println();
        }
    }
}
