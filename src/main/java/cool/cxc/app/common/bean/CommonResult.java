package cool.cxc.app.common.bean;

import lombok.Data;

@Data
public class CommonResult<T> {

    private Integer status;

    private String msg;

    private T date;

    private CommonResult(T date){
        this.status = CommonEnum.SUCCESS.getStatus();
        this.msg = CommonEnum.SUCCESS.getMsg();
        this.date = date;
    }

    private CommonResult(Integer status, String msg, T date){
        this.status = status;
        this.msg = msg;
        this.date = date;
    }

    public static <T> CommonResult<T> ok(){
        return new CommonResult<>(null);
    }

    public static <T> CommonResult<T> ok(T date){
        return new CommonResult<>(date);
    }

    public static <T> CommonResult<T> fail(String msg){
        return new CommonResult<>(CommonEnum.FAIL.getStatus(), msg, null);
    }

    public static <T> CommonResult<T> fail(String msg, T date){
        return new CommonResult<>(CommonEnum.FAIL.getStatus(), msg, date);
    }

    public static <T> CommonResult<T> fail(Integer status,String msg, T date){
        return new CommonResult<>(status, msg, date);
    }
}

