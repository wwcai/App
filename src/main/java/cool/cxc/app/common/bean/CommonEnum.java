package cool.cxc.app.common.bean;

public enum CommonEnum {
    SUCCESS(1,"成功"),
    FAIL(-1, "失败");
    private Integer status;

    private String msg;

    CommonEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
