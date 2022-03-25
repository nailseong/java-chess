package chess.domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PawnTest {

    Position initialPosition = new Position("d5");

    @ParameterizedTest
    @DisplayName("이동 할 수 없는 위치로 이동하면 예외를 던진다.")
    @CsvSource(value = {"BLACK:d6", "WHITE:d4"}, delimiter = ':')
    void canMove_cantGo(Color color, String target) {
        // given
        ChessPiece pawn = new Pawn(color);

        // then
        assertThatThrownBy(() -> pawn.canMove(initialPosition, new Position(target)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 갈 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("이동 할 수 있는 위치라면 예외를 던지지 않는다.")
    void canMove_canGo() {
        // given
        ChessPiece pawn = new Pawn(Color.BLACK);

        // then
        Assertions.assertThatCode(() -> pawn.canMove(initialPosition, new Position("d4")))
                .doesNotThrowAnyException();

    }

    @ParameterizedTest
    @DisplayName("처음 위치에서 2칸 이동한다.")
    @CsvSource(value = {"BLACK:d7:d5", "WHITE:d2:d4"}, delimiter = ':')
    void canMove_canGoInit(Color color, String from, String to) {
        // given
        ChessPiece pawn = new Pawn(color);

        // then
        Assertions.assertThatCode(() -> pawn.canMove(new Position(from), new Position(to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("이동 할 수 있는 위치라면 예외를 던지지 않는다.")
    @CsvSource(value = {"BLACK:e4", "BLACK:c4", "WHITE:c6", "WHITE:e6"}, delimiter = ':')
    void canCatch_canCatch(Color color, String target) {
        // given
        ChessPiece pawn = new Pawn(color);

        // then
        Assertions.assertThatCode(() -> ((Pawn) pawn).checkCrossMove(initialPosition, new Position(target)))
                .doesNotThrowAnyException();
    }
}
