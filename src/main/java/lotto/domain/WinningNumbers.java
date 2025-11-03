package lotto.domain;

import java.util.List;

public class WinningNumbers {
    private final Lotto winningNumbers;
    private final int bonus;

    public WinningNumbers(Lotto winningNumbers, int bonus) {
        validateBonus(winningNumbers.getNumbers(), bonus);

        this.winningNumbers = winningNumbers;
        this.bonus = bonus;
    }

    public Lotto getWinningNumbers() {
        return winningNumbers;
    }

    public int getBonus() {
        return bonus;
    }

    private void validateBonus(List<Integer> numbers, int bonus) {
        if (bonus < 1 || bonus > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (numbers.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
