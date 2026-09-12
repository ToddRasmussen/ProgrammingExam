package chess;

public class ChessVector {
    
    private final int delta_row;
    private final int delta_col;

    public ChessVector(int delta_row, int delta_col) {
        this.delta_col = delta_col;
        this.delta_row = delta_row;
    }


    public int getDeltaRow() {
        return delta_row;
    }

    public int getDeltaCol() {
        return delta_col;
    }

    public boolean isVector() {
        return (delta_col != 0 || delta_row != 0);
    }
}
