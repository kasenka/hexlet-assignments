package exercise.controller;

import org.apache.commons.lang3.StringUtils;
import exercise.util.Security;
import exercise.model.User;
import exercise.util.NamedRoutes;
import static io.javalin.rendering.template.TemplateUtil.model;
import exercise.repository.UserRepository;
import exercise.dto.users.UserPage;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.Context;


public class UsersController {

    public static void build(Context ctx) throws Exception {
        ctx.render("users/build.jte");
    }

    // BEGIN
    public static void postForm(Context ctx){
        var firstName = ctx.formParam("firstName");
        var lastName = ctx.formParam("lastName");
        var email = ctx.formParam("email");
        var password = ctx.formParam("password");

        String token = Security.generateToken();
        ctx.cookie("token", token);

        User user = new User(firstName,lastName,email,password,token);
        UserRepository.save(user);

        ctx.redirect("/users/" + user.getId());

    }

    public static void show(Context ctx){
        var tokenCookie = ctx.cookie("token");

        var id = ctx.pathParam("id");
        User user = UserRepository.find(Long.parseLong(id)).orElse(null);

        if (user.getToken().equals(tokenCookie)) {
            UserPage page = new UserPage(user);
            ctx.render("users/show.jte", model("page", page));
        }else{
            ctx.redirect("/users/build");
        }

    }

    // END
}
