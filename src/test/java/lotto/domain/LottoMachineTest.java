package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoMachineTest {
    static class FakeNumberGenerator implements NumberGenerator {
        @Override
        public List<Integer> generate(int size) {
            return List.of(1, 2, 3, 4, 5, 6);
        }
    }

    @Test
    void 정상적으로_로또를_반환한다() {
        LottoMachine machine = new LottoMachine(new FakeNumberGenerator(), BigInteger.valueOf(3000));
        List<Lotto> lottos = machine.issue();

        assertThat(lottos).hasSize(3);
        assertThat(lottos.get(0).getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void 제너레이터가_null이면_예외() {
        assertThatThrownBy(() -> new LottoMachine(null, BigInteger.valueOf(1000)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 금액이_1000원_미만이면_예외() {
        assertThatThrownBy(() -> new LottoMachine(new FakeNumberGenerator(), BigInteger.valueOf(999)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("금액이 1000원 단위가 아니면 예외 발생")
    @Test
    void 금액이_1000원_단위가_아니면_예외() {
        assertThatThrownBy(() -> new LottoMachine(new FakeNumberGenerator(), BigInteger.valueOf(1100)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("금액이 너무 크면 예외 발생")
    @Test
    void 금액이_너무_크면_예외() {
        BigInteger tooBig = BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.ONE).multiply(BigInteger.valueOf(1000));

        assertThatThrownBy(() -> new LottoMachine(new FakeNumberGenerator(), tooBig))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
