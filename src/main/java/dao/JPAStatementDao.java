package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Statement;

@Repository(value = "statementDao")
public class JPAStatementDao implements StatementDao {

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
    public List<Statement> queryAll() {
        return em.createQuery("select d from statement d order by d.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void create(Statement d) {
        em.persist(d);
    }
    
    @Transactional(readOnly = true)
    public Statement read(int id) {
    	return em.find(Statement.class, id);
    }
    
    @Transactional(readOnly = false)
    public void update(Statement d) {
        em.merge(d);
    }
    
    @Transactional(readOnly = false)
    public void delete(Statement d) {
    	d = em.merge(d);
	    em.remove(d);
    }
    

}