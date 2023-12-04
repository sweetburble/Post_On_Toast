<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<br>
<div class="container mt-3">
    <form>
        <div class="mb-3">
            <label for="title"><spring:message code="user.insert.form.title" /> :</label>
            <input type="text" class="form-control" id="title" placeholder="Enter title">
        </div>
        <div class="mb-3">
            <label for="content"><spring:message code="user.insert.form.content" /> :</label>
            <textarea class="form-control" rows="5" id="content"></textarea>
        </div>
    </form>

    <button id="btn-insert" class="btn btn-secondary">
        <spring:message code="user.index.form.posting" />
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