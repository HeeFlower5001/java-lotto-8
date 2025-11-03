package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.domain.Lotto;

public class InputView {
    private InputView() {

    }

    public static int getMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        int money = Integer.parseInt(Console.readLine());

        return money;
    }

    public static Lotto getResultLotto() {
        System.out.println("당첨 번호를 입력해 주세요.");
        String input = Console.readLine();

        Lotto lotto = new Lotto(input);

        return lotto;
    }

    public static int getBonusNumber() {
        System.out.println();
        System.out.println("보너스 번호를 입력해 주세요.");

        int input = Integer.parseInt(Console.readLine());

        return input;
    }
}
