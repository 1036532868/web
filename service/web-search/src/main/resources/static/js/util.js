/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/18 18:30
 * @since 1.0.0
 */

/** 动态在目标div的原本top属性的基础上增加数值
 * @param target 目标元素: "#target"
 * @param value 每行的高度
 * @param rowNum 行数
 * @param isArray 是否是数组
 * */
function changeTop(target, value, rowNum, isArray) {
    if (isArray){
        for(var i = 0; i < target.length; i++){
            exec(target[i]);
        }
    } else{
        exec(target);
    }


    function exec(string) {
        var target = $(string);
        var top = target.css("top");
        if (top === "auto") {
            top = "0px";
        }

        top = parseInt(top.replace("px"));

        top += rowNum * value;
        top += "px";
        target.css("top", top);
    }
}