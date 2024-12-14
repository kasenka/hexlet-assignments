@import gg.jte.Content
@param Content content
@param Content footer = null
@param Content header = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ПРИМЕР МАКЕТА СТРАНИЦЫ</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
@if (header != null)
    <div class="header">
        ${header}
    </div>
@endif

<div class="content">
    ${content}
</div>

@if (footer != null)
    <div class="footer">
        ${footer}
    </div>
@endif

</body>
</html>

