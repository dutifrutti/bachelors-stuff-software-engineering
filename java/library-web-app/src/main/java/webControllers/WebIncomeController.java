package webControllers;

import AccountingSystem.*;
import GSONSerializable.IncomeGSONSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import persistence.CategoryHib;
import persistence.IncomeHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class WebIncomeController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab5_ign");
    IncomeHib incomeHib = new IncomeHib(entityManagerFactory);
    CategoryHib categoryHib = new CategoryHib(entityManagerFactory);

    @RequestMapping(value = "income/list")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllIncome() {
        List<Income> allIncome = incomeHib.getIncomeList();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Income.class, new IncomeGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allIncome.get(0));

        return parser.toJson(allIncome);
    }

    @RequestMapping(value = "income/byCatName", method = RequestMethod.GET) //?name= string
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getIncomeByCategoryByName(@RequestParam("name") String name){
        List <Income> allCatIncome;
        Category category = categoryHib.getCategoryByName(name);
        allCatIncome = category.getIncome();
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Income.class, new IncomeGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allCatIncome.get(0));
        return parser.toJson(allCatIncome);
    }

    @RequestMapping(value = "income/create") //?name= string
    public String createIncome(){
        return "createIncome";}

    @RequestMapping(value = "income/save")
    public String saveIncome(HttpServletRequest request){
        String catName = request.getParameter("catName");
        Category category = categoryHib.getCategoryByName(catName);
        double amount = Double.parseDouble(request.getParameter("amount"));
        Income income = new Income(request.getParameter("name"),amount,category);
        incomeHib.create(income);
  // return ("income/byCatName?name="+catName);
        return "redirect:byCatName?name="+catName;
    }
    @RequestMapping(value="income/editform")
    public ModelAndView editIncomeForm(HttpServletRequest request, @RequestParam("catname") String catname, @RequestParam("incname") String incname){
        Category category = categoryHib.getCategoryByName(catname);
        Income income = null;
        for (Income i: category.getIncome()){
            if (i.getName().equals(incname))
                income = i;
        }
        System.out.println(income);
        ModelAndView mav = new ModelAndView("editIncome");
        mav.addObject("income",income);
        return mav;
    }

    @RequestMapping(value = "income/editincome", method = RequestMethod.PUT)
    @ResponseBody
    //@ModelAttribute("income") Income income
    public String editIncome(@RequestBody String request){

        //System.out.println(income.getName()+income.getAmount());


    return "redirect:/income/list";}



}
