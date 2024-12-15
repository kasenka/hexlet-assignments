@import exercise.dto.users.UsersPage
@param UsersPage page


@template.layout.page(
content = @`
    <form action="/users" method="get">
        <input type="search" name="term" value="${page.getTerm()}" />
        <input type="submit" value="Search" />
    </form>
    @if(page == null)
        <p>Users not found</p>
    @else
        @for (var user: page.getUsers())
            <h1>${user.getFirstName()} ${user.getLastName()}</h1>
        @endfor
    @endif
`
)