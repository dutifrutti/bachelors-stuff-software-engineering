package GSONSerializable;


import AccountingSystem.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class UserGSONSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject usJson = new JsonObject();
        usJson.addProperty("Name: ", user.getName());
        usJson.addProperty(" Last name: ", user.getSurname());
        return usJson;
    }
}
