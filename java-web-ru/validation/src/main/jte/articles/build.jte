@import exercise.dto.articles.BuildArticlePage
@param BuildArticlePage page

@if(page.getErrors() != null)
    <ul>
        @for(var validator : page.getErrors().values())
            @for(var error : validator)
                <li>${error.getMessage()}</li>
            @endfor
        @endfor
    </ul>
@endif
<meta charset="UTF-8">
<form action="/articles" method="post">
    <div>
        <label>
            Название
            <input type="text" name="title" value="${page.getTitle()}" />
        </label>
    </div>
    <div>
        <label>
            Содержимое статьи
            <textarea name="content" rows="5" cols="33">${page.getContent()}</textarea>
        </label>
    </div>
    <input type="submit" value="Зарегистрировать" />
</form>