package chess.view;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    @DisplayName("명령이 올바른 형식으로 들어오지 않았을 때 예외를 발생시킨다")
    void validate() {
        // given
        final String input = "s t a r t";

        // then
        assertThatThrownBy(() -> Command.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 명령입니다.");
    }

    @Test
    @DisplayName("이동 명령이 올바른 형식으로 들어오지 않았을 때 예외를 발생시킨다")
    void getFromPosition() {
        // given
        final String input = "move a2";

        // then
        assertThatThrownBy(() -> Command.findFromPosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("move source위치 target위치 형식이 아닙니다.");
    }
}