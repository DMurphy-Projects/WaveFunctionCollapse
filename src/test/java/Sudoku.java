import examples.sudoku.SudokuGrid;
import examples.sudoku.SudokuPosition;
import model.Constraint;
import model.State;
import model.SuperPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Sudoku {
    public static void main(String[] args)
    {
        SudokuGrid grid = new SudokuGrid(new int[]{
                0, 0, 0,    0, 2, 0,    0, 0, 0,
                1, 6, 0,    5, 0, 9,    0, 0, 0,
                0, 0, 8,    7, 4, 6,    0, 0, 9,

                9, 1, 0,    0, 7, 2,    0, 6, 0,
                0, 0, 7,    0, 0, 0,    3, 0, 0,
                0, 0, 0,    6, 3, 8,    1, 9, 7,

                0, 0, 0,    2, 0, 0,    0, 0, 3,
                0, 0, 0,    0, 0, 0,    8, 0, 0,
                0, 4, 0,    0, 1, 0,    9, 0, 0
        });

        final Constraint<SudokuPosition, SudokuGrid> rowConstraint = new Constraint<SudokuPosition, SudokuGrid>(grid) {
            @Override
            protected boolean allowed(State<SudokuPosition> state) {
                int[] row = model.getRow(model.positionToRow(state.getModelState().getPosition()));
                return !Arrays.stream(row).anyMatch(x -> x == state.getModelState().getValue());
            }
        };
        final Constraint<SudokuPosition, SudokuGrid> columnConstraint = new Constraint<SudokuPosition, SudokuGrid>(grid) {
            @Override
            protected boolean allowed(State<SudokuPosition> state) {
                int[] col = model.getColumn(model.positionToColumn(state.getModelState().getPosition()));
                return !Arrays.stream(col).anyMatch(x -> x == state.getModelState().getValue());
            }
        };
        final Constraint<SudokuPosition, SudokuGrid> gridConstraint = new Constraint<SudokuPosition, SudokuGrid>(grid) {
            @Override
            protected boolean allowed(State<SudokuPosition> state) {
                int[] iGrid = model.getInnerGrid(model.positionToInnerGrid(state.getModelState().getPosition()));
                return !Arrays.stream(iGrid).anyMatch(x -> x == state.getModelState().getValue());
            }
        };

        ArrayList<Constraint<SudokuPosition, SudokuGrid>> constraints = new ArrayList<Constraint<SudokuPosition, SudokuGrid>>(){
            {
                add(rowConstraint);
                add(columnConstraint);
                add(gridConstraint);
            }
        };

        ArrayList<SuperPosition<SudokuPosition, SudokuGrid>> superPositionGrid = new ArrayList<SuperPosition<SudokuPosition, SudokuGrid>>();
        for (int i=0;i<81;i++)
        {
            if (grid.getPosition(i) == 0) {
                SuperPosition<SudokuPosition, SudokuGrid> position = new SuperPosition<SudokuPosition, SudokuGrid>();
                for (int j = 1; j < 10; j++) {
                    position.addState(new State<SudokuPosition>(new SudokuPosition(i, j)));
                }
                position.addConstraints(constraints);

                superPositionGrid.add(position);
            }
        }

        Comparator<SuperPosition> comparator = new Comparator<SuperPosition>() {
            public int compare(SuperPosition o1, SuperPosition o2) {
                return o1.getEntropy() - o2.getEntropy();
            }
        };

        for (int i=0;i<superPositionGrid.size();i++)
        {
            for (SuperPosition s: superPositionGrid)
            {
                s.update();
            }
            Collections.sort(superPositionGrid, comparator);

            for (SuperPosition s: superPositionGrid)
            {
                if (!s.hasCollapsed())
                {
                    State<SudokuPosition> state = s.collapse();
                    grid.setPosition(state.getModelState().getPosition(), state.getModelState().getValue());
                    break;
                }
            }
        }

        System.out.println(grid);

        for (int i=0;i<81;i++)
        {
            if (grid.getPosition(i) == 0)
            {
                System.out.println("Sudoku Invalid");
            }
        }
    }
}
