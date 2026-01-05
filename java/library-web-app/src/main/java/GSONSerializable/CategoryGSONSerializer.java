package GSONSerializable;

import AccountingSystem.Category;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


import java.lang.reflect.Type;

public class CategoryGSONSerializer implements JsonSerializer<Category> {
    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject catJson = new JsonObject();
        catJson.addProperty("Name: ", category.getName());
        catJson.addProperty(" Description: ", category.getDescription());
        catJson.addProperty(" DateCreated: ", category.getDateCreated().toString());
        catJson.addProperty(" Subcategories: ", category.getAllSubcategoryNames().toString());
        catJson.addProperty("ResponsibleUsers", category.getResponsibleUser().toString());
        catJson.addProperty("Income", category.getIncome().toString());
        catJson.addProperty("Expense", category.getExpense().toString());
        catJson.addProperty("ParentCat", category.getParentCategory());

        return catJson;
    }
}
