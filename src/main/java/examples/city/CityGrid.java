package examples.city;

public class CityGrid {

    int width = 5, height = 5;
    CityTile[] grid;

    public CityGrid(int w, int h)
    {
        width = w;
        height = h;

        grid = new CityTile[width * height];
    }

    public void setPosition(int pos, CityTile tile)
    {
        grid[pos] = tile;
    }

    public CityTile getPosition(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
        {
            return null;
        }
        return getPosition(x + y * width);
    }

    public CityTile getPosition(int pos)
    {
        return grid[pos];
    }

    public CityTile[] getSurrounding(int pos)
    {
        return getSurrounding(pos % width, pos / width);
    }

    public CityTile[] getSurrounding(int x, int y)
    {
        return new CityTile[]{
                getPosition(x, y-1),
                getPosition(x+1, y),
                getPosition(x, y+1),
                getPosition(x-1, y),
        };
    }
}
