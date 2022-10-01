import examples.sudoku.SudokuGrid;
import model.Constraint;
import model.State;
import model.SuperPosition;

import java.util.Arrays;

public class Testing {
    public static void main(String[] args)
    {
        SuperPosition<Integer, Object> superPosition = new SuperPosition<Integer, Object>();
        for (int i=0;i<10;i++)
        {
            superPosition.addState(new State<Integer>(i));
        }

        superPosition.addConstraint(new Constraint<Integer, Object>(0) {
            @Override
            protected boolean allowed(State<Integer> state) {
                return state.getModelState() > 5;
            }
        });

        superPosition.update();
    }
}
