<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<br><br>
<div class="container border">
    <br>
    <div>
        <h3>${post.title}</h3>
    </div>
    <br>
    <div>
        <h3>${post.content}</h3>
    </div>

    <br>
    <div>
        <spring:message code="user.post.detail.num" /> : <span id="id"><i>${post.id}</i></span><br>
        <spring:message code="user.post.detail.post_user" /> : <span><i>${post.user.username}</i></span>
    </div>

    <hr>
    <button class="btn btn-secondary" onclick="history.back()">
        <spring:message code="user.post.detail.return" />
    </button>

    <c:if test="${post.user.username == principal.username}">
        <a href="/post/updatePost/${post.id}" class="btn btn-warning">
            <spring:message code="user.post.detail.update" />
        </a>
        <button id="btn-delete" class="btn btn-danger">
            <spring:message code="user.post.detail.delete" />
        </button>
    </c:if>
    <br><br>

    <c:if test="${!empty post.replyList}">
        <div class="container mt-3">
            <table class="table">
                <thead>
                    <tr>
                        <th width="80%"><spring:message code="user.insert.form.content" /></th>
                        <th width="10%"><spring:message code="user.post.detail.post_user" /></th>
                        <th width="10%"><spring:message code="user.post.detail.comment_delete" /></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="reply" items="${post.replyList}">
                    <tr>
                        <td>${reply.content}</td>
                        <td>${reply.user.username}</td>
                        <c:if test="${reply.user.username != null && reply.user.username == principal.username}">
                            <td><button onclick="replyObject.deleteReply(${post.id},${reply.id})">
                                <spring:message code="user.post.detail.comment_delete" />
                            </button></td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <div class="container mt-3">
        <input type="hidden" id="postId" value="${post.id}">
        <table class="table">
            <thead>
                <tr>
                    <th><h4><spring:message code="user.post.detail.comment_list" /></h4></th>
                </tr>
            </thead>
            <tbody>
                <tr align="right">
                    <td>
                        <textarea id="reply-content" rows="1" class="form-control"></textarea>
                        <br>
                        <button id="btn-save-reply" class="btn btn-secondary">
                            <spring:message code="user.post.detail.comment" />
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script src="/js/post.js"></script>
<script src="/js/reply.js"></script>

<%@ include file="../layout/footer.jsp" %>