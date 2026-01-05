package webControllers;

import AccountingSystem.Category;
import AccountingSystem.User;
import GSONSerializable.CategoryGSONSerializer;
import GSONSerializable.UserGSONSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persistence.UserHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Properties;

@Controller
public class WebUserController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab5_ign");
    UserHib userHib = new UserHib(entityManagerFactory);

    @RequestMapping(value = "user/list")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsers() {
        List<User> allCats = userHib.getUserList();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allCats.get(0));

        return parser.toJson(allCats);
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserByName(@RequestBody String request){
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String loginName = data.getProperty("login");
        System.out.println(loginName);
        User user = userHib.getUserByName(loginName);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        parser = gson.create();

        if (user == null) {
            return "Wrong credentials";
        }
        else
        return parser.toJson(user);
}
}
