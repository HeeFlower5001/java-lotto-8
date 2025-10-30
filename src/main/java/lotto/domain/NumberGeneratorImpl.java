package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.List;

public class NumberGeneratorImpl implements NumberGenerator {
    private static final int MIN = 1;
    private static final int MAX = 45;

    @Override
    public List<Integer> generate(int size) {
        return Randoms.pickUniqueNumbersInRange(MIN, MAX, size);
    }
}
