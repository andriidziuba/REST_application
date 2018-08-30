<%--
  Created by IntelliJ IDEA.
  User: Андрiй
  Date: 21.08.2018
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags/form" %>
<html>
<spring:url value="/resources/delbut.png" var="delbutimg"/>
<spring:url value="/resources/editbut.png" var="editbutimg"/>
<%@include file="BootstrapDependecies.jsp"%>
<%@include file="Navbar.jsp"%>
<head>
    <title>Books</title>

    <script>
        $('#authorsPage').removeClass('active');
        $('#booksPage').addClass('active');
    </script>
</head>
    <body>
    <div class="table-responsive">
        <div class="m-2">
            <c:choose>
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
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty books.authorSet}">
                                                <a class="btn btn-primary" data-toogle="collapse" data-target="<c:out value="nestedAuthorTable${books.id}"/>" role="button" aria-expanded="false" aria-controls="<c:out value="nestedAuthorTable${books.id}"/>">${books.authorCount}</a>
                                                <div id="<c:out value="nestedAuthorTable${books.id}"/>" class="collapse mt-2">
                                                    <table class="table" style="width: 100%">
                                                        <thead>
                                                        <th>Author ID</th>
                                                        <th>Author Name</th>
                                                        <th>Gender</th>
                                                        <th>Date of birth</th>
                                                        <th>Books</th>
                                                        <th></th>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach items="${books.authorSet}" var="list">
                                                            <tr>
                                                                <td>${list.id}</td>
                                                                <td>${list.authorName}</td>
                                                                <td>${list.gender}</td>
                                                                <td>${list.born}</td>
                                                                <td>
                                                                    <div class="m-1">
                                                                        <a href="${pageContext.request.contextPath}/rel/del?authorId=${list.id}&bookId=${books.id}&from=books"><img src="${delbutimg}" width="16" height="16" title="Delete"></a>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>
                                                    <div class="mt-2" style="font-size: large" align="right">
                                                        <button data-authorid="${books.id}" type="button" class="btn" id="<c:out value="addRel${books.id}"/>" data-toggle="modal" data-target="#joinerModal">
                                                            <span aria-hidden="true">+</span>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                No authors
                                                <div class="mt-2" style="font-size: large" align="right">
                                                    <button data-authorid="${books.id}" type="button" class="btn" id="<c:out value="addRel${books.id}"/>" data-toggle="modal" data-target="#joinerModal">
                                                        <span aria-hidden="true">+</span>
                                                    </button>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="m-1 mb-3">
                                            <a href="#" data-bookid="${books.id}" data-name="${books.bookName}" data-published="${books.published}" data-genre="${books.genre}" data-rating="${books.rating}" data-toggle="modal" data-target="#addEditBookModal"><img src="${editbutimg}" width="24" height="24" title="Edit"></a>
                                        </div>
                                        <div class="m-1">
                                            <a href="${pageContext.request.contextPath}/books/del?id=${books.id}"><img src="${delbutimg}" width="24" height="24" title="Delete"></a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <h3>No authors</h3>
                </c:otherwise>
            </c:choose>
            <%--TODO: Pagination--%>
            <!-- Button trigger modal -->
            <div class="m-2" align="right">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addEditBookModal">
                    Add new
                </button>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="addEditBookModal" tabindex="-1" role="dialog" aria-labelledby="addEditBookModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addEditBookModalLabel">Add new book</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <spr:form method="POST" modelAttribute="book">
                    <div class="modal-body">
                        <div style="display: none">
                            <input type="text" class="m-0" id="bookIdlbl" name="id" value="-1">
                        </div>
                        <div class="form-group mx-2 mb-2">
                            <label for="inputBookName">Book name:</label>
                            <input class="form-control" type="text" name="bookName" id="inputBookName">
                        </div>
                        <div class="form-group mx-2 mb-2">
                            <label for="inputGenre">Genre:</label>
                            <input class="form-control" type="text" name="genre" id="inputGenre">
                        </div>
                        <div class="form-group m-2 mb-2">
                            <label for="inputPublished">Published:</label>
                            <input class="form-control" name="published" type="date" id="inputPublished">
                        </div>
                        <div class="form-group mx-2">
                            <label for="inputRating">Rating:</label>
                            <input class="form-control" type="text" name="rating" id="inputRating">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </spr:form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="joinerModal" tabindex="-1" role="dialog" aria-labelledby="joinerModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="joinerModalLabel">
                        <c:choose>
                            <c:when test="${not empty authorList}">
                                Assign book to this author
                            </c:when>
                            <c:otherwise>
                                Assign author to this book
                            </c:otherwise>
                        </c:choose>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <spr:form method="POST" action="?addRel" modelAttribute="authorBookRelation">
                    <div class="modal-body">
                        <div class="mb-3">
                            <c:choose>
                                <c:when test="${not empty authorList}">
                                    <a href="${pageContext.request.contextPath}/books">Books</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/authors">Authors</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group">
                            <div>
                                <c:choose>
                                    <c:when test="${not empty authorList}">
                                        <input type="text" class="m-0" id="firstIdInput" name="authorId" value="-1">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="m-0" id="firstIdInput" name="bookId" value="-1">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <label for="secondIdInput">
                                <c:choose>
                                    <c:when test="${not empty authorList}">
                                        Book ID:
                                    </c:when>
                                    <c:otherwise>
                                        Author ID:
                                    </c:otherwise>
                                </c:choose>
                            </label>
                            <c:choose>
                                <c:when test="${not empty bookList}">
                                    <input type="text" class="form-control" id="secondIdInput" placeholder="Enter id" name="authorId">
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="form-control" id="secondIdInput" placeholder="Enter id" name="bookId">
                                </c:otherwise>
                            </c:choose>
                            <small class="form-text text-muted">You can see authors/books id's on appropriate pages.</small>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger mt-2" role="alert">
                                        ${error}
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </spr:form>
            </div>
        </div>
    </div>

    <script>
        $('#addEditBookModal').on('show.bs.modal', function(event) {
            if(event.relatedTarget.dataset.bookid === undefined) {
                $("#addEditBookModalLabel").text("Add new book");
                $("input[name='id']").val(-1);
                $("input[name='bookName']").val("");
                $("input[name='genre']").val("");
                $("input[name='published']").val("");
                $("input[name='raating']").val("");
                return;
            }
            $("#addEditBookModalLabel").text("Edit book");
            $("input[name='id']").val(event.relatedTarget.dataset.bookid);
            $("input[name='bookName']").val(event.relatedTarget.dataset.name);
            $("input[name='genre']").val(event.relatedTarget.dataset.genre);
            $("input[name='published']").val(event.relatedTarget.dataset.published);
            $("input[name='rating']").val(event.relatedTarget.dataset.rating);
        });

        $("a[data-toogle='collapse']").on('click', function (event) {
            $('#' + this.getAttribute('data-target')).collapse('toggle');
        });

        $(document).ready(function () {
            var typingError = '${error}';
            if(typingError !== '') {
                $('#joinerModal').modal('show');
                $("input[id='firstIdInput']").val('${firstId}');
            }
        });

        $("button[data-target='#joinerModal']").on('click', function (event) {
            $("input[id='firstIdInput']").val(event.target.getAttribute('data-authorid'));
        });
    </script>

    </body>
</html>
