package lotto;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.math.BigInteger;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Lotto> lottos = getValidLottos();
        OutputView.printLottos(lottos);

        Lotto winningLotto = getValidWinningLotto();
        WinningNumbers winningNumbers = getValidWinningNumbers(winningLotto);

        Calculator calculator = new Calculator(lottos, winningNumbers);
        OutputView.printResult(calculator.countByRanks());
        OutputView.printIncreasePercent(calculator.yieldIncreasePercent());
    }

    private static List<Lotto> getValidLottos() {
        while (true) {
            try {
                int money = InputView.getMoney();
                LottoMachine machine = new LottoMachine(BigInteger.valueOf(money));

                return machine.issue();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Lotto getValidWinningLotto() {
        while (true) {
            try {
                return InputView.getResultLotto();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static WinningNumbers getValidWinningNumbers(Lotto winningLotto) {
        while (true) {
            try {
                int bonus = InputView.getBonusNumber();

                return new WinningNumbers(winningLotto, bonus);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
