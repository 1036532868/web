/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:25
 * @since 1.0.0
 */

// 分页插件, * 为需要传入的值
function pagination(o){
    // *目标元素
    var target = $(o.target);
    // *当前页
    var currentPage = o.currentPage;
    // *总记录条数
    var total = o.total;
    // *每页显示的条数
    var pageSize = o.pageSize;
    // *每页最多多少条记录
    var maxPageSize = o.maxPageSize;
    // *页码按钮数量
    var buttonCount = o.buttonCount;
    // *回调函数
    callback = function(currentPageNum){
        // 以 input[id=currentPageSize] 的 value 为准
        var currentPageSize = parseInt($("#currentPageSize").val());

        // currentPageSize小于1时, 更改为默认
        if (currentPageSize < 1) currentPageSize = 10;
        // currentPageSize大于最大值时, 更改为最大值
        if (currentPageSize > maxPageSize) currentPageSize = maxPageSize;


        // pageNum小于0时, 更改为1
        if (currentPageNum < 1) currentPageNum = 1;
        // pageNum大于当前总页数时, 更改为最大值
        var currentPageCount = Math.ceil(total/currentPageSize);
        if (currentPageNum > currentPageCount) currentPageNum = currentPageCount;

        o.callback(currentPageNum, currentPageSize);
    }

    // 总页数
    var pageCount = Math.ceil(total/pageSize);

    /* buttonCount 的中间值, 当 buttonCount 为奇数时, 中间值向上取整作为中间值
    *       作用:
    *           1. 用来判断当前页是否超过阈值
    *               低于阈值时, 页码显示样例 -> 1 2 3 4 5 ...
    *               高于阈值时, 页码显示样例 -> 5 6 7 8 9 ...
    *           2. 高于阈值时, 第 "中间值" 个页码按钮为 当前页 的页码按钮
    * */
    var middleButtonCount;
    if (buttonCount % 2 === 0){
        middleButtonCount = buttonCount/2;
    } else {
        middleButtonCount = Math.ceil(buttonCount/2);
    }

    // 页码按钮的页数
    var firstPage = currentPage - middleButtonCount + 1;
    var lastPage = currentPage - 1;
    var nextPage = currentPage + 1;
    var finalPage = firstPage - 1 + buttonCount;

    // 清空目标元素
    target.empty();

    // 开头部分(固定), 不是第一页时显示
    target.append(
        "<span>" +
        "    共<span id='total'>"+ total +"</span>条记录" +
        "    &nbsp;&nbsp;" +
        "    共<span id='pageCount'>"+ pageCount +"</span>页" +
        "</span>" +
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")


    // 按钮部分(变化)
    // 页码开头
    if (currentPage !== 1){
        target.append(
            "<input onclick='callback(1)' type='button' value='首页'/>" +
            "&nbsp;" +
            "<input onclick='callback("+ lastPage +")' type='button' value='上一页'/>" +
            "&nbsp;" )
    }

    // 页码按钮
    // 当前页小于等于中间值时, 正常展示页码按钮, 在小于总页数的前提下
    if (currentPage <= middleButtonCount){

        for (var i = 1; i <= buttonCount && i <= pageCount; i++){
            //为当前页的页码按钮更改样式
            if (i === currentPage){
                target.append(
                    "<input id='currentPageNum' class='currentPageNum' type='button' value='"+ currentPage +"'/>" +
                    "&nbsp;");
            } else {
                target.append(
                    "<input onclick='callback("+ i +")' type='button' value='"+ i +"'/>" +
                    "&nbsp;");
            }

        }

    } else {
        // 当前页大于中间值时, 以当前页 和 buttonCount 为参考, 生成页码按钮, 中间值对应的按钮为当前页按钮
        target.append(
            "<input id='currentPageNum' class='currentPageNum' type='button' value='"+ currentPage +"'/>" +
            "&nbsp;");
        var currentPageButton = $(".currentPage");

        // 以当前页码为参照, 向前添加 n 个页码按钮
        for (var i = firstPage; i < currentPage; i++){
            currentPageButton.before(
                "<input onclick='callback("+ i +")' type='button' value='"+ i +"'/>" +
                "&nbsp;");
        }

        // 以当前页码为参照, 向后添加 n 个页码按钮, 在小于总页数的前提下
        for (var i = nextPage; i <= finalPage && i <= pageCount; i++){
            target.append(
                "<input onclick='callback("+ i +")' type='button' value='"+ i +"'/>" +
                "&nbsp;");
        }

    }

    // 页码结尾, 是最后一页时不生成
    if (currentPage !== pageCount){
        target.append(
            "<input onclick='callback("+ nextPage +")' type='button' value='下一页'/>" +
            "&nbsp;" +
            "<input onclick='callback("+ pageCount +")' type='button' value='末页'/>")
    }



    // 结尾部分(固定), 最后一页时不显示
    if (currentPage)
        target.append(
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "显示<input id='currentPageSize' class='enter' type='text' value='"+ pageSize +"'/>条" +
            "&nbsp;&nbsp;" +
            "跳转到<input id='jump' class='enter' type='text' maxlength='20'/>页" +
            "<input onclick='jump()' type='button' value='确定'/>")
}

// 跳转
function jump() {
    var currentPageNum = $("#jump").val();
    if (currentPageNum === ""){
        return;
    }

    callback(parseInt(currentPageNum));
}