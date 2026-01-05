package webControllers;

import AccountingSystem.*;
import GSONSerializable.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import persistence.AccountingSystemHib;
import persistence.CategoryHib;
import persistence.UserHib;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebCategoryController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab5_ign");
    CategoryHib categoryHib = new CategoryHib(entityManagerFactory);
    AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
    UserHib userHib = new UserHib(entityManagerFactory);

    @RequestMapping(value = "category/list")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCategories() {
        List<Category> allCats = categoryHib.getCategoryList();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allCats.get(0));
        return parser.toJson(allCats);

//        Type categoryList = new TypeToken<List<Category>>(){}.getType();
//        gson.registerTypeAdapter(categoryList, new AllCategoryGSONSerializer());
//        parser = gson.create();
//        return parser.toJson(allCats);
    }

    @RequestMapping(value = "category/byname", method = RequestMethod.GET) //?name= string
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryByName(@RequestParam("name") String name){
        Category category = categoryHib.getCategoryByName(name);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gson.create();

        return parser.toJson(category);
    }

    @RequestMapping(value = "category/create") //?name= string
    public String createCategory(){
    return "createCat";}

    @RequestMapping(value="category/save", method = RequestMethod.POST)
    public String saveCategory(HttpServletRequest request){
        AccountingSystem accountingSystem = accountingSystemHib.getFirstAccountingSystem();
        User user = new User("server",accountingSystem);
        userHib.create(user);
        ArrayList <User> users = new ArrayList<User>();
        users.add(user);
    String name=request.getParameter("name");
    String description =request.getParameter("description");
    String parentcategory=request.getParameter("parentcategory");
        if (parentcategory.equals("")){

            Category category = new Category(
                    name,
                    description,
                    LocalDate.now(),
                    users,
                    new ArrayList<Category>(),
                    parentcategory="",
                    new ArrayList<Income>(),
                    new ArrayList<Expense>(),
                    accountingSystem);
            categoryHib.create(category);
        }
        else {
            Category parenCat=categoryHib.getCategoryByName(parentcategory);
            Category category = new Category(
                    name,
                    description,
                    LocalDate.now(),
                    users,
                    new ArrayList<Category>(),
                    parentcategory,
                    new ArrayList<Income>(),
                    new ArrayList<Expense>(),
                    parenCat);
            categoryHib.create(category);
        }
    return "redirect:/category/list";
    }
    @RequestMapping(value="category/edit", method = RequestMethod.GET)
    public ModelAndView editCategory(HttpServletResponse response, HttpServletRequest request, @RequestParam("name") String name){
        Category category = categoryHib.getCategoryByName(name);
        ModelAndView mav = new ModelAndView("editCat");
        mav.addObject("category",category);
    return mav;}

    @RequestMapping(value="category/editCat", method = RequestMethod.POST)
    public String editCategoryConfirm(@ModelAttribute("category") Category category,HttpServletRequest request){
        System.out.println(category);
        Category parentCat;
        category = categoryHib.getCategoryByName(category.getName());
        AccountingSystem asis = category.getAccountingSystem();
        category.setName(request.getParameter("newName"));
        category.setDescription(request.getParameter("newDescription"));
        category.setParentCategory(request.getParameter("newParentCategory"));
        if (!request.getParameter("newParentCategory").equals("")){
        asis.getCategories().remove(category);
        category.setAccountingSystem(null);
        parentCat = categoryHib.getCategoryByName(request.getParameter("newParentCategory"));
        category.setParentCategoryObj(parentCat);
        }
        else {
            category.setAccountingSystem(accountingSystemHib.getFirstAccountingSystem());
            category.getParentCategoryObj().getSubCategory().remove(category);
            category.setParentCategoryObj(null);
        }
        categoryHib.edit(category);

    return "redirect:/category/list";}

    @RequestMapping(value="category/delete", method = RequestMethod.GET)
    public String deleteCategory(@RequestParam("name") String name){
        Category category = categoryHib.getCategoryByName(name);
        categoryHib.remove(category.getId());
    return "redirect:/category/list";}



}
