package persistence;

import AccountingSystem.Category;
import AccountingSystem.Expense;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ExpenseHib { EntityManagerFactory emf = null;

    public ExpenseHib(EntityManagerFactory entityManagerFactory){
        this.emf = entityManagerFactory;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Expense expense){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(expense);
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

    public void edit(Expense expense){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(expense);
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
            Expense expense = null;
            try {
                expense = em.getReference(Expense.class, id);
                expense.getId();
                Category cat = expense.getCategory();
                cat.getExpense().remove(expense);
                em.merge(cat);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            em.remove(expense);
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
    public List<Expense> getExpenseList(){
        return getExpenseList(true,-1,-1);
    }
    public List<Expense> getExpenseList(boolean all, int maxRes, int firstRes){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Expense.class));
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

    public Expense findIncomeById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Expense.class, id);
        } finally {
            em.close();
        }
    }
}
