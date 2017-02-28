package pgoggin.models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by patrickgoggin on 2/27/17.
 */
//@Transactional
@Component
public class ThingDaoImpl implements ThingDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ThingRepository repo;

    public Thing create(Thing thing){
        repo.save(thing);
        return thing;
    }

    public void createMultiple(Iterable<Thing> things){
        repo.save(things);
    }

    public List<Thing> getAll(){
        List<Thing> list = repo.findAll();
        return list;
    }

    public Thing getById(long id){
        Thing thing = repo.getOne(id);
        return thing;
    }

    public List<Thing> getByName(String name){
        List<Thing> things = entityManager.createQuery("from Thing where name = :name").setParameter("name", name).getResultList();
        return things;
    }

    public void update(Thing thing){
        entityManager.merge(thing);
    }

    public void delete(long id){
        repo.delete(id);
    }

    public void deleteMultiple(Iterable<Thing> things){
        repo.deleteInBatch(things);
    }

}

