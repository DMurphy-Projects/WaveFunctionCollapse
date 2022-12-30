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
            superPosition.addState(new State<Integer>(i, i));
        }

        superPosition.addConstraint(new Constraint<Integer, Object>(0) {
            @Override
            protected void updateProbability(State<Integer> state) {
                if (state.getModelState() <= 5)
                {
                    state.updateWeight(0);
                };
            }
        });

        superPosition.update();
        System.out.println(superPosition.collapse().getModelState());
    }
}
