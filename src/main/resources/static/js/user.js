// userObject 객체 선언
let userObject = {
    // init() 함수 선언
    init: function () {
        let _this = this;

        // insertUser.jsp 화면에서 #btn-save 버튼에 click 이벤트가 발생하면, insertUser() 함수 호출
        $("#btn-save").on("click", () => {
            _this.insertUser();
        });
        $("#btn-update").on("click", () => {
            _this.updateUser();
        });
    },

    insertUser: function () {
        // 사용자가 입력한 값을 (username, password, email) 추출
        let user = {
            username : $('#username').val(),
            password : $('#password').val(),
            email : $('#email').val()
        }

        // Ajax를 이용한 비동기 호출
        // done() 함수 : 요청 처리에 성공했을 때. 실행될 코드
        // fail() 함수 : 요청 처리에 실패했을 때, 실행될 코드
        $.ajax({
            type: "POST", // 요청 방식
            url: "/auth/insertUser", // 요청 경로
            data: JSON.stringify(user), // user 객체를 JSON 형식으로 변환
            // HTTP의 body에 설정되는 데이터 마임 타입
            contentType: "application/json; charset=utf-8"
            // 응답으로 들어온 JSON 데이터를 response로 받는다.
        }).done(function (response) {
            let status = response["status"];
            if (status == 200) { // 정상 등록
                let message = response["data"];
                alert(message);
                location = "/";
            } else { // 등록 중에 문제 발생!
                let warn = "";
                let errors = response["data"];
                if (errors.username != null) warn = warn + errors.username + "\n";
                if (errors.password != null) warn = warn + errors.password + "\n";
                if (errors.email != null) warn = warn + errors.email;
                alert(warn);
            }
        }).fail(function (error) {
            // 에러 메시지를 알림창에 출력
            alert("에러 발생! : " + error);
        });
    },

    updateUser: function () {
        alert("회원 정보 수정 요청");

        let user = {
            id : $("#id").val(),
            username : $('#username').val(),
            password : $('#password').val(),
            email : $('#email').val()
        }

        $.ajax({
            type: "PUT", // 요청 방식
            url: "/user", // 요청 경로
            data: JSON.stringify(user), // user 객체를 JSON 형식으로 변환
            contentType: "application/json; charset=utf-8"
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/";
        }).fail(function (error) {
            let message = error["data"];
            alert("에러 발생! : " + message);
        });
    },
}

// user.js 파일이 로딩되는 순간, userObject 객체의 init() 함수를 호출
userObject.init();