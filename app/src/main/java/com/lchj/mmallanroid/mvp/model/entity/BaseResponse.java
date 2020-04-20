package com.lchj.mmallanroid.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class BaseResponse<T> implements Serializable {
    private boolean success;
    private T data;
    private String msg;
    private List<T> list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
