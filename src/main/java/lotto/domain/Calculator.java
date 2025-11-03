package lotto.domain;

import lotto.domain.enums.Rank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

public class Calculator {
    private static final BigInteger MONEY_UNIT = BigInteger.valueOf(1000);

    private final List<Lotto> lottos;
    private final WinningNumbers winningNumbers;

    public Calculator(List<Lotto> lottos, WinningNumbers winningNumbers) {
        this.lottos = lottos;
        this.winningNumbers = winningNumbers;
    }

    public Map<Rank, Integer> countByRanks() {
        List<Rank> ranks = mapRanks();
        Map<Rank, Integer> result = new EnumMap<>(Rank.class);

        for (Rank r : Rank.values()) {
            result.put(r, 0);
        }

        for (Rank r : ranks) {
            result.put(r, result.get(r) + 1);
        }

        return result;
    }

    public BigDecimal yieldIncreasePercent() {
        BigInteger totalMoney = getTotalMoney();
        BigInteger cost = BigInteger.valueOf(lottos.size()).multiply(MONEY_UNIT);

        if (cost.compareTo(BigInteger.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(totalMoney)
                .divide(new BigDecimal(cost), 3,  RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(1, RoundingMode.HALF_UP);
    }

    private BigInteger getTotalMoney() {
        List<Rank> ranks = mapRanks();

        BigInteger totalMoney = BigInteger.ZERO;

        for (int i = 0; i < ranks.size(); i++) {
            int money = ranks.get(i).getMoney();

            totalMoney = totalMoney.add(BigInteger.valueOf(money));
        }

        return totalMoney;
    }

    private List<Rank> mapRanks() {
        List<Rank> ranks = new ArrayList<>(lottos.size());

        for (Lotto lotto : lottos) {
            int match = matchCount(lotto);
            boolean bonusMatched = bonusMatched(lotto);

            Rank rank = Rank.from(match, bonusMatched);

            ranks.add(rank);
        }

        return List.copyOf(ranks);
    }

    private int matchCount(Lotto lotto) {
        int count = 0;

        List<Integer> numbers = winningNumbers.getWinningNumbers().getNumbers();

        for (int number : lotto.getNumbers()) {
            if (numbers.contains(number)) count++;
        }

        return count;
    }

    private boolean bonusMatched(Lotto lotto) {
        for (int number: lotto.getNumbers()) {
            if (number == winningNumbers.getBonus()) return true;
        }

        return false;
    }
}