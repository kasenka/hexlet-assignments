@import exercise.model.Post
@import exercise.util.NamedRoutes
@import exercise.dto.posts.PostPage
@param PostPage page


@template.layout.page(
content = @`
    <div class="mx-auto p-4 py-md-5">
        <main>
            <p>${page.getPost().getName()}</p>
            <p>${page.getPost().getBody()}</p>

            <a href="${NamedRoutes.postsPath()}">Назад ко всем постам</a>
        </main>
    </div>
`
)
