package lotto.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RankTest {
    @Test
    void 등수_매핑_확인() {
        assertThat(Rank.from(6, false)).isEqualTo(Rank.FIRST);
        assertThat(Rank.from(5, true)).isEqualTo(Rank.SECOND);
        assertThat(Rank.from(5, false)).isEqualTo(Rank.THIRD);
        assertThat(Rank.from(4, false)).isEqualTo(Rank.FOURTH);
        assertThat(Rank.from(3, false)).isEqualTo(Rank.FIFTH);
        assertThat(Rank.from(2, true)).isEqualTo(Rank.MISS);
    }

    @Test
    void 상금액_정상_확인() {
        assertThat(Rank.FIRST.getMoney()).isEqualTo(2_000_000_000);
        assertThat(Rank.SECOND.getMoney()).isEqualTo(30_000_000);
        assertThat(Rank.THIRD.getMoney()).isEqualTo(1_500_000);
        assertThat(Rank.FOURTH.getMoney()).isEqualTo(50_000);
        assertThat(Rank.FIFTH.getMoney()).isEqualTo(5_000);
        assertThat(Rank.MISS.getMoney()).isEqualTo(0);
    }

    @DisplayName("2등은 보너스번호가 맞아야 한다")
    @Test
    void 이등은_보너스번호가_맞아야_한다() {
        assertThat(Rank.from(5, true)).isEqualTo(Rank.SECOND);
    }

    @DisplayName("3등은 보너스번호가 맞지 않는다")
    @Test
    void 삼등은_보너스번호가_맞지_않는다() {
        assertThat(Rank.from(5, false)).isEqualTo(Rank.THIRD);
    }

    @DisplayName("1등은 보너스와 무관하다")
    @Test
    void 일등은_보너스와_무관하다() {
        assertThat(Rank.from(6, true)).isEqualTo(Rank.FIRST);
        assertThat(Rank.from(6, false)).isEqualTo(Rank.FIRST);
    }

    @Test
    void 보너스가_맞아도_매칭이_적으면_miss() {
        assertThat(Rank.from(2, true)).isEqualTo(Rank.MISS);
        assertThat(Rank.from(0, true)).isEqualTo(Rank.MISS);
    }
}