@import exercise.model.Post
@import exercise.util.NamedRoutes
@import exercise.dto.posts.PostsPage
@param PostsPage page


@template.layout.page(
content = @`
    <div class="mx-auto p-4 py-md-5">
        <main>
            @for (Post post: page.getPosts())
            <p><a href="${NamedRoutes.postPath(post.getId())}">${post.getName()}</a></p>
            @endfor
            @if (page.getСurrentPage() >= 1) <!-- Кнопка "Назад" -->
                <a href="${NamedRoutes.postsPath()}?page=${page.getСurrentPage() - 1}">Назад</a>
            @endif
<%--        <!-- Кнопка "Вперед" -->--%>
            <a href="${NamedRoutes.postsPath()}?page=${page.getСurrentPage() + 1}">Вперед</a>
        </main>
    </div>
`
)
