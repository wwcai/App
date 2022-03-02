package cool.cxc.app.common.errdefine;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ErrUtils {
    @Resource
    private ErrMessage errMessage = new ErrMessage();

/*    public RespBean errorResp(ErrCode errCode) {
        return RespBean.error(errCode, errMessage.messageByErr(errCode));
    }*/

//    public RespBean errorResp(ErrCode errCode, String message) {
//        return RespBean.error(errCode, message);
//    }
}
