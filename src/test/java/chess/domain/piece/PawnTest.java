package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.position.Position;
import chess.domain.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {
    @DisplayName("폰의 이동 가능 위치")
    @ParameterizedTest
    @MethodSource("getCasesForPawnMoveByDirection")
    void pawnMove(MoveStrategy moveStrategy, Piece piece, List<Position> expectedToPositions) {
        Board board = BoardFactory.createBoard();
        assertThat(moveStrategy.possiblePositions(board, piece)).isEqualTo(expectedToPositions);
    }

    private static Stream<Arguments> getCasesForPawnMoveByDirection() {
        return Stream.of(
                Arguments.of(new FirstWhitePawnMoveStrategy(), Pawn.createWhite(new Position(2, 2)),
                        Arrays.asList(new Position(2, 3), new Position(2, 4))),

                Arguments.of(new WhitePawnMoveStrategy(), Pawn.createWhite(new Position(3, 4)),
                        Arrays.asList(new Position(3, 5))),

                Arguments.of(new FirstBlackPawnMoveStrategy(), Pawn.createBlack(new Position(2, 7)),
                        Arrays.asList(new Position(2, 6), new Position(2, 5))),

                Arguments.of(new BlackPawnMoveStrategy(), Pawn.createBlack(new Position(4, 6)),
                        Arrays.asList(new Position(4, 5)))
        );
    }
}