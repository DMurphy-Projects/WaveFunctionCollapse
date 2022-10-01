package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SuperPosition<E, F> {

    ArrayList<State<E>> possibleStates = new ArrayList<State<E>>();
    ArrayList<Constraint<E, F>> constraints = new ArrayList<Constraint<E, F>>();

    static Random r = new Random();

    public void addState(State s)
    {
        possibleStates.add(s);
    }
    public void addStates(ArrayList<State<E>> s)
    {
        possibleStates.addAll(s);
    }

    public void addConstraint(Constraint c)
    {
        constraints.add(c);
    }
    public void addConstraints(ArrayList<Constraint<E, F>> c)
    {
        constraints.addAll(c);
    }

    public void update()
    {
        Iterator<State<E>> it = possibleStates.iterator();
        while (it.hasNext())
        {
            State<?> state = it.next();

            for (Constraint c: constraints)
            {
                if (!c.allowed(state)) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public int getEntropy()
    {
        return possibleStates.size();
    }

    public boolean hasCollapsed() {
        return possibleStates.size() == 0;
    }

    public State<E> collapse()
    {
        int choice = r.nextInt(possibleStates.size());
        State<E> colapsedState = possibleStates.get(choice);
        possibleStates.clear();

        return colapsedState;
    }
}
