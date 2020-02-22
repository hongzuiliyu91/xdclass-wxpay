package net.xiaodiclass.xdclass.exception;

/**
 * 自定义异常类
 */
public class XdException extends RuntimeException{

    /**
     *状态码
     */
    private int code;
    /**
     * 异常信息
     */
    private String msg;

    public XdException(int code,String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
