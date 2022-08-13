package galton;

import galton.RNG.FairCoin;
import galton.config.BeamMachineConfig;
import galton.response.Result;
import galton.specifics.Coin;
import galton.specifics.RiskLevel;

import java.util.ArrayList;
import java.util.List;

public class BeamMachine extends BeamMachineConfig {
    private static final int MIN_PIN_ROWS = 8;
    private static final int MAX_PIN_ROWS = 16;
    private final FairCoin coin = new FairCoin();

    private final int PIN_ROWS;
    private final double[] PAY_TABLE;

    public BeamMachine(final int pinRows, final RiskLevel riskLvl) {
        if (pinRows < MIN_PIN_ROWS || pinRows > MAX_PIN_ROWS) {
            throw new IllegalArgumentException("Invalid pin rows number");
        }
        PIN_ROWS = pinRows;
        final int PAY_ROW = pinRows - MIN_PIN_ROWS;

        PAY_TABLE = switch (riskLvl) {
            case LOW -> LOW[PAY_ROW];
            case MEDIUM -> MEDIUM[PAY_ROW];
            case HIGH -> HIGH[PAY_ROW];
        };
    }

    public Result dropBeam(int bet) {
        int pinRowsCopy = PIN_ROWS;

        List<Boolean> rightDirections = new ArrayList<>(PIN_ROWS);

        int right = 0;

        do {
            if (coin.flip().equals(Coin.HEAD)) {
                rightDirections.add(true);
            } else {
                rightDirections.add(false);
                right++;
            }
        } while (--pinRowsCopy > 0);

        double win = PAY_TABLE[right];

        return new Result(rightDirections, win * bet);
    }
}
