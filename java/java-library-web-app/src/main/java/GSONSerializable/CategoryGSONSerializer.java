package GSONSerializable;

import AccountingSystem.Category;


import AccountingSystem.Expense;
import AccountingSystem.Income;
import AccountingSystem.User;
import com.google.gson.*;


import javax.json.Json;
import java.lang.reflect.Type;

public class CategoryGSONSerializer implements JsonSerializer<Category> {

    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject catJson = new JsonObject();
        catJson.addProperty("name", category.getName());
        catJson.addProperty("description", category.getDescription());
        catJson.addProperty("dateCreated", category.getDateCreated().toString());


        JsonArray subcategoryarray = new JsonArray();
        JsonObject subcategories = new JsonObject();
        if (category.getSubCategory()!=null){
            for (Category subc : category.getSubCategory()){
        subcategoryarray.add(serialize(subc,type, jsonSerializationContext));}
        }
        catJson.add("subCategory",subcategoryarray);

        JsonArray users = new JsonArray();
        JsonObject user = new JsonObject();
        if (category.getResponsibleUser()!=null) {
            for (User u : category.getResponsibleUser()) {
                user.addProperty("name", u.getName());
                user.addProperty("surname", u.getSurname());
                users.add(user);
            }
        }

        catJson.add("responsibleUser",users);

        JsonArray incomeArray = new JsonArray();
        JsonObject income = new JsonObject();
        for (Income c : category.getIncome()){
        income.addProperty("name",c.getName());
        income.addProperty("amount", c.getAmount());
            incomeArray.add(income);
        }

        catJson.add("income", incomeArray);

        JsonArray expenseArray = new JsonArray();
        JsonObject expense = new JsonObject();
        for (Expense e : category.getExpense()){
            expense.addProperty("name",e.getName());
            expense.addProperty("amount", e.getAmount());
            expenseArray.add(expense);
        }

        catJson.add("expense", expenseArray);

        catJson.addProperty("parentCategory",category.getParentCategory());



        return catJson;
    }
}
