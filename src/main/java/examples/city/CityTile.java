package examples.city;

public class CityTile {

    int id;

    //[direction][list of valid id]
    int[][] valid;

    public CityTile(int id, int[][] valid)
    {
        this.id = id;
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public int[][] getValid() {
        return valid;
    }

    public int[] getValid(int d) {
        return valid[d];
    }
}
