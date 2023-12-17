// replyObject 객체 선언
let replyObject = {
    init: function () {
        // getPost.jsp 화면에서 #btn-save-reply 버튼에 click 이벤트가 발생하면, insertReply() 함수 호출
        $("#btn-save-reply").on("click", () => {
            this.insertReply();
        });
    },

    insertReply: function () {
        alert("댓글 등록 요청됨");

        let id = $("#postId").val();

        let reply = {
            content: $("#reply-content").val()
        }

        // Ajax를 이용한 비동기 호출
        // done() 함수 : 요청 처리에 성공했을 때. 실행될 코드
        $.ajax({
            type: "POST", // 요청 방식
            url: "/reply/" + id, // 요청 경로
            data: JSON.stringify(reply), // user 객체를 JSON 형식으로 변환
            // HTTP의 body에 설정되는 데이터 마임 타입
            contentType: "application/json; charset=utf-8"
            // 응답으로 들어온 JSON 데이터를 response로 받는다.
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/post/" + id;
            // 에러 발생 시, error로 에러 정보를 받는다.
        });
    },

    deleteReply: function (postId, replyId) {
        alert("댓글 삭제 요청됨");

        // Ajax를 이용한 비동기 호출
        // done() 함수 : 요청 처리에 성공했을 때. 실행될 코드
        $.ajax({
            type: "DELETE", // 요청 방식
            url: "/reply/" + replyId // 요청 경로
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/post/" + postId;
            // 에러 발생 시, error로 에러 정보를 받는다.
        });
    },
}

replyObject.init();