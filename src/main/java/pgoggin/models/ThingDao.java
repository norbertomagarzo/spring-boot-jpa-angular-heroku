package pgoggin.models;


import java.util.List;

/**
 * Created by patrickgoggin on 2/27/17.
 */
public interface ThingDao {
    public Thing create(Thing thing);
    public void createMultiple(Iterable<Thing> things);
    public List<Thing> getAll();
    public Thing getById(long id);
    public List<Thing> getByName(String name);
    public void update(Thing thing);
    public void delete(long id);
    public void deleteMultiple(Iterable<Thing> things);
}
