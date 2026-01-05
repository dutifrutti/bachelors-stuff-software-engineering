package persistence;

import AccountingSystem.Category;
import AccountingSystem.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class UserHib { EntityManagerFactory emf = null;

    public UserHib(EntityManagerFactory entityManagerFactory){
        this.emf = entityManagerFactory;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
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

    public void edit(User user){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(user);
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
            User user = null;
            try {
                user = em.getReference(User.class, id);
                user.getId();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            em.persist(user);
      System.out.println("remov"+user.getName());
            em.remove(user);
            em.flush();
            em.clear();
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
    public List<User> getUserList(){
        return getUserList(true,-1,-1);
    }
    public List<User> getUserList(boolean all, int maxRes, int firstRes){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
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

    public User findUserById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public User getUserByName (String name){
        for (User u:getUserList()){
            if (u.getName().equals(name))
                return u;
        }
        return null; }
}
