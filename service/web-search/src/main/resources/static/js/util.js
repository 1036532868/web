// 工具函数 ------------------------------------------------------------------------------
// 样式控制
// onmouseover='overHref("+ id +", "+ red +")' onmouseleave='leaveHref("+ id +", "+ black +")'
var red = JSON.stringify("red");
var black = JSON.stringify("black");
var blue = JSON.stringify("blue");
// 鼠标悬停在可点击的文字上
function overHref(id, color){
    //alert(id)
    //alert($("#"+ id).length)
    $("#"+ id).css("color", color);
}
// 鼠标离开可点击的文字
function leaveHref(id, color){
    //alert(id)
    $("#"+ id).css("color", color);
}

/**
 * 根据字符串的内容判断 生成的超链接的长度, 包括在超链接之后的 spaceNum 个空格
 * @param text 文本
 * @param spaceNum 空格个数 默认0
 * @param regex 正则表达式, 匹配 将被认作没有中文的字符串, 其余没有匹配成功的都作为中文长度计算。
 *              默认 /^[-\.~/、A-Za-z0-9]+$/
 * @version 1.0.0
 * @date 2021/2/20 12:13
 * @since 1.0.0
 */
function getContentWidth(text, spaceNum, regex) {
    if (regex === undefined){
        regex = /^[-\.~/、A-Za-z0-9]+$/;
    }
    if (typeof spaceNum !== "number") spaceNum = 0;

    var width = 0;
    if (regex.test(text)) {
        //文本由字母、数字、特定特殊符号组成时
        width += text.length * 9.39;
    } else {
        //文本中含有中文时
        width += text.length * 18;
    }

    width += spaceNum * 4.37;
    return width;
}

/**
 * 动态在目标div的原本top属性的基础上增加数值
 * @param target 目标元素: "#target"
 * @param value 每行的高度
 * @param rowNum 行数 默认为1
 * @param isArray 是否是数组 默认为false
 *
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/18 18:30
 * @since 1.0.0
 * */
function changeTop(target, value, rowNum) {
    if (typeof rowNum !== "number") rowNum = 1;

    for(var i = 0; i < target.length; i++){
        exec(target[i]);
    }


    function exec(string) {
        var target = $(string);
        var top = target.css("top");
        if (top === "auto") {
            top = "0px";
        }

        top = parseInt(top.replaceAll("px"));

        top += rowNum * value;
        top += "px";
        target.css("top", top);
    }
}

/**
 * 更改在此div之后生成的div的top属性.
 * <pre>
 * 遍历, 当寻找到和当前要触发事件的 title相同的title时,
 * 从下一次循环开始更改top属性
 * </pre>
 * */
function changeOtherOptionTop(currentTitle, height, data, type) {
    var gotCurrentTitle = false;

    if (type === "map"){
        for (var t in data){

            if (gotCurrentTitle){
                changeTop(["#div"+ t], height);
                if ($("#moreDiv"+ t)[0]) changeTop(["#moreDiv"+ t], height);
            }

            if (t === currentTitle){
                gotCurrentTitle = true;
            }
        }
    } else {

        for (var i = 0; i < data.length; i++){

            var t = data[i].name;

            if (gotCurrentTitle){

                changeTop(["#div"+ t], height);
                if ($("#moreDiv"+ t)[0]) changeTop(["#moreDiv"+ t], height);
            }

            if (t === currentTitle){
                gotCurrentTitle = true;
            }

        }

    }

}