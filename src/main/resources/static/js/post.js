// postObject 객체 선언
let postObject = {
    // init() 함수 선언
    init: function () {
        let _this = this;

        // insertPost.jsp 화면에서 #btn-save 버튼에 click 이벤트가 발생하면, insertPost() 함수 호출
        $("#btn-insert").on("click", () => {
            _this.insertPost();
        });
        $("#btn-update").on("click", () => {
            _this.updatePost();
        });
        $("#btn-delete").on("click", () => {
            _this.deletePost();
        });
    },

    insertPost: function () {
        alert("포스트 등록 요청됨");
        let post = {
            title : $('#title').val(),
            content : $('#content').val(),
        }

        // Ajax를 이용한 비동기 호출
        // done() 함수 : 요청 처리에 성공했을 때. 실행될 코드
        // fail() 함수 : 요청 처리에 실패했을 때, 실행될 코드
        $.ajax({
            type: "POST", // 요청 방식
            url: "/post", // 요청 경로
            data: JSON.stringify(post), // user 객체를 JSON 형식으로 변환
            // HTTP의 body에 설정되는 데이터 마임 타입
            contentType: "application/json; charset=utf-8"
            // 응답으로 들어온 JSON 데이터를 response로 받는다.
        }).done(function (response) {
            let status = response["status"];
            if (status == 200) { // 정상 등록
                let message = response["data"];
                alert(message);
                location = "/";
            } else { // 부적절한 포스트 등록
                let warn = "";
                let errors = response["data"];
                if (errors.title != null) warn = warn + errors.title + "\n";
                if (errors.content != null) warn = warn + errors.content;
                alert(warn);
            }
        }).fail(function (error) {
            // 에러 메시지를 알림창에 출력
            let message = error["data"];
            alert("문제 발생! : " + message);
        });
    },

    updatePost : function () {
        alert("포스트 수정 요청됨");
        let post = {
            id : $("#id").val(),
            title : $("#title").val(),
            content : $("#content").val()
        }

        $.ajax({
            type: "PUT", // 요청 방식
            url: "/post", // 요청 경로
            data: JSON.stringify(post), // user 객체를 JSON 형식으로 변환
            contentType: "application/json; charset=utf-8"
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/";
        }).fail(function (error) {
            let message = error["data"];
            alert("문제 발생! : " + message);
        });
    },

    deletePost : function () {
        alert("포스트 삭제 요청됨");
        let id = $("#id").text();

        $.ajax({
            type: "DELETE", // 요청 방식
            url: "/post/" + id, // 요청 경로
            contentType: "application/json; charset=utf-8"
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/";
        }).fail(function (error) {
            let message = error["data"];
            alert("문제 발생! : " + message);
        });
    },
}

// post.js 파일이 로딩되는 순간, postObject 객체의 init() 함수를 호출
postObject.init();