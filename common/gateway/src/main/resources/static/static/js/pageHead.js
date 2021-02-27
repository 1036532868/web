/**
 * showModal 如果用户未登录, 是否弹出模态框
 * */
function staticPageInitUser(showModal) {
    $.ajax({
        url: "/user/get",
        type: "get",
        success: function(res){
            if (!res.flag){
                if (showModal) showLoginModal();
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

// 根据现有条件进行搜索
function search() {
    var url = "/search?";
    url += "pageNum=1";
    if (v.userIn !== null) url += "&userIn=" + v.userIn;

    window.location = url;
}
// 点击搜索以 userIn 和分页数据 查询
function searchButton(){
    var val = $.trim($("#searchInput").val());
    if (val === "") return;

    v.userIn = val;

    search();
}
// 搜索输入域点击回车
function searchKeyDown(event){
    if (event.keyCode === 13){
        $("#searchButton").click();
    }
}
