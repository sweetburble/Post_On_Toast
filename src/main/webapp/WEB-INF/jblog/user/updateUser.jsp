<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<br>
<div class="container mt-3">
    <form>
        <input type="hidden" id="id" value="${principal.user.id}">
        <div class="mb-3">
            <label for="username"><spring:message code="user.login.form.username" /> :</label>
            <input type="text" class="form-control" id="username" placeholder="Enter username" value="${principal.user.username}">
        </div>
        <c:if test="${principal.user.oauth == 'JBLOG'}">
        <div class="mb-3">
            <label for="password"><spring:message code="user.login.form.password" /> :</label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" >
        </div>
        </c:if>
        <div class="mb-3 mt-3">
            <label for="email"><spring:message code="user.login.form.email" /> :</label>
            <input type="email" class="form-control" id="email" placeholder="Enter email" value="${principal.user.email}">
        </div>
    </form>

    <button id="btn-update" class="btn btn-secondary">
        <spring:message code="user.index.form.update_profile" />
    </button>
</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp" %>