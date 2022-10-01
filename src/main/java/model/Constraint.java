package model;

public abstract class Constraint<E, F> {

    protected F model;

    public Constraint(F m)
    {
        model = m;
    }

    protected abstract boolean allowed(State<E> state);
}
