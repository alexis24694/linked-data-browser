package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Resource;

@Repository(value = "resourceDao")
public class JPAResourceDao implements ResourceDao {

    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Resource> queryAll() {
        return em.createQuery("select d from resource d order by d.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void create(Resource d) {
        em.persist(d);
    }
    
    @Transactional(readOnly = true)
    public Resource read(int id) {
    	return em.find(Resource.class, id);
    }
    
    @Transactional(readOnly = false)
    public void update(Resource d) {
        em.merge(d);
    }
    
    @Transactional(readOnly = false)
    public void delete(Resource d) {
    	d = em.merge(d);
	    em.remove(d);
    }
    

}