package examples.sudoku;

public class SudokuGrid {
    int[] structure;

    public SudokuGrid()
    {
        structure = new int[3*3*3*3];
        for (int i=0;i<structure.length;i++)
        {
            structure[i] = 0;
        }
    }

    public SudokuGrid(int[] puzzle)
    {
        structure = puzzle;
    }

    public int positionToRow(int position)
    {
        return position / 9;
    }
    public int positionToColumn(int position)
    {
        return position % 9;
    }
    public int positionToInnerGrid(int position)
    {
        int row = positionToRow(position) / 3;
        int col = positionToColumn(position) / 3;

        return (row * 3) + col;
    }

    public int[] getRow(int rowNumber)
    {
        int[] row = new int[9];

        int offset = rowNumber * 9;
        for (int i=offset, j=0; i<offset+9;i++, j++)
        {
            row[j] = structure[i];
        }

        return row;
    }

    public int[] getColumn(int columnNumber)
    {
        int[] column = new int[9];
        int offset = columnNumber;
        for (int i=offset, j=0; j<9;i+=9, j++)
        {
            column[j] = structure[i];
        }

        return column;
    }

    public int[] getInnerGrid(int gridNumber)
    {
        int[] grid = new int[9];

        int row = gridNumber / 3, column = gridNumber % 3;
        int start = (row * 3 * 9) + (column * 3);

        for (int j=0;j<3;j++) {
            for (int i = 0; i < 3; i++) {
                grid[i + (j*3)] = structure[start + i + (j * 9)];
            }
        }

        return grid;
    }

    public int getPosition(int position) {
        return structure[position];
    }

    public void setPosition(int position, int value)
    {
        structure[position] = value;
    }

    @Override
    public String toString() {
        String s = "";

        for (int i=0;i<9;i++)
        {
            int[] row = getRow(i);
            for (int j=0;j<row.length;j++)
            {
                s += row[j] + " ";
                if (j % 3 == 2)
                {
                    s += " ";
                }
            }
            s += '\n';
            if (i % 3 == 2)
            {
                s += '\n';
            }
        }

        return s;
    }
}
