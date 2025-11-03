package lotto.domain.enums;

import java.util.Arrays;

public enum Rank {
    FIRST(6, false, 2_000_000_000),
    SECOND(5, true, 30_000_000),
    THIRD(5, false, 1_500_000),
    FOURTH(4, false, 50_000),
    FIFTH(3, false, 5_000),
    MISS(0, false, 0);

    private final int count;
    private final boolean bonusMatched;
    private final int money;

    Rank(int count, boolean bonusMatched, int money) {
        this.count = count;
        this.bonusMatched = bonusMatched;
        this.money = money;
    }

    public int getCount() {
        return count;
    }

    public boolean isBonusMatched() {
        return bonusMatched;
    }

    public int getMoney() {
        return money;
    }

    public static Rank from(int count, boolean bonusMatched) {
        if (count == SECOND.getCount() && bonusMatched) {
            return SECOND;
        }

        return Arrays.stream(values())
                .filter(rank -> rank.count == count && !rank.bonusMatched)
                .findFirst()
                .orElse(MISS);
    }
}
