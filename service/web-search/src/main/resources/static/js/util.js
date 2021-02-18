/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/18 18:30
 * @since 1.0.0
 */

/*动态在目标div的原本top属性的基础上增加数值
    * target 目标元素
    * value 每行的高度
    * rowNum 行数*/
function changeTop(target, value, rowNum) {
    var top = target.css("top");

    if (top === "auto"){
        top = "0px";
    }

    top = parseInt(top.replace("px"));

    top += rowNum * value;
    top += "px";
    target.css("top", top);
}