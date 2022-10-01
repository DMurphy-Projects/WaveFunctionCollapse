import examples.city.CityGrid;
import examples.city.CityTile;
import examples.city.CityTilePosition;
import model.Constraint;
import model.State;
import model.SuperPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class CityBuilder {
    public static void main(String[] args)
    {
        int w = 10, h = 5;

        CityGrid cityGrid = new CityGrid(w, h);
        int id = 0;

        CityTile blank = new CityTile(id++, new int[][]{
                {0, 2},
                {0, 1},
                {0, 2},
                {0, 1},
        });

        CityTile road_ns = new CityTile(id++, new int[][]{
                {1, 3},
                {0},
                {1, 3},
                {0},
        });

        CityTile road_ew = new CityTile(id++, new int[][]{
                {0},
                {2, 3},
                {0},
                {2, 3},
        });

        CityTile road_cross = new CityTile(id++, new int[][]{
                {1},
                {2},
                {1},
                {2},
        });
        CityTile[] lookup = new CityTile[id];
        lookup[0] = blank;
        lookup[1] = road_ns;
        lookup[2] = road_ew;
        lookup[3] = road_cross;

        //constraints
        final Constraint<CityTilePosition, CityGrid> tileConstraints = new Constraint<CityTilePosition, CityGrid>(cityGrid) {
            @Override
            protected boolean allowed(State<CityTilePosition> state) {
                int pos = state.getModelState().getPosition();
                int id = state.getModelState().getTile().getId();

                CityTile[] surrounding = model.getSurrounding(pos);

                for (int i=0;i<4;i++)
                {
                    if (surrounding[i] == null) continue;

                    int valid[] = lookup[id].getValid(i);
                    int finalI = i;
                    if (!Arrays.stream(valid).anyMatch(_i -> _i == surrounding[finalI].getId()))
                    {
                        return false;
                    }
                }
                return true;
            }
        };

        ArrayList<SuperPosition<CityTile, CityGrid>> superPositionGrid = new ArrayList<SuperPosition<CityTile, CityGrid>>();

        //setup super positions
        for (int i=0;i<w*h;i++)
        {
            SuperPosition<CityTile, CityGrid> sp = new SuperPosition<>();
            sp.addConstraint(tileConstraints);

            for (CityTile t: lookup)
            {
                sp.addState(new State<CityTilePosition>(new CityTilePosition(t, i)));
            }

            superPositionGrid.add(sp);
        }

        Comparator<SuperPosition> comparator = new Comparator<SuperPosition>() {
            public int compare(SuperPosition o1, SuperPosition o2) {
                return o1.getEntropy() - o2.getEntropy();
            }
        };

        //collapse into concrete positions
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
                    State<CityTilePosition> state = s.collapse();
                    cityGrid.setPosition(state.getModelState().getPosition(), state.getModelState().getTile());
                    break;
                }
            }
        }

        //print city grid
        for (int i=0;i<w*h;i++)
        {
            if (i % w == 0)
            {
                System.out.println();
            }
            System.out.print(cityGrid.getPosition(i).getId() + " ");
        }
    }
}
