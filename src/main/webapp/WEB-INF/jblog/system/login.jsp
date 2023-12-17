<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<br>
<div class="container mt-3">
    <form action="/auth/securitylogin" method="post">
        <div class="mb-3">
            <label for="username">
                <spring:message code="user.login.form.username" /> :
            </label>
            <input type="text" class="form-control" id="username" placeholder="Enter username" name="username" value="test">
        </div>
        <div class="mb-3">
            <label for="password">
                <spring:message code="user.login.form.password" /> :
            </label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" name="password" value="test123">
        </div>

        <button id="btn-login" class="btn btn-secondary">
            <spring:message code="user.login.form.login_btn" />
        </button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id={ 비밀정보입니다 }&redirect_uri=http://PostOnToast.ap-northeast-2.elasticbeanstalk.com/oauth/kakao&response_type=code"><img height="38px" src="/image/kakao_login_btn.png"></a>
        <a href="../oauth2/authorization/google"><img height="38px" src="/image/google_login_btn.png"></a>
    </form>
</div>

<%@ include file="../layout/footer.jsp" %>