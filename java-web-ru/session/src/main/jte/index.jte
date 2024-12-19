@import exercise.dto.MainPage
@import exercise.util.NamedRoutes
@param MainPage page

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Hello Hexlet!</title>
</head>
<body>
<main>
    <h1>Привет, Хекслет!</h1>
    @if(page.getCurrentUser() == null)
        <a href="${NamedRoutes.buildSessionPath()}">Войти</a>
    @else
        <p>${String.valueOf(page.getCurrentUser())}</p>
        <a href="${NamedRoutes.logoutPath()}">Выйти</a>
    @endif
</main>
</body>
</html>