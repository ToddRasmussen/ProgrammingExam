package chess;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private Map<ChessPosition, ChessPiece> board;

    public ChessBoard() {
        board = new HashMap<>();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board.put(position, piece);
    }


    public Map<ChessPosition, ChessPiece> getBoard() {
        return new HashMap<>(board);
    }


    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board.get(position);
    }


    public boolean isTeam(ChessPosition position, ChessGame.TeamColor team) {
        ChessPiece other = getPiece(position);
        if (other == null) {
            return false;
        }
        return other.getTeamColor() == team;
    }


    public boolean isPiece(ChessPosition position) {
        return (getPiece(position) != null);
    }

    public boolean isTeam(ChessPosition position, ChessPiece piece) {
        if (piece == null) {
            return false;
        }
        return isTeam(position, piece.getTeamColor());
    }


    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // r n b k q b n r
        // p p p p p p p p
        // ...
        // P P P P P P P P
        // R N B K Q B N R

        board = new HashMap<>();

        ChessPiece.PieceType[] baseCollection = {
            ChessPiece.PieceType.ROOK,
            ChessPiece.PieceType.KNIGHT,
            ChessPiece.PieceType.BISHOP,

            ChessPiece.PieceType.QUEEN,
            ChessPiece.PieceType.KING,

            ChessPiece.PieceType.BISHOP,
            ChessPiece.PieceType.KNIGHT,
            ChessPiece.PieceType.ROOK,
        };

        int totalColumns = 8;


        ChessGame.TeamColor team = ChessGame.TeamColor.BLACK;
        int i = 1;
        for (ChessPiece.PieceType pieceType : baseCollection) {
            ChessPiece piece = new ChessPiece(team, pieceType);
            ChessPosition position = new ChessPosition(8, i);
            i++;
            addPiece(position, piece);
        }

        for (i = 1; i <= totalColumns; i++) {
            ChessPiece piece = new ChessPiece(team, ChessPiece.PieceType.PAWN);
            ChessPosition position = new ChessPosition(7, i);
            addPiece(position, piece);
        }

        team = ChessGame.TeamColor.WHITE;

        for (i = 1; i <= totalColumns; i++) {
            ChessPiece piece = new ChessPiece(team, ChessPiece.PieceType.PAWN);
            ChessPosition position = new ChessPosition(2, i);
            addPiece(position, piece);
        }

        i = 1;
        for (ChessPiece.PieceType pieceType : baseCollection) {
            ChessPiece piece = new ChessPiece(team, pieceType);
            ChessPosition position = new ChessPosition(1, i);
            i++;
            addPiece(position, piece);
        }
    }

    @Override
    public String toString() {
        return board.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChessBoard) {
            ChessBoard other = (ChessBoard) obj;
            return (this.getBoard().equals(other.getBoard()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }
}
