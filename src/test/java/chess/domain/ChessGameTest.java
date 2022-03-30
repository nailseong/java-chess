package chess.domain;

import static chess.domain.position.Position.from;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.chessboard.ChessBoardFactory;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameTest {

    @Test
    @DisplayName("순서에 맞지않는 색의 기물을 이동시키면 예외를 발생시킵니다.")
    void chessBoard_turn() {
        // given
        final ChessGame chessGame = new ChessGame(ChessBoardFactory.createChessBoard());
        chessGame.start();

        // then
        assertThatThrownBy(() -> chessGame.move(from("a7"), from("a6")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("WHITE의 차례입니다.");
    }

    @Test
    @DisplayName("게임 중이 아닐 때 기물을 이동시키려고 하면 예외를 던진다.")
    void move_not_in_play() {
        // given
        final ChessGame chessGame = new ChessGame(ChessBoardFactory.createChessBoard());

        // then
        assertThatThrownBy(() -> chessGame.move(Position.from("a1"), Position.from("a2")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임이 시작되지 않았습니다.");
    }
}