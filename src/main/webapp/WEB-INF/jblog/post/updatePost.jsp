<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<br>
<div class="container mt-3">
    <form>
        <!-- 포스트 수정 기능에 사용할 post의 id는 누구도 보지 못하게 hidden으로 전달한다 -->
        <input type="hidden" id="id" value="${post.id}">
        <div class="mb-3">
            <label for="title"><spring:message code="user.insert.form.title" /> :</label>
            <input type="text" class="form-control" id="title" value="${post.title}">
        </div>
        <div class="mb-3">
            <label for="content"><spring:message code="user.insert.form.content" /> :</label>
            <textarea class="form-control" rows="5" id="content">${post.content}</textarea>
        </div>
    </form>

    <button class="btn btn-secondary" onclick="history.back()">
        <spring:message code="user.post.detail.return" />
    </button>
    <button id="btn-update" class="btn btn-warning">
        <spring:message code="user.post.detail.update" />
    </button>
</div>

<script>
    $(document).ready(function() {
        $('#content').summernote({
            height: 300
        });
    });
</script>

<script src="/js/post.js"></script>

<%@ include file="../layout/footer.jsp" %>