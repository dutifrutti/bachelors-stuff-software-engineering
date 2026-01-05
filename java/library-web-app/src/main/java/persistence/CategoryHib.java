package persistence;

import AccountingSystem.AccountingSystem;
import AccountingSystem.Category;
import AccountingSystem.User;
import GSONSerializable.CategoryGSONSerializer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

;

public class CategoryHib {
    EntityManagerFactory emf = null;
    public CategoryHib(EntityManagerFactory entityManagerFactory){
        this.emf = entityManagerFactory;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    UserHib userHib;
    public void create(Category category){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(category);
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

    public void edit(Category category){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();

        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally{
            if (em!=null)
                em.close();
        }


    }// prie šito sedėjau labai per ilgai, tai galimai prirašiau nebūtinu dalykų
    public void remove(int id){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
           Category cat;
           User user;
           try {
                cat = em.getReference(Category.class, id);
                cat.getId();

                AccountingSystem acc = cat.getAccountingSystem();
                if (acc!=null){
                acc.getCategories().remove(cat);
                em.merge(acc);}

                for (User u: cat.getResponsibleUser()){
                    u.getResponsibleCategories().remove(cat);
                    em.merge(u);

                }
               cat.getResponsibleUser().clear();
               em.merge(cat);
               Category parent = cat.getParentCategoryObj();
               if (parent!=null){
               parent.getSubCategory().remove(cat);
               em.merge(parent);
               }
                for (Category c: cat.getSubCategory()){
                    remove(c.getId());
                }
                cat.getSubCategory().clear();
                em.merge(cat);

//               if (cat.getParentCategory().equals("")){
//                   AccountingSystem acc = cat.getAccountingSystem();
//                   acc.getCategories().remove(cat);
//                   em.merge(acc);
//               }
//               else {
//                   Category parentCat=cat.getParentCategoryObj();
//                   parentCat.getSubCategory().remove(cat);
//                   em.merge(parentCat);
//               }

               em.remove(cat);

               em.flush();
               em.getTransaction().commit();

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally{
            if (em!=null)
                em.close();
        }
    }
    public List<Category> getCategoryList(){
        return getCategoryList(true,-1,-1);
    }
    public List<Category> getCategoryList(boolean all, int maxRes, int firstRes){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Category.class));
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

    public Category getCategoryByName (String name){
        for (Category c:getCategoryList()){
            if (c.getName().equals(name))
                return c;
        }
   return null; }
}
