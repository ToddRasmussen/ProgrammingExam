package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;


    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left column
     */
    public int getColumn() {
        return col;
    }


    public boolean isValid() {
        return (
            getRow() <= 8 && getRow() > 0
            && getColumn() <= 8 && getColumn() > 0
        );
    }

    public ChessPosition addVector(ChessVector vector) {
        return new ChessPosition(getRow() + vector.getDeltaRow(), getColumn() + vector.getDeltaCol());
    }

    @Override
    public String toString() {
        return "Row: " + getRow() + " Column: " + getColumn();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChessPosition) {
            ChessPosition other = (ChessPosition) obj;
            return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
