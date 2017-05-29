package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Ontology;

@Repository(value = "ontologyDao")
public class JPAOntologyDao implements OntologyDao {

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
    public List<Ontology> queryAll() {
        return em.createQuery("select d from ontology d order by d.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void create(Ontology d) {
        em.persist(d);
    }
    
    @Transactional(readOnly = true)
    public Ontology read(int id) {
    	return em.find(Ontology.class, id);
    }
    
    @Transactional(readOnly = false)
    public void update(Ontology d) {
        em.merge(d);
    }
    
    @Transactional(readOnly = false)
    public void delete(Ontology d) {
    	d = em.merge(d);
	    em.remove(d);
    }
    

}