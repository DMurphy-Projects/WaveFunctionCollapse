package model;

public class State<E> {

    SuperPosition belongsTo;

    E modelState;
    int weight;

    public State(E mS, int w)
    {
        modelState = mS;
        weight = w;
    }

    public E getModelState() {
        return modelState;
    }

    public void setBelongsTo(SuperPosition sp)
    {
        belongsTo = sp;
    }

    public void updateWeight(int w)
    {
        int delta = w - weight;
        belongsTo.addTotalWeight(delta);

        weight = w;
    }

    public int getWeight() {
        return weight;
    }
}
