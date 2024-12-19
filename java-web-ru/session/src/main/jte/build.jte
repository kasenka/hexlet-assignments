@import exercise.dto.LoginPage
@import exercise.util.NamedRoutes
@param LoginPage page

<meta charset="UTF-8">
@if (page.getError() != null){
    <p>${page.getError()}</p>
}
@endif
<form action="${NamedRoutes.loginPath()}" method="post">
    <input type="text" placeholder="name" name="name" />
    <input type="password" placeholder="password" name="password" />
    <input type="submit" value="Зарагестрироваться"/>
</form>