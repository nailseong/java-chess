package chess.domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.position.Position;
import java.util.Stack;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class KnightTest {

    Position initialPosition = new Position("d5");

    @Test
    @DisplayName("이동 할 수 없는 위치로 이동하면 예외를 던진다.")
    void canMove_cantGo() {
        // given
        ChessPiece knight = new Knight(Color.BLACK);

        // then
        assertThatThrownBy(() -> knight.canMove(initialPosition, new Position("d7")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 갈 수 없는 위치입니다.");
    }

    @ParameterizedTest
    @DisplayName("이동 할 수 있는 위치라면 예외를 던지지 않는다.")
    @ValueSource(strings = {"c7", "c3", "b6", "b4", "f4", "f6", "e7", "e3"})
    void canMove_canGo(String target) {
        // given
        ChessPiece knight = new Knight(Color.BLACK);

        // then
        Assertions.assertThatCode(() -> knight.canMove(initialPosition, new Position(target)))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("목적지까지 경로를 구한다.")
    void findRoute() {
        // given
        ChessPiece knight = new Knight(Color.BLACK);

        // when
        Stack<Position> actual = knight.findRoute(initialPosition, new Position("b6"));

        // then
        assertThat(actual).isEmpty();
    }
}
