package lotto.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class WinningNumbersTest {
    @Test
    void 보너스_번호는_1이상이다() {
        Lotto lotto = new Lotto(List.of(2, 3, 4, 5, 6, 7));
        int bonus = 1;

        WinningNumbers winningNumbers = new WinningNumbers(lotto, bonus);
        assertThat(winningNumbers.getBonus()).isEqualTo(1);
    }

    @Test
    void 보너스_번호는_45이하이다() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonus = 45;

        WinningNumbers winningNumbers = new WinningNumbers(lotto, bonus);
        assertThat(winningNumbers.getBonus()).isEqualTo(45);
    }

    @Test
    void 보너스_번호가_범위를_벗어나면_예외() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonus = 0;

        assertThatThrownBy( () -> new WinningNumbers(lotto, bonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 보너스_번호가_겹치면_예외() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonus = 1;

        assertThatThrownBy( () -> new WinningNumbers(lotto, bonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
