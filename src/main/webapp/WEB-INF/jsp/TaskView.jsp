<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags/form" %>
<html>
<%@include file="BootstrapDependecies.jsp"%>
<%@include file="Navbar.jsp"%>
<head>
    <title>Task</title>
    <h3>${message}</h3>
</head>
<body>
    <div class="table-responsive">
        <div class="m-2">
            <c:choose>
                <c:when test="${not empty authorList}">
                    <div>
                        <table class="table table-bordered table-hover">
                            <thead class="thead-light">
                            <th>Author ID</th>
                            <th>Author Name</th>
                            <th>Gender</th>
                            <th>Date of birth</th>
                            <th>Books</th>
                            <th></th>
                            </thead>
                            <tbody class="table-striped">
                            <c:forEach items="${authorList}" var="list">
                                <tr>
                                    <td>${list.id}</td>
                                    <td>${list.authorName}</td>
                                    <td>${list.gender}</td>
                                    <td>${list.born}</td>
                                    <td>${list.bookCount}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:when test="${not empty bookList}">
                    <div>
                        <table class="table table-bordered table-hover">
                            <thead class="thead-light">
                            <th>Book ID</th>
                            <th>Book Name</th>
                            <th>Publish Date</th>
                            <th>Genre</th>
                            <th>Book rating /10</th>
                            <th>Authors</th>
                            <th></th>
                            </thead>
                            <tbody class="table-striped">
                            <c:forEach items="${bookList}" var="books">
                                <tr>
                                    <td>${books.id}</td>
                                    <td>${books.bookName}</td>
                                    <td>${books.published}</td>
                                    <td>${books.genre}</td>
                                    <td>${books.rating}</td>
                                    <td>${books.authorCount}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <table class="table table-bordered table-hover">
                            <thead class="thead-light">
                            <th>Genre</th>
                            <th>Count</th>
                            </thead>
                            <tbody class="table-striped">
                            <c:forEach items="${genres}" var="genre">
                                <tr>
                                    <td>${genre.key}</td>
                                    <td>${genre.value}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:if test="${not empty sortable}">
        <div>
            <%--<form method="get">--%>
                <label>Age:</label>
                <input type="text" id="ageInput" placeholder="Natural number" value="55">
                <a class="btn btn-primary" onclick="getFunction({num : 1})">Ascending</a>
                <a class="btn btn-primary" onclick="getFunction({num : 2})">Descending</a>
            <%--</form>--%>
        </div>
    </c:if>
</body>
</html>

<script>
    function getFunction(parameters) {
        var num = parameters.num;
        var age = $('#ageInput').val();
        if(!isNaturalNumber(age)) return;
        if(num === 1) {
            window.location.replace('/task/view/older?age=' + age);
        } else {
            window.location.replace('/task/view/older?age=' + age + '&desc=1');
        }

    }

    function isNaturalNumber(n) {
        n = n.toString(); // force the value incase it is not
        var n1 = Math.abs(n),
            n2 = parseInt(n, 10);
        return !isNaN(n1) && n2 === n1 && n1.toString() === n;
    }
</script>