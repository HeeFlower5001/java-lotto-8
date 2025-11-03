package lotto.domain;

import lotto.domain.enums.Rank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

public class Calculator {
    private final List<Lotto> lottos;
    private final Lotto resultNumbers;
    private final int bonus;

    public Calculator(List<Lotto> lottos, Lotto resultNumbers, int bonus) {
        this.lottos = lottos;
        this.resultNumbers = resultNumbers;
        this.bonus = bonus;
    }

    public List<Rank> mapRanks() {
        List<Rank> ranks = new ArrayList<>(lottos.size());

        for (Lotto lotto : lottos) {
            int match = matchCount(lotto);
            boolean bonusMatched = bonusMatched(lotto);

            Rank rank = Rank.from(match, bonusMatched);

            ranks.add(rank);
        }

        return List.copyOf(ranks);
    }

    public Map<Rank, Integer> countByRanks(List<Rank> ranks) {
        Map<Rank, Integer> result = new EnumMap<>(Rank.class);

        for (Rank r : Rank.values()) {
            result.put(r, 0);
        }

        for (Rank r : ranks) {
            result.put(r, result.get(r) + 1);
        }

        return result;
    }

    public BigInteger getTotalMoney(List<Rank> ranks) {
        BigInteger totalMoney = BigInteger.ZERO;

        for (int i = 0; i < ranks.size(); i++) {
            int money = ranks.get(i).getMoney();

            totalMoney = totalMoney.add(BigInteger.valueOf(money));
        }

        return totalMoney;
    }

    public BigDecimal yieldIncreasePercent(BigInteger totalMoney, BigInteger cost) {
        if (cost.compareTo(BigInteger.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(totalMoney)
                .divide(new BigDecimal(cost), 3,  RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(1, RoundingMode.HALF_UP);
    }

    private int matchCount(Lotto lotto) {
        int count = 0;

        List<Integer> numbers = resultNumbers.getNumbers();

        for (int number : lotto.getNumbers()) {
            if (numbers.contains(number)) count++;
        }

        return count;
    }

    private boolean bonusMatched(Lotto lotto) {
        for (int number: lotto.getNumbers()) {
            if (number == bonus) return true;
        }

        return false;
    }
}