package com.disconf.web.aop;

import com.disconf.core.common.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author lzj
 * @date 2018/1/4
 */
@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public Response runTimeHandler(Exception exception) {
        logger.error("web运行时异常：" + exception.getMessage(), exception);
        return Response.result(500, "系统内部错误");
    }


}
