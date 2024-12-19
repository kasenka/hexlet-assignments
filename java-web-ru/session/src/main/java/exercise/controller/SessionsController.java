package exercise.controller;

import static io.javalin.rendering.template.TemplateUtil.model;
import exercise.dto.MainPage;
import exercise.dto.LoginPage;
import exercise.model.User;
import exercise.repository.UsersRepository;
import static exercise.util.Security.encrypt;

import exercise.util.NamedRoutes;
import exercise.util.Security;
import io.javalin.http.Context;

public class SessionsController {

    // BEGIN

    public static void build(Context ctx){
        LoginPage page = new LoginPage(null, null);
        ctx.render("build.jte", model("page", page));
    }

    public static void login(Context ctx){
        var name = ctx.formParam("name");
        var password = ctx.formParam("password");

        ctx.sessionAttribute("currentUser", name);

        User user = UsersRepository.findByName(name).orElse(null);
        if ( user != null && user.getPassword().equals(Security.encrypt(password))){

            ctx.redirect(NamedRoutes.rootPath());
        }else{
            LoginPage page = new LoginPage(name,"Wrong username or password");
            ctx.render("build.jte", model("page", page));
        }
    }

    public static void form(Context ctx){

        ctx.render("destroy.jte");
    }

    public static void logout(Context ctx){

        ctx.sessionAttribute("currentUser", null);

        ctx.redirect(NamedRoutes.rootPath());
    }
    
    // END
}
