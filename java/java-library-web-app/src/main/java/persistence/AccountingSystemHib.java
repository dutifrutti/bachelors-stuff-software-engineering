package persistence;

import AccountingSystem.AccountingSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AccountingSystemHib {
    EntityManagerFactory emf = null;

    public AccountingSystemHib(EntityManagerFactory entityManagerFactory){
        this.emf = entityManagerFactory;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountingSystem asis){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asis);
            em.getTransaction().commit();

        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally{
            if (em!=null)
                em.close();
        }
    }

    public void edit(AccountingSystem asis){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(asis);
            em.getTransaction().commit();

        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally{
            if (em!=null)
                em.close();
        }


    }
    public void remove(int id){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            AccountingSystem as = null;
            try {
                as = em.getReference(AccountingSystem.class, id);
                as.getId();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            em.remove(as);
            em.getTransaction().commit();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally{
            if (em!=null)
                em.close();
        }
    }
    public List<AccountingSystem> getAccountingSystemList(){
        return getAccountingSystemList(true,-1,-1);
    }
    public List<AccountingSystem> getAccountingSystemList(boolean all, int maxRes, int firstRes){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(AccountingSystem.class));
            Query query = em.createQuery(criteriaQuery);
            if (!all){
                query.setFirstResult(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (em!=null)
                em.close();
        }
        return null;
    }

    public AccountingSystem findAccountingSystem(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountingSystem.class, name);
        } finally {
            em.close();
        }
    }
    public AccountingSystem getFirstAccountingSystem (){
        return getAccountingSystemList().get(0);
    }

}
