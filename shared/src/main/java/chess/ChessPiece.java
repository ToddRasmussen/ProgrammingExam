package chess;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (getPieceType()) {
            case PieceType.KING:
                return kingMoves(board, myPosition);
            case PieceType.QUEEN:
                Collection<ChessMove> moves = new LinkedList<>();
                moves.addAll(horizontalMoves(board, myPosition));
                moves.addAll(diagonalMoves(board, myPosition));
                return moves;
            case PieceType.BISHOP:
                return diagonalMoves(board, myPosition);
            case PieceType.KNIGHT:
                return knightMoves(board, myPosition);
            case PieceType.ROOK:
                return horizontalMoves(board, myPosition);
            case PieceType.PAWN:
                return pawnMoves(board, myPosition);

            default:
                return null;
        }
    }




    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();
        int[] directions = {-1, 0, 1};
        for (int row : directions) {
            for (int col : directions) {
                ChessVector vector = new ChessVector(row, col);
                ChessPosition position = myPosition.addVector(vector);
                if (vector.isVector() && position.isValid() && !board.isTeam(position, board.getPiece(myPosition))) {
                    moves.add(new ChessMove(myPosition,position, null));
                }
            }
        }
        return moves;
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();
        int[] longDirection = {-2, 2};
        int[] shortDirection = {-1, 1};
        for (int row : longDirection) {
            for (int col : shortDirection) {
                ChessVector vector = new ChessVector(row, col);
                ChessPosition position = myPosition.addVector(vector);
                if (vector.isVector() && position.isValid() && !board.isTeam(position, board.getPiece(myPosition))) {
                    moves.add(new ChessMove(myPosition,position, null));
                }
            }
        }
        for (int row : shortDirection) {
            for (int col : longDirection) {
                ChessVector vector = new ChessVector(row, col);
                ChessPosition position = myPosition.addVector(vector);
                if (vector.isVector() && position.isValid() && !board.isTeam(position, board.getPiece(myPosition))) {
                    moves.add(new ChessMove(myPosition,position, null));
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece.PieceType[] promotions = {
            ChessPiece.PieceType.QUEEN,
            ChessPiece.PieceType.BISHOP,
            ChessPiece.PieceType.KNIGHT,
            ChessPiece.PieceType.ROOK
        };
        Collection<ChessMove> moves = new LinkedList<>();
        int row = board.getPiece(myPosition).getTeamColor().getDirection();
        //NO TAKE
        ChessVector vector = new ChessVector(row, 0);
        ChessPosition position = myPosition.addVector(vector);
        if (position.isValid() && !board.isPiece(position)) {
            if (position.getRow() == this.getTeamColor().inverse().getHomeRow()) {
                for (ChessPiece.PieceType type : promotions) {
                    moves.add(new ChessMove(myPosition,position, type));
                }
            } else {
                moves.add(new ChessMove(myPosition,position, null));
            }
            // Double Move
            position = position.addVector(vector);
            if (myPosition.getRow() == this.getTeamColor().getPawnRow() && position.isValid() && !board.isPiece(position)) {
                moves.add(new ChessMove(myPosition,position, null));
            }
        }
        //TAKE
        int[] direction = {-1, 1};
        for (int col : direction) {
            vector = new ChessVector(row, col);
            position = myPosition.addVector(vector);
            if (position.isValid() && board.isPiece(position) && board.isTeam(position, this.getTeamColor().inverse())) {
                if (position.getRow() == this.getTeamColor().inverse().getHomeRow()) {
                    for (ChessPiece.PieceType type : promotions) {
                        moves.add(new ChessMove(myPosition,position, type));
                    }
                } else {
                    moves.add(new ChessMove(myPosition,position, null));
                }
            }
        }
        return moves;
    }


    private Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();
        int[] directions = {-1, 1};
        for (int row : directions) {
            for (int col : directions) {
                ChessVector vector = new ChessVector(row, col);
                moves.addAll(vectorMoves(board, myPosition, vector));
            }
        }

        return moves;
    }


    private Collection<ChessMove> horizontalMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();
        int[] directions = {-1, 1};
        for (int row : directions) {
            ChessVector vector = new ChessVector(row, 0);
            moves.addAll(vectorMoves(board, myPosition, vector));
        }
        for (int col : directions) {
            ChessVector vector = new ChessVector(0, col);
            moves.addAll(vectorMoves(board, myPosition, vector));
        }
        return moves;
    }


    private Collection<ChessMove> vectorMoves(ChessBoard board, ChessPosition myPosition, ChessVector vector) {
        Collection<ChessMove> moves = new LinkedList<>();

        ChessPosition newPosition = myPosition.addVector(vector);
        
        while (newPosition.isValid()) {
            if (!board.isPiece(newPosition)) {
                moves.add(new ChessMove(myPosition,newPosition, null));
            } else if (/* Must be a Piece */ !board.isTeam(newPosition, this)) {
                moves.add(new ChessMove(myPosition,newPosition, null));
                break;
            } else if (board.isTeam(newPosition, this)) {
                break;
            }


            newPosition = newPosition.addVector(vector);
        }

        return moves;
    }



    @Override
    public String toString() {
        return " Type: " + getPieceType().toString() + " Color: " + getTeamColor().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChessPiece) {
            ChessPiece other = (ChessPiece) obj;
            return (this.getPieceType() == other.getPieceType() && this.getTeamColor() == other.getTeamColor());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }


}
