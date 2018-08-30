<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="BootstrapDependecies.jsp"%>

<nav class="navbar navbar-expand navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item" id="authorsPage">
                <a class="nav-link" href="/authors">Authors <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item" id="booksPage">
                <a class="nav-link" href="/books">Books</a>
            </li>
            <li class="btn-group">
                <a class="nav-link dropdown-toggle" href="#" onclick="cl()" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Tasks
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown" id="drop">
                    <div>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/task/view/older">showAuthorsOlderParamAndSortByBorn</a>
                    </div>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/task/view/books">booksWhoseAuthorHasMoreThan1WrittenBooks</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/task/view/mostbooks">authorWhichHasMostBooks</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/task/view/genres">byGenres</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<script>
    function cl() {
        $('#drop').dropdown('toggle');
    }
    // $('#navbarDropdown').on('click', function (event) {
    //     $('#drop').dropdown('toggle');
    // })

    $("a[class='dropdown-item']").on('click', function (event) {
        window.location.replace(this.getAttribute('href'));
        console.log(this);
    });
</script>
