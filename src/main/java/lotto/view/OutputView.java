package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.enums.Rank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OutputView {
    private OutputView() {

    }

    public static void printLottos(List<Lotto> lottos) {
        System.out.println();
        System.out.printf("%d개를 구매했습니다.\n", lottos.size());

        for (Lotto lotto : lottos) {
            printLotto(lotto);
        }

        System.out.println();
    }

    public static void printResult(Map<Rank, Integer> map) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");

        List<Rank> order = List.of(
                Rank.FIFTH, Rank.FOURTH, Rank.THIRD, Rank.SECOND, Rank.FIRST
        );

        order.forEach(rank -> printRank(rank, map.getOrDefault(rank, 0)));
    }

    public static void printIncreasePercent(BigDecimal increasePercent) {
        System.out.printf("총 수익률은 %s%%입니다.", increasePercent.toPlainString());
    }

    private static void printLotto(Lotto lotto) {
        System.out.println(lotto.getNumbers());
    }

    private static void printRank(Rank rank, int count) {
        String label = (rank == Rank.SECOND)
                ? String.format("%d개 일치, 보너스 볼 일치", rank.getCount())
                : String.format("%d개 일치", rank.getCount());

        System.out.printf("%s (%s) - %d개%n", label, formatMoney(rank.getMoney()), count);
    }

    private static String formatMoney(int money) {
        return String.format("%,d원", money);
    }
}