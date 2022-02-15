package com.upedge.thirdparty.fpx.model;

public class ResponseMsg {
    public String result;
    public String msg;
    public ResponseError errors;
    public Object data;

    public ResponseMsg() {
    }

    public ResponseMsg(String result, String msg, ResponseError errors, Object data) {
        this.result = result;
        this.msg = msg;
        this.errors = errors;
        this.data = data;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseError getErrors() {
        return this.errors;
    }

    public void setErrors(ResponseError errors) {
        this.errors = errors;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "ResponseMsg{result='" + this.result + '\'' + ", msg='" + this.msg + '\'' + ", errors=" + this.errors + ", data=" + this.data + '}';
    }

    public static ResponseMsg fial(String msg) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsg(msg);
        return responseMsg;
    }


}
