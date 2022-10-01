package model;

public class State<E> {

    E modelState;

    public State(E mS)
    {
        modelState = mS;
    }

    public E getModelState() {
        return modelState;
    }
}
