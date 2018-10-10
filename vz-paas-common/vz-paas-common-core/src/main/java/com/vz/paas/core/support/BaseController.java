package com.vz.paas.core.support;

import com.vz.paas.base.constant.GlobalConstant;
import com.vz.paas.base.dto.LoginAuthDto;
import com.vz.paas.base.exception.BusinessException;
import com.vz.paas.base.exception.ErrorCodeEnum;
import com.vz.paas.util.PublicUtil;
import com.vz.paas.util.ThreadLocalMapUtil;
import com.vz.paas.util.wrapper.WrapMapper;
import com.vz.paas.util.wrapper.Wrapper;
import com.vz.paas.zk.generator.IncrementIdGenerator;
import com.vz.paas.zk.generator.UniqueIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制器基类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 11:56:36
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取登录验证数据传输对象
     * @return 成功对象
     */
    protected LoginAuthDto getLoginAuthDto() {
        LoginAuthDto dto = (LoginAuthDto) ThreadLocalMapUtil.get(GlobalConstant.System.TOKEN_AUTH_DTO);
        if (PublicUtil.isEmpty(dto)) {
            throw new BusinessException(ErrorCodeEnum.UAC10011039);
        }
        return dto;
    }

    /**
     * 处理返回结果集包装类
     * @param result 结果集
     * @param <T> 类型
     * @return 包装类
     */
    protected <T> Wrapper<T> handleResult(T result) {
        boolean flag = isFlag(result);

        if (flag) {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } else {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE, result);
        }
    }

    /**
     * 是否合法信息
     * Integer 需要大于0
     * Boolean 直接判断
     * 其他类型判断是否为Empty
     * @param result 对象
     * @return 是否合法
     */
    private boolean isFlag(Object result) {
        boolean flag;

        if (result instanceof Integer) {
            flag = (Integer)result > 0;
        } else if (result instanceof Boolean) {
            flag = (Boolean) result;
        } else {
            flag = PublicUtil.isNotEmpty(result);
        }

        return flag;

    }

    /**
     * 处理返回结果集包装类
     * @param result 结果集
     * @param errorMsg 错误信息
     * @param <T> 类型
     * @return 包装类
     */
    protected <T> Wrapper<T> handleResult(T result, String errorMsg) {
        boolean flag = isFlag(result);
        if (flag) {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } else {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, errorMsg, result);
        }
    }

    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    protected long generateId() {
        return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
    }
}
