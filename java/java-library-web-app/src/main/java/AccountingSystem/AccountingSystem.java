package AccountingSystem;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class AccountingSystem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int accid;
  private String companyName;
  private String email;
  private Date dateCreated;
  private String systemVersion;
    @OneToMany(mappedBy = "accountingSystem", cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
  private List<Category> categories = new ArrayList<Category>();
    @OneToMany(mappedBy = "accountingSystem", cascade= CascadeType.ALL, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
  private List<User> systemUsers = new ArrayList<User>();


  public AccountingSystem() {}

    public AccountingSystem(String companyName, String email, Date dateCreated, String systemVersion) {
        this.companyName = companyName;
        this.email = email;
        this.dateCreated = dateCreated;
        this.systemVersion = systemVersion;

    }

    public int getId() {
        return accid;
    }

    public void setId(int accid) {
        this.accid = accid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public List<User> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(ArrayList<User> systemUsers) {
        this.systemUsers = systemUsers;
    }

    public double getCompanyBalance(){
      double balance = 0;
      for (Category currentCat: getCategories())
      {
          balance = getCompanyBalanceRecursive(currentCat, balance);
      }
    return balance;
  }
    private double getCompanyBalanceRecursive (Category currentCat, double balance){
      balance = balance + currentCat.getBalance();
        if (currentCat.getSubCategory().size() != 0) {
            for (Category subCat : currentCat.getSubCategory()) {
                balance = getCompanyBalanceRecursive(subCat, balance);
            }
            return balance;
        }
    return balance;
  }

    @Override
    public String toString() {
        return "AccountingSystem{" +
                "companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                ", systemVersion='" + systemVersion + '\'' +
                ", categories=" + categories +
                ", systemUser=" + systemUsers +
                '}';
    }

}
