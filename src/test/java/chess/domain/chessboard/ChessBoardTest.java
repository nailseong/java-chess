package chess.domain.chessboard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.chesspiece.Bishop;
import chess.domain.chesspiece.ChessPiece;
import chess.domain.chesspiece.Color;
import chess.domain.chesspiece.King;
import chess.domain.chesspiece.Rook;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChessBoardTest {

    @ParameterizedTest
    @DisplayName("위치를 기반으로 기물을 찾는다.")
    @CsvSource(value = {"a1:r", "a8:R"}, delimiter = ':')
    void findPiece(final String position, final String expected) {
        // given
        final ChessBoard chessBoard = ChessBoardFactory.createChessBoard();

        // when
        final Optional<ChessPiece> possiblePiece = chessBoard.findPiece(Position.from(position));
        final ChessPiece actual = possiblePiece.get();

        // then
        assertThat(actual.name()).isEqualTo(expected);
    }

    @Test
    @DisplayName("위치에 기물이 있는지 확인한다.")
    void findPiece_Null() {
        // given
        final ChessBoard chessBoard = ChessBoardFactory.createChessBoard();

        // when
        final Optional<ChessPiece> actual = chessBoard.findPiece(Position.from("a3"));

        // then
        assertThat(actual.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("기물을 빈 위치로 이동시킨다.")
    void move_to_empty() {
        // given
        final ChessBoard chessBoard = ChessBoardFactory.createChessBoard();
        final Position from = Position.from("b1");
        final Position to = Position.from("c3");

        // when
        chessBoard.move(from, to);
        final ChessPiece actual = chessBoard.findPiece(to).get();

        // then
        assertThat(actual.name()).isEqualTo("n");
    }

    @Test
    @DisplayName("기물을 다른색의 기물이 있는 위치로 이동시킨다.")
    void move_to_enemy() {
        // given
        final Position from = Position.from("d2");
        final Position to = Position.from("f4");

        final ChessBoard chessBoard = PieceByPosition.create()
                .add(from, Bishop.from(Color.WHITE))
                .add(to, Rook.from(Color.BLACK))
                .toChessBoard();

        // when
        chessBoard.move(from, to);
        final ChessPiece actual = chessBoard.findPiece(to).get();

        // then
        assertThat(actual.name()).isEqualTo("b");
    }

    @Test
    @DisplayName("이동 경로 사이에 다른 기물이 있으면 예외를 발생시킵니다.")
    void move_exception() {
        // given
        final ChessBoard chessBoard = ChessBoardFactory.createChessBoard();
        final Position from = Position.from("a1");
        final Position to = Position.from("a3");

        // then
        assertThatThrownBy(() -> chessBoard.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로 사이에 다른 기물이 있습니다.");
    }

    @Test
    @DisplayName("킹을 잡으면 게임이 끝난다.")
    void game_end() {
        // given
        final Position from = Position.from("f3");
        final Position to = Position.from("f4");

        final ChessBoard chessBoard = PieceByPosition.create()
                .add(from, King.from(Color.WHITE))
                .add(to, King.from(Color.BLACK))
                .toChessBoard();

        // when
        chessBoard.move(from, to);
        final boolean actual = chessBoard.isKingDie();

        // then
        assertThat(actual).isEqualTo(true);
    }

    private static class PieceByPosition {

        private final Map<Position, ChessPiece> value;

        private PieceByPosition() {
            this.value = new HashMap<>();
        }

        static PieceByPosition create() {
            return new PieceByPosition();
        }

        PieceByPosition add(Position position, ChessPiece chessPiece) {
            value.put(position, chessPiece);
            return this;
        }


        ChessBoard toChessBoard() {
            return new ChessBoard(value);
        }
    }
}
