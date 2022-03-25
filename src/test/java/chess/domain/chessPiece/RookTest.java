package chess.domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.position.Position;
import java.util.List;
import java.util.Stack;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RookTest {

    Position initialPosition = new Position("d5");

    @Test
    @DisplayName("이동 할 수 없는 위치로 이동하면 예외를 던진다.")
    void canMove_cantGo() {
        // given
        ChessPiece rook = new Rook(Color.BLACK);

        // then
        assertThatThrownBy(() -> rook.canMove(initialPosition, new Position("c6")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 갈 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("이동 할 수 있는 위치라면 예외를 던지지 않는다.")
    void canMove_canGo() {
        // given
        ChessPiece rook = new Rook(Color.BLACK);

        // then
        Assertions.assertThatCode(() -> rook.canMove(initialPosition, new Position("c5")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("목적지까지 경로를 구한다.")
    void findRoute() {
        // given
        ChessPiece rook = new Rook(Color.BLACK);

        // when
        Stack<Position> actual = rook.findRoute(initialPosition, new Position("d1"));
        List<Position> expected = List.of(new Position("d4"), new Position("d3"), new Position("d2"));

        // then
        assertThat(actual).containsAll(expected);
    }
}
