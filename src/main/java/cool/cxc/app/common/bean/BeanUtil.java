package cool.cxc.app.common.bean;

import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanUtil {

    public static <B, M> B fromModel(M model, Class<B> beanClz) {
        B bean = null;
        try {
            bean = beanClz.newInstance();
            cn.hutool.core.bean.BeanUtil.copyProperties(model, bean);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("unexcept exception:", e);
        }
        return bean;
    }

    public static <B, M> M fromBean(B bean, Class<M> modelClz) {
        M model = null;
        try {
            model = modelClz.newInstance();
            cn.hutool.core.bean.BeanUtil.copyProperties(bean, model);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("unexcept exception:", e);

        }
        return model;
    }

    public static <B, M> M modifyFromBean(B bean, M srcModel, Class<M> modelClz) {
        M model = null;
        try {
            model = modelClz.newInstance();
            cn.hutool.core.bean.BeanUtil.copyProperties(srcModel, model);
            cn.hutool.core.bean.BeanUtil.copyProperties(bean, model, CopyOptions.create().ignoreNullValue());
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("unexcept exception:", e);
        }
        return model;
    }
}
