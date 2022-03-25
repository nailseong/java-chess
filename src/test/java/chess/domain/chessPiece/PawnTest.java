package chess.domain.chessPiece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PawnTest {

    final Position initialPosition = new Position("d5");

    @ParameterizedTest
    @DisplayName("이동 할 수 있는 위치라면 예외를 던지지 않는다.")
    @ValueSource(strings = {"c4", "d4", "e4"})
    void canMove(final String target) {
        // given
        final ChessPiece pawn = new Pawn(Color.BLACK);

        // then
        assertThatCode(() -> pawn.canMove(initialPosition, new Position(target)))
                .doesNotThrowAnyException();

    }

    @ParameterizedTest
    @DisplayName("이동 할 수 없는 위치로 이동하면 예외를 던진다.")
    @CsvSource(value = {"BLACK:d6", "BLACK:d3", "BLACK:c3", "WHITE:d4", "WHITE:d7", "WHITE:c7"}, delimiter = ':')
    void canMove_exception(final Color color, final String target) {
        // given
        final ChessPiece pawn = new Pawn(color);

        // then
        assertThatThrownBy(() -> pawn.canMove(initialPosition, new Position(target)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 갈 수 없는 위치입니다.");
    }

    @ParameterizedTest
    @DisplayName("처음 위치에서 2칸 이동한다.")
    @CsvSource(value = {"BLACK:d7:d5", "WHITE:d2:d4"}, delimiter = ':')
    void canMove_initFile(final Color color, final String from, final String to) {
        // given
        final ChessPiece pawn = new Pawn(color);

        // then
        assertThatCode(() -> pawn.canMove(new Position(from), new Position(to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("처음 위치에서 2칸 이상 이동하면 예외를 던진다.")
    @CsvSource(value = {"BLACK:d7:d4", "WHITE:d2:d5"}, delimiter = ':')
    void canMove_initFile_exception(final Color color, final String from, final String to) {
        // given
        final ChessPiece pawn = new Pawn(color);

        // then
        assertThatThrownBy(() -> pawn.canMove(new Position(from), new Position(to)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 갈 수 없는 위치입니다.");
    }

    @ParameterizedTest
    @DisplayName("대각선으로 이동 할 수 있는 위치라면 예외를 던지지 않는다.")
    @CsvSource(value = {"BLACK:e4", "BLACK:c4", "WHITE:c6", "WHITE:e6"}, delimiter = ':')
    void checkCrossMove(final Color color, final String target) {
        // given
        final Pawn pawn = new Pawn(color);

        // then
        assertThatCode(() -> pawn.checkCrossMove(initialPosition, new Position(target)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("대각선으로 이동 할 수 없는 위치라면 예외를 던진다.")
    @CsvSource(value = {"BLACK:f4", "BLACK:b4", "WHITE:b6", "WHITE:f6"}, delimiter = ':')
    void checkCrossMove_exception(final Color color, final String target) {
        // given
        final Pawn pawn = new Pawn(color);

        // then
        assertThatThrownBy(() -> pawn.checkCrossMove(initialPosition, new Position(target)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 갈 수 없는 위치입니다.");
    }
}
