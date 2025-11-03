package lotto.domain;

import lotto.domain.enums.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    @Test
    void 등수_집계와_수익률_계산() {
        List<Lotto> lottos = List.of(
                // 3개 일치
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                // 0개 일치
                new Lotto(List.of(7, 8, 9, 10, 11, 12))
        );
        WinningNumbers winningNumbers = new WinningNumbers(new Lotto(List.of(1, 2, 3, 13, 14, 15)), 7);
        Calculator calculator = new Calculator(lottos, winningNumbers);

        Map<Rank, Integer> count = calculator.countByRanks();
        BigDecimal yield = calculator.yieldIncreasePercent();

        assertThat(count.get(Rank.FIFTH)).isEqualTo(1);
        assertThat(count.get(Rank.MISS)).isEqualTo(1);
        assertThat(count.get(Rank.FOURTH)).isZero();
        assertThat(yield).isEqualByComparingTo("250.0");
    }

    @DisplayName("2등과 3등은 보너스로 구분")
    @Test
    void 이등과_삼등은_보너스로_구분() {
        WinningNumbers winningNumbers = new  WinningNumbers(new Lotto(List.of(1, 2, 3, 4, 5, 6)), 7);

        Lotto second = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Lotto third = new Lotto(List.of(1, 2, 3, 4, 5, 8));

        Calculator calculator = new Calculator(List.of(second, third), winningNumbers);

        Map<Rank, Integer> count = calculator.countByRanks();
        assertThat(count.get(Rank.SECOND)).isEqualTo(1);
        assertThat(count.get(Rank.THIRD)).isEqualTo(1);
        assertThat(count.get(Rank.MISS)).isZero();
    }

    @Test
    void 수익률_소수점_한자리_반올림() {
        WinningNumbers winningNumbers = new  WinningNumbers(new Lotto(List.of(1, 2, 3, 4, 5, 6)), 7);

        Lotto fifth1 = new Lotto(List.of(1, 2, 3, 8, 9, 10));
        Lotto fifth2 = new Lotto(List.of(1, 2, 3, 10, 11, 12));
        Lotto miss = new Lotto(List.of(7, 8, 9, 10, 11, 12));

        Calculator calculator = new Calculator(List.of(fifth1, fifth2, miss), winningNumbers);
        BigDecimal yield = calculator.yieldIncreasePercent();
        assertThat(yield).isEqualByComparingTo("333.3");
    }
}
