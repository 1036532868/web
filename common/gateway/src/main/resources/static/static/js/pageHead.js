function staticPageInitUser() {
    $.ajax({
        url: "/user/get",
        type: "get",
        success: function(res){
            if (!res.flag){
                showLoginModal();
                $("#loginError").text(res.message);
            }

            v.user = res.data;
            initUser();
        }
    })
}
function initUser() {
    if (v.user === null) return;
    $("#user").html(v.user.username + "&nbsp;<a href='javascript:void(0)' onclick='logout()' style='color: red'>登出</a>")
}
function showLoginModal(){
    $("#loginModal").modal("show");
}
function hideLoginModal(){
    $("#loginModal").modal("hide");
    $("#username").val("");
    $("#password").val("");
}
function login(){
    var username = $.trim( $("#username").val());
    var password = $.trim($("#password").val());

    if (username === "" || password === "") return;

    password = $.md5(password);

    $.ajax({
        url: "/user/login",
        data: {
            "username": username,
            "password": password
        },
        type: "post",
        success: function (res){
            if (!res.flag){
                $("#loginError").text(res.message);
                return;
            }

            v.user = res.data;
            initUser();
            $("#loginModal").modal("hide");
        }
    })
}
function logout() {
    $.ajax({
        url: "/user/logout",
        type: "get",
        success: function (res) {
            if (!res.flag){
                alert(res.message);
                return;
            }

            v.user = null;
            $("#user").html("<a id='loginHref' href='javascript:void(0)' onclick='showLoginModal()'>你好，请登录</a>")
        }
    })
}
