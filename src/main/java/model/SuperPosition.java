package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SuperPosition<E, F> {

    static Random r = new Random();

    int totalWeight = 0;
    ArrayList<State<E>> possibleStates = new ArrayList<State<E>>();

    ArrayList<Constraint<E, F>> constraints = new ArrayList<Constraint<E, F>>();

    public void addState(State s)
    {
        s.setBelongsTo(this);

        possibleStates.add(s);
        addTotalWeight(s.getWeight());
    }
    public void addStates(ArrayList<State<E>> s)
    {
        for (State<E> state: s)
        {
            addState(state);
        }
    }

    public void addConstraint(Constraint c)
    {
        constraints.add(c);
    }
    public void addConstraints(ArrayList<Constraint<E, F>> c)
    {
        constraints.addAll(c);
    }

    protected void addTotalWeight(int w)
    {
        totalWeight += w;
    }

    public void update()
    {
        Iterator<State<E>> it = possibleStates.iterator();
        while (it.hasNext())
        {
            State<?> state = it.next();

            for (Constraint c: constraints)
            {
                c.updateProbability(state);
            }

            if (state.getWeight() == 0)
            {
                it.remove();
            }
        }
    }

    public int getEntropy()
    {
        return possibleStates.size();
    }

    public boolean hasCollapsed() {
        return getEntropy() == 0;
    }

    public State<E> collapse()
    {
        int choice = r.nextInt(totalWeight);
        for (State collapsedState: possibleStates)
        {
            choice -= collapsedState.getWeight();
            if (choice <= 0)
            {
                possibleStates.clear();
                return collapsedState;
            }
        }

        return null;
    }
}
