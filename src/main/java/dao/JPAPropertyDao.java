package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Property;

@Repository(value = "propertyDao")
public class JPAPropertyDao implements PropertyDao {

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
    public List<Property> queryAll() {
        return em.createQuery("select d from property d order by d.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void create(Property d) {
        em.persist(d);
    }
    
    @Transactional(readOnly = true)
    public Property read(int id) {
    	return em.find(Property.class, id);
    }
    
    @Transactional(readOnly = false)
    public void update(Property d) {
        em.merge(d);
    }
    
    @Transactional(readOnly = false)
    public void delete(Property d) {
    	d = em.merge(d);
	    em.remove(d);
    }
    

}