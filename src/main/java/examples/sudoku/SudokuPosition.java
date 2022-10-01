package examples.sudoku;

public class SudokuPosition {
    int position, value;
    public SudokuPosition(int p, int v)
    {
        position = p;
        value = v;
    }

    public int getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }
}
