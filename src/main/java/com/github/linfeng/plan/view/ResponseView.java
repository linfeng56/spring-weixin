package com.github.linfeng.plan.view;

/**
 * 操作结果
 *
 * @author 黄麟峰
 * @date 2021-08-02
 */
public class ResponseView<E> {

    private Integer status = 200;
    private String code = "OK";
    private String server = "nginx";

    private E data;

    public ResponseView(Integer status, String code) {
        this.status = status;
        this.code = code;
    }

    public ResponseView(Integer status, String code, E data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseView{");
        sb.append("status=").append(status);
        sb.append(", code='").append(code).append('\'');
        sb.append(", server='").append(server).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
