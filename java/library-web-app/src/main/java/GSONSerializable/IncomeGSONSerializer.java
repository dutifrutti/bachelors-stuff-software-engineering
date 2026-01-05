package GSONSerializable;

import AccountingSystem.Income;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class IncomeGSONSerializer implements JsonSerializer<Income> {
    @Override
    public JsonElement serialize(Income income, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject catJson = new JsonObject();
        catJson.addProperty("Name: ", income.getName());
        catJson.addProperty(" Description: ", income.getAmount());
        return catJson;
    }
}
