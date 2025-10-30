package lotto.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LottoMachine {
    private static final int LOTTO_SIZE = 6;
    private static final BigInteger MONEY_UNIT = BigInteger.valueOf(1000);

    private final NumberGenerator generator;
    private final BigInteger money;

    public LottoMachine(NumberGenerator generator, BigInteger money) {
        validateGenerator(generator);
        validateMoney(money);

        this.generator = generator;
        this.money = money;
    }

    public LottoMachine(BigInteger money) {
        this(new NumberGeneratorImpl(), money);
    }

    public List<Lotto> issue() {
        int count = getCount();
        List<Lotto> lottos = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            List<Integer> numbers = generator.generate(LOTTO_SIZE);

            lottos.add(new Lotto(numbers));
        }

        return List.copyOf(lottos);
    }

    private void validateGenerator(NumberGenerator generator) {
        if (generator == null) {
            throw new IllegalArgumentException("[ERROR] NumberGenerator는 null일 수 없습니다.");
        }
    }

    private void validateMoney(BigInteger money) {
        if (money.compareTo(MONEY_UNIT) < 0) {
            throw new IllegalArgumentException("[ERROR] 입력 금액이 1,000원 이상이어야 합니다.");
        }

        if (!money.remainder(MONEY_UNIT).equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("[ERROR] 입력 금액은 1,000원 단위여야 합니다.");
        }

        BigInteger maxCount = BigInteger.valueOf(Integer.MAX_VALUE);

        if (money.divide(MONEY_UNIT).compareTo(maxCount) > 0) {
            throw new IllegalArgumentException("[ERROR] 입력 금액이 너무 많습니다.");
        }
    }

    private int getCount() {
        return money.divide(MONEY_UNIT).intValueExact();
    }
}
