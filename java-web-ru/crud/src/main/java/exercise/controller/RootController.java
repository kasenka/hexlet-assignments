package exercise.controller;

import exercise.dto.posts.PostsPage;
import exercise.repository.PostRepository;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class RootController {
    public static void index(Context ctx) {
        ctx.render("index.jte");
    }

    public static void showPosts(Context ctx) {
        var сurrentPage = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);

        var entities = PostRepository.findAll(сurrentPage, 5);

        var page = new PostsPage(entities,сurrentPage);
        ctx.render("posts/index.jte", model("page", page));
    }
}
