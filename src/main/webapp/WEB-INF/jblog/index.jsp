<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="./layout/header.jsp" %>

<br>
<div class="container mt-3">
<c:if test="${!empty postList}">
    <div class="card">
    <c:forEach var="post" items="${postList.content}">
        <div class="card-body">
            <h4 class="card-title">${post.title}</h4>
            <a href="/post/${post.id}" class="btn btn-secondary">
                <spring:message code="user.index.form.detail" />
            </a>
        </div>
    </c:forEach>
    </div>

    <br>
    <ul class="pagination justify-content-between">
        <li class="page-item <c:if test="${postList.first}">disabled</c:if>">
            <a class="page-link" href="?page=${postList.number - 1}">
                <spring:message code="user.index.form.previous" />
            </a>
        </li>
        <li class="page-item <c:if test="${postList.last}">disabled</c:if>">
            <a class="page-link" href="?page=${postList.number + 1}">
                <spring:message code="user.index.form.next" />
            </a>
        </li>
    </ul>
</c:if>
</div>

<%@ include file="./layout/footer.jsp" %>