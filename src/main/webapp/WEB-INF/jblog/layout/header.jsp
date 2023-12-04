<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!-- 로그인 인증에 성공한 브라우저(유저)만 접근할 수 있는 영역 -->
<sec:authorize access="isAuthenticated()">
    <!-- principal은 로그인에 성공한 사용자(User) 객체에 접근할 수 있는 변수이다 -->
    <sec:authentication var="principal" property="principal" />
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    <script src="/webjars/jquery/3.6.0/dist/jquery.min.js"></script>

    <!-- Summernote 시작 -->
    <link href="/webjars/summernote/0.8.10/summernote-bs4.css" rel="stylesheet">
    <script src="/webjars/summernote/0.8.10/summernote-bs4.min.js"></script>
    <!-- Summernote 종료 -->
</head>
<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Post On Toast</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mynavbar">
        <c:if test="${principal == null}">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="/auth/login">
                    <spring:message code="user.login.form.login_btn" />
                </a></li>
                <li class="nav-item"><a class="nav-link" href="/auth/insertUser">
                    <spring:message code="user.index.form.sign_up" />
                </a></li>
            </ul>
        </c:if>
        <c:if test="${principal != null}">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="/user/updateUser">
                    <spring:message code="user.index.form.profile" />
                </a></li>
                <li class="nav-item"><a class="nav-link" href="/post/insertPost">
                    <spring:message code="user.index.form.posting" />
                </a></li>
                <li class="nav-item"><a class="nav-link" href="/auth/logout">
                    <spring:message code="user.index.form.sign_out" />
                </a></li>
            </ul>
        </c:if>
        </div>
    </div>
</nav>