package exercise;

// BEGIN

// END

import io.javalin.Javalin;

public final class App {

    public static Javalin getApp() {

        // BEGIN
        var app = Javalin.create(config -> {
                    config.plugins.enableDevLogging();
                })
                .get("/welcome", ctx -> ctx.result("Welcome to Hexlet!"))
                .start(7070);
        return app;

        // END
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
