@import exercise.dto.users.UserPage
@param UserPage page


@template.layout.page(
content = @`
    @if(page == null)
        <p>User not found</p>
    @else
        <h1>${page.getUser().getFirstName()} ${page.getUser().getLastName()}</h1>
        <p>${page.getUser().getEmail()}</p>
    @endif
`
)