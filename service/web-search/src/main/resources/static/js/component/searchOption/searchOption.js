/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/20 14:40
 * @since 1.0.0
 */

/* 插件调用-----------------------------------------------------------------
insertOption({
    // 基本属性
    target: "#searchOption",
    data: map,
    type: "map",
    spaceNum: 5,
    alertWidth: 1280,
    moreDivWidth: 1480,
    rowHeight: 23,
    extraOptionWidth: 0,
    enableMulti: true,
    regex: /^[-\.~/、A-Za-z0-9]+$/,
    below: ["#id", ".class", "tag"],

    // 按钮内容
    moreButtonVal: "更多↓",
    packUpButtonVal: "收起↑",
    multiButtonVal: "＋多选",

    // 样式
    titleDivClass: "insertOption_titleDiv",
    optionDivClass: "insertOption_optionDiv",
    optionClass: "",
    operationButtonDivClass: "insertOption_operationButtonDiv",
    moreButtonClass: "insertOption_operationButton insertOption_moreButton",
    multiButtonClass: "insertOption_operationButton insertOption_multiButton",
    confirmButtonClass: "insertOption_confirmButton",
    cancelButtonClass: "insertOption_cancelButton",


    // 选项卡点击回调函数
    optionCallback: function (id) {
        checkCategoryCondition(id);
    },
    // 多选框回调函数
    multiCallback:function(resStr){
        checkSpecCondition(resStr);
    }
});
*/


/**
 * 参数说明
 * <pre>
 * target: "#target"
 * data: "map"/"list" -> {name, [e1, e2, ...]} / [{name: "name", option: "{e1,e2,...}"}]
 * type: "map"/"list" map不会生成多选
 * spaceNum: 空格数量                                                                      默认 -> 4
 * alertWidth: 选项卡达到此位置时, 开始换行                                                    默认 -> 1280
 * moreDivWidth: 点击"更多"按钮后, 出现的div的宽度                                             默认 -> 1480
 * rowHeight: 每行的高度                                                                    默认 -> 23
 * extraOptionWidth: 每个选项卡的额外宽度, 此插件默认只计算字符内容的宽度                            默认 -> 0
 * enableMulti: 是否启用多选                                                                默认true, 类型为"map"时, 默认false
 * regex: 正则表达式, 匹配 将被认作没有中文的字符串, 其余没有匹配成功的都作为中文长度计算。              默认 /^[-\.~/、A-Za-z0-9]+$/
 * below: 选项卡生成后, 需要变动top样式的 选项卡"下面" 的元素 -> ["#id", ".class", "tag"]
 *
 * moreButtonVal: "更多" 按钮的值                                                           默认 -> "更多↓"
 * packUpButtonVal: "收起" 按钮的值                                                         默认 -> "收起↑"
 * multiButtonVal: "多选" 按钮的值                                                          默认 -> "＋多选"
 *
 * optionClass: 选项卡 样式                                                                 默认样式
 * moreButtonClass: "更多" 按钮样式
 * multiButtonClass: "多选" 按钮样式
 * titleDivClass: 标题的 div样式
 * optionDivClass: 选项卡的 div样式
 * operationButtonDivClass: 操作按钮的 div样式
 * confirmButtonClass: 点击"多选"后, 确认按钮的样式
 * cancelButtonClass: 点击"多选"后, 取消按钮的样式
 *
 * optionCallback: 单击选项卡, 为回调函数传入
 *                      type 为 map 时, "e1.id"
 *                      type 为 list 时, "title:e1"
 * multiCallback: 多选 回调函数, 为回调函数传入
 *                      "title:e1.id、e2.id"
 *                      "title:e1、e2、e3"
 *
 * 生成的元素的名称
 * div + title, 该类别 所在的div
 * optionDiv + title,  选项卡 所在的div
 * moreDiv + title,  点击 "更多" 后显示的div
 * </pre>
 */
function insertOption(o) {

    // onmouseover='overHref("+ skuNameId +", "+ red +")'
    // onmouseleave='leaveHref("+ skuNameId +", "+ black +")'
    var red = "\"red\"";
    var black = "\"black\"";
    var blue = "\"blue\"";

    var target = $(o.target);
    target.empty();
    var data = o.data;
    var type = o.type;

    var spaceNum = o.spaceNum;
    var alertWidth = o.alertWidth;
    var moreDivWidth = o.moreDivWidth;
    var rowHeight = o.rowHeight;
    var extraOptionWidth = o.extraOptionWidth;
    var enableMulti = o.enableMulti;

    if (typeof spaceNum !== "number" || spaceNum < 0) spaceNum = 5;
    if (typeof alertWidth !== "number" || alertWidth < 0) alertWidth = 1280;
    if (typeof moreDivWidth !== "number" || moreDivWidth < 0) moreDivWidth = 1480;
    if (typeof rowHeight !== "number" || rowHeight < 0) rowHeight = 21;
    if (typeof extraOptionWidth !== "number" || extraOptionWidth < 0) extraOptionWidth = 0;
    if (!enableMulti) type === "map" ? enableMulti = false : enableMulti = true;

    var regex = o.regex;
    var below = o.below;

    // 按钮字样
    var moreButtonVal = o.moreButtonVal;
    var packUpButtonVal = o.packUpButtonVal;
    var multiButtonVal = o.multiButtonVal;
    if (!moreButtonVal) moreButtonVal = "更多↓";
    if (!packUpButtonVal) packUpButtonVal = "收起↑";
    if (!multiButtonVal) multiButtonVal = "＋多选";

    //样式
    var titleDivClass = o.titleDivClass;
    var optionDivClass = o.optionDivClass;
    var optionClass = o.optionClass;
    var operationButtonDivClass = o.operationButtonDivClass;
    var moreButtonClass = o.moreButtonClass;
    var multiButtonClass = o.multiButtonClass;
    var confirmButtonClass = o.confirmButtonClass;
    var cancelButtonClass = o.cancelButtonClass;

    if(!titleDivClass) titleDivClass = "insertOption_titleDiv";
    if(!optionDivClass) optionDivClass = "insertOption_optionDiv";
    if(!optionClass) optionClass = "";
    if(!operationButtonDivClass) operationButtonDivClass = "insertOption_operationButtonDiv";
    if(!moreButtonClass) moreButtonClass = "insertOption_operationButton insertOption_moreButton";
    if(!multiButtonClass) multiButtonClass = "insertOption_operationButton insertOption_multiButton";
    if(!confirmButtonClass) confirmButtonClass = "insertOption_confirmButton";
    if(!cancelButtonClass) cancelButtonClass = "insertOption_cancelButton";


    var rowNum = 0;

    //用来存放上一个 多选按钮 触发时使用的参数
    target.append("<input type='hidden' id='lastMultiParam'/>");
    //存放当前是否是多选状态, 用来动态更改点击选项卡后触发的事件效果
    target.append("<input type='hidden' id='isMulti' value='0'/>");

    if (type === "map"){

        for (var title in data) {
            var list = data[title];

            insert(title, list);
            rowNum++
        }

        changeTop(below, rowHeight, rowNum);
    }

    if (type === "list"){

        for (var i = 0; i < data.length; i++){
            insert(data[i].name, data[i].options.split(","));
            rowNum++;
        }

        changeTop(below, rowHeight, rowNum);
    }

    /**
     * 生成一行div*/
    function insert(title, list) {

        var widthSum = 0;
        var hasMoreDiv = false;
        var moreDivRowNum = 0;

        var html = "";
        html += "<div id='div"+ title +"' style='position: absolute; top: "+ rowHeight * rowNum +"px;'>";

        html += "<div class='"+ titleDivClass +"' style='top: " + rowHeight * moreDivRowNum +"px;'>"+ title +"</div>";
        html += "<div id='optionDiv"+ title +"' class='"+ optionDivClass +"' style='top: "+ rowHeight * moreDivRowNum +"px;'>";


        for (var i = 0; i < list.length; i++) {
            var text = "";
            var val = "";

            type === "map" ? text = list[i].name : text = list[i];
            type === "map" ? val = list[i].id : val = list[i];

            if (enableMulti) widthSum += 13;
            // 多选
            var width = getContentWidth(text, spaceNum, regex === undefined ? undefined : regex) + extraOptionWidth;
            widthSum += width;

            if (!hasMoreDiv) {
                //长度超过警戒值时, 生成 "更多"div
                if (widthSum > alertWidth) {
                    html += "</div>";
                    html += "<div id='moreDiv"+ title +"' class='"+ optionDivClass +"' style='top: "+ rowHeight +"px; display: none;'>";

                    hasMoreDiv = true;
                    widthSum = width;
                    moreDivRowNum++;
                }
            } else {
                if (widthSum > moreDivWidth) {
                    html += "<br>";
                    widthSum = 0;
                    moreDivRowNum++;
                }
            }

            //多选 多选框
            if (enableMulti) html += "<input id='checkbox"+ title + i +"' onclick='clickCheckbox(\""+ title +"\")' style='display: none;' type='checkbox' value='"+ val +"'/>";

            var optionId = JSON.stringify("option" + title + i);
            html += "<a id="+ optionId +" href='javascript:void(0)' class='"+ optionClass +"' " +
                "onclick='optionCallback(\""+ title + i +"\",\"" + title + "\",\"" + val + "\")' " +
                "onmouseover='overHref("+ optionId +","+ red +")' " +
                "onmouseleave='leaveHref("+ optionId +","+ blue +")'>"+
                text +
                "</a>";
            for (var s = 0; s < spaceNum; s++) {
                html += "&nbsp;";
            }

        }

        // 到此依然没有 moreDiv, 并且数量大于1时, 添加一个moreDiv, 但不生成 "更多按钮"
        if (!hasMoreDiv && enableMulti && list.length > 1) {
            moreDivRowNum = 0;
            html += "</div>";
            html += "<div id='moreDiv"+ title +"' class='"+ optionDivClass +"' style='top: "+ rowHeight +"px; display: none;'>";
        }

        html += "</div>";
        html += "</div>";
        target.append(html);

        // 选项卡数量为1时, 不生成更多和多选相关内容
        if (list.length < 2) return;

        // 生成buttonDiv
        $("#optionDiv"+ title).append("<div id='buttonDiv" + title + "' class='"+ operationButtonDivClass +"'></div>");
        var buttonDiv = $("#buttonDiv" + title);
        var moreDivHeight = rowHeight * moreDivRowNum;
        var moreDiv = $("#moreDiv" + title);

        // 此隐藏域的值, 将用来表示 moreDiv/multiDiv 是否已经显示
        buttonDiv.append("<input id='showMore"+ title +"' type='hidden' value='0'/>");
        if (hasMoreDiv) {
            buttonDiv.append("<input id='more"+ title +"' class='"+ moreButtonClass +"' type='button' value='"+ moreButtonVal +"' onclick='moreCallback(\""+ title +"\", "+ moreDivHeight +")'/>");
        }

        // 多选 只有显示规格时, 才有多选按钮 以及相关
        if (enableMulti) {
            buttonDiv.append("<input id='multi" + title + "' class='" + multiButtonClass + "' type='button' value='" + multiButtonVal + "' onclick='multiCallback(\"" + title + "\", "+ moreDivHeight +")'/>");

            // 点击多选后显示的相关内容
            var multiDivTop = moreDivHeight + rowHeight;
            moreDiv.append(
                "<div id='multiAbout" + title + "' class='"+ optionDivClass +"' style='display: none; position: absolute; left: 0px; top: " + multiDivTop + "px;'>" +
                "<input id='confirm" + title + "' type='button' class='" + confirmButtonClass + "' value='确定' onclick='confirmMulti(\"" + title + "\")'>" +
                "<input id='cancel" + title + "' type='button' class='" + cancelButtonClass + "' value='取消' onclick='cancelMulti(\"" + title + "\", "+ moreDivHeight +")'>" +
                "</div>")
        }
    }


    // 回调函数 ------------------------------------------------------------
    /**
     * "更多" 按钮
     * <pre>
     * 点击 "更多"/"收起" 后, 更改隐藏域的值 "1"/"0"
     * 通过隐藏域的值, 判断是否 -> 显示额外div, 并更改所有下边的div的top, 增加(通过改为负数实现 减少)当前 moreDiv height 量
     * </pre>
     * */
    moreCallback = function (title, moreDivHeight) {
        var showMore = $("#showMore"+ title);
        var show = showMore.val();
        show === "1" ? showMore.val("0") : showMore.val("1");
        show = showMore.val() === "1";

        $("#more"+ title).val(show ? packUpButtonVal : moreButtonVal);

        var moreDiv = $("#moreDiv"+ title);

        moreDiv.css("display", show ? "inline" : "none");

        // 100 : "-100"
        var height = 0;
        show ? height = moreDivHeight : height = parseInt("-"+ moreDivHeight);

        changeOtherOptionTop(title, height, data, type);
        changeTop(below, height);
    };

    // 每次点击"多选", 检查存放上一个 多选按钮 触发时使用的参数
    multiCallback = function (title, moreDivHeight){
        var multiDivTop = moreDivHeight + 2 * rowHeight;
        var lastMulti =  $("#lastMultiParam");
        var lastMultiParam = lastMulti.val();

        if (!(lastMultiParam === "" || lastMultiParam === undefined)){
            // 调用moreCallback, 隐藏 上次的 moreDiv, 并隐藏上次多选 对应的 multiAbout
            var lastParam = lastMultiParam.split(",")
            var lastTitle = lastParam[0];
            var lastHeight = lastParam[1];

            moreCallback(lastTitle, lastHeight);
            controlMultiAbout(lastTitle, false);
        }

        // 初始化本次多选, 将条件存入lastMultiVal, 判断 1.是否有moreDiv 2.是否已经显示
        var showMore = $("#showMore"+ title);
        var moreReady = showMore.val() === "1";

        //如果已经显示 moreDiv, 隐藏
        if (moreReady) moreCallback(title, moreDivHeight);

        moreCallback(title, multiDivTop)
        controlMultiAbout(title, true);

        var val = title +","+ multiDivTop;
        lastMulti.val(val);

    }
    // "多选" 确定按钮
    confirmMulti = function (title){
        var optionDiv = $("#optionDiv"+ title +" > :checkbox:checked");
        var moreDiv = $("#moreDiv"+ title +" > :checkbox:checked");

        var result = [];
        for (var i = 0; i < optionDiv.length; i++){
            result.push(optionDiv[i].value);
        }
        for (var i = 0; i < moreDiv.length; i++){
            result.push(optionDiv[i].value);
        }

        result = title +":"+ result.join("、");

        o.multiCallback(result);

    }
    // "多选" 取消按钮
    cancelMulti = function (title, moreDivHeight){
        var multiDivTop = moreDivHeight + 2 * rowHeight;
        moreCallback(title, multiDivTop);
        controlMultiAbout(title, false);
        /*
        如果不清除, 多选按钮触发时, 原先既定的隐藏逻辑会变为显示, 导致逻辑混乱
        且取消后, 页面上没有 多选div, 清除参数也更符合逻辑
        */
        $("#lastMultiParam").val("");
    }

    /**
     * 操作 "多选" 相关的元素
     * */
    controlMultiAbout = function (title, show){

        $("#multi"+ title).css("display", show ? "none" : "inline");
        $("#more"+ title).css("display", show ? "none" : "inline")

        $("#optionDiv"+ title +" > :checkbox").css("display", show ? "inline" : "none");
        $("#moreDiv"+ title +" > :checkbox").css("display", show ? "inline" : "none");

        $("#isMulti").val(show ? "1" : "0");

        if (!show){
            $("#optionDiv"+ title +" > :checkbox:checked").prop("checked", false);
            $("#moreDiv"+ title +" > :checkbox:checked").prop("checked", false);
            $("#confirm"+ title).css("display", "none");
        }

        $("#multiAbout"+ title).css("display", show ? "inline" : "none");


    }
    // 点击多选框
    clickCheckbox = function (title){
        var l1 = $("#optionDiv"+ title +" > :checkbox:checked").length;
        var l2 = $("#moreDiv"+ title +" > :checkbox:checked").length;

        $("#confirm"+ title).css("display", l1 + l2 > 0 ? "inline" : "none");
    }

    // 点击 选项卡, 调用传入的 optionCallback
    optionCallback = function (checkboxId, title, val){
        var isMulti = $("#isMulti").val() === "1";

        if (isMulti){
            $('#checkbox'+ checkboxId).click();
        } else{
            if (type === "map"){
                o.optionCallback(val);
            } else {
                o.optionCallback(title +":"+ val);
            }
        }
    }
}