package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Dataset;

@Repository(value = "datasetDao")
public class JPADatasetDao implements DatasetDao {

    private EntityManager em = null;
    
    @PersistenceUnit(unitName = "linkeddatabrowserPU")
    private EntityManagerFactory entityManagerFactory;
    
    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Dataset> queryAll() {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
        return em.createQuery("select d from Dataset d order by d.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void create(Dataset d) {
        em.persist(d);
    }
    
    @Transactional(readOnly = true)
    public Dataset read(int id) {
    	return em.find(Dataset.class, id);
    }
    
    @Transactional(readOnly = false)
    public void update(Dataset d) {
        em.merge(d);
    }
    
    @Transactional(readOnly = false)
    public void delete(Dataset d) {
    	d = em.merge(d);
	    em.remove(d);
    }
    

}