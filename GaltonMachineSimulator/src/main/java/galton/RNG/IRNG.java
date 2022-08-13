package galton.RNG;

import galton.specifics.Coin;

public interface IRNG {
    /**
     * @return true for head, false for tail
     */
    Coin flip();
}
