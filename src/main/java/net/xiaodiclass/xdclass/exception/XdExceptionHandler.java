package net.xiaodiclass.xdclass.exception;

import net.xiaodiclass.xdclass.domain.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理控制器
 */
@ControllerAdvice
public class XdExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){
        if(e instanceof XdException){
            XdException xdException=(XdException)e;
            return JsonData.buildError(xdException.getCode(),xdException.getMsg());
        }else{
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}
