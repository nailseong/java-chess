package chess.domain;

import chess.domain.chessPiece.*;
import chess.domain.position.Position;

import java.util.*;

public class ChessBoard {

    private Map<Position, ChessPiece> chessBoard;
    private Color currentTurn = Color.WHITE;

    public ChessBoard() {
        chessBoard = new HashMap<>();
        init();
    }

    private void init() {
        for (Color value : Color.values()) {
            List<ChessPiece> pieces = List.of(
                    new King(value),
                    new Queen(value),
                    new Pawn(value),
                    new Rook(value),
                    new Bishop(value),
                    new Knight(value));

            for (ChessPiece chessPiece : pieces) {
                initByPiece(chessPiece);
            }
        }
    }

    private void initByPiece(ChessPiece chessPiece) {
        if (chessPiece.isBlack()) {
            for (Position position : chessPiece.getInitBlackPosition()) {
                chessBoard.put(position, chessPiece);
            }
            return;
        }
        for (Position position : chessPiece.getInitWhitePosition()) {
            chessBoard.put(position, chessPiece);
        }
    }

    public void move(Position from, Position to) {
        ChessPiece me = findPiece(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));

        if (me instanceof Pawn) {
            movePawn(from, to, (Pawn) me);
            return;
        }

        checkMove(from, to, me);

        if (findPiece(to).isEmpty() || enemyExist(me, to)) {
            movePiece(from, to, me);
        }
    }

    // movePawn(직진,대각선), checkMove, enemyExist 예외

    private void checkMove(Position from, Position to, ChessPiece me){
        me.canMove(from, to);
        Stack<Position> routes = me.findRoute(from, to);

        while (!routes.isEmpty()) {
            Position position = routes.pop();
            if (findPiece(position).isPresent()) {
                throw new IllegalArgumentException("이동 경로 사이에 다른 기물이 있습니다.");
            }
        }
    }

    private void movePiece(Position from, Position to, ChessPiece me) {
        chessBoard.put(to, me);
        chessBoard.remove(from);
        currentTurn = currentTurn.toOpposite();
    }

    private void movePawn(Position from, Position to, Pawn me) {
        if (from.isSameRank(to)) {
            me.canMove(from, to);
            if (findPiece(to).isPresent()) {
                throw new IllegalArgumentException("이동 경로 사이에 다른 기물이 있습니다.");
            }

            movePiece(from, to, me);
            return;
        }

        if (!from.isSameRank(to)) {
            me.checkCrossMove(from, to);
            if (enemyExist(me, to)) {
                movePiece(from, to, me);
            }
        }
    }

    public boolean enemyExist(ChessPiece me, Position to) {
        Optional<ChessPiece> possiblePiece = findPiece(to);
        if (possiblePiece.isEmpty()) {
            throw new IllegalArgumentException("폰은 대각선에 상대 기물이 존재해야합니다");
        }

        ChessPiece piece = possiblePiece.get();
        if (piece.isSameColor(me)) {
            throw new IllegalArgumentException("같은색 기물입니다.");
        }

        return true;
    }

    public int countPiece() {
        return chessBoard.size();
    }

    public Optional<ChessPiece> findPiece(Position position) {
        ChessPiece piece = chessBoard.get(position);
        if (piece == null) {
            return Optional.empty();
        }

        return Optional.of(piece);
    }
}