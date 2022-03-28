package chess.domain.chessboard;

import chess.domain.GameStatus;
import chess.domain.chesspiece.ChessPiece;
import chess.domain.chesspiece.Color;
import chess.domain.chesspiece.King;
import chess.domain.chesspiece.Pawn;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoard {

    private final Map<Position, ChessPiece> chessBoard;
    private GameStatus gameStatus;
    private Color currentTurnColor = Color.WHITE;

    ChessBoard(final Map<Position, ChessPiece> chessBoard) {
        this.chessBoard = chessBoard;
        this.gameStatus = GameStatus.READY;
    }

    public Optional<ChessPiece> findPiece(final Position position) {
        final ChessPiece piece = chessBoard.get(position);
        return Optional.ofNullable(piece);
    }

    public void move(final Position from, final Position to) {
        final ChessPiece movablePiece = findPiece(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));

        checkCanMove(from, to, movablePiece);
        movePiece(from, to);
    }

    private void checkCanMove(final Position from, final Position to, final ChessPiece movablePiece) {
        checkCurrentTurn(movablePiece);
        movablePiece.checkMovablePosition(from, to, findPiece(to));
        checkHurdle(from, to, movablePiece);
    }

    private void checkCurrentTurn(final ChessPiece movablePiece) {
        if (!movablePiece.isSameColor(currentTurnColor)) {
            throw new IllegalArgumentException(currentTurnColor.name() + "의 차례입니다.");
        }
    }

    private void checkHurdle(final Position from, final Position to, final ChessPiece movablePiece) {
        final boolean hurdleExist = movablePiece.findRoute(from, to).stream()
                .map(this::findPiece)
                .anyMatch(Optional::isPresent);

        if (hurdleExist) {
            throw new IllegalArgumentException("이동 경로 사이에 다른 기물이 있습니다.");
        }
    }

    private void movePiece(final Position from, final Position to) {
        if (chessBoard.get(to) instanceof King) {
            gameStatus = GameStatus.END;
        }

        final ChessPiece movablePiece = chessBoard.remove(from);
        chessBoard.put(to, movablePiece);
        currentTurnColor = currentTurnColor.toOpposite();
    }

    public Map<Color, Double> calculateScore() {
        return Arrays.stream(Color.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        color -> sumScoreExceptPawn(color) + sumPawnScore(color)));
    }

    private double sumScoreExceptPawn(final Color color) {
        return chessBoard.values().stream()
                .filter(chessPiece -> chessPiece.isSameColor(color))
                .filter(chessPiece -> !(chessPiece instanceof Pawn))
                .mapToDouble(ChessPiece::value)
                .sum();
    }

    private double sumPawnScore(final Color color) {
        return Arrays.stream(Rank.values())
                .mapToInt(rank -> countSameRankPawn(color, rank))
                .mapToDouble(Pawn::calculateScore)
                .sum();
    }

    private int countSameRankPawn(final Color color, final Rank rank) {
        return (int) Arrays.stream(File.values())
                .map(file -> findPiece(Position.of(rank, file)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(chessPiece -> chessPiece instanceof Pawn)
                .filter(pawn -> pawn.isSameColor(color))
                .count();
    }

    public boolean isReady() {
        return gameStatus.isReady();
    }

    public boolean isEnd() {
        return gameStatus.isEnd();
    }

    public boolean isPlaying() {
        return gameStatus.isPlaying();
    }

    public void start() {
        gameStatus = GameStatus.PLAYING;
    }
}
