package galton.RNG;

import galton.specifics.Coin;
import org.apache.commons.math3.random.MersenneTwister;

public class FairCoin implements IRNG {
    private final MersenneTwister mt;

    public FairCoin() {
        mt = new MersenneTwister();
    }

    @Override
    public Coin flip() {
        return mt.nextInt(2) == 1 ? Coin.HEAD : Coin.TAIL;
    }
}
