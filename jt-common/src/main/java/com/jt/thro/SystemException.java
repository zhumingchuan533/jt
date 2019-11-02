package com.jt.thro;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice//对controller层异常有效
public class SystemException {
	@ExceptionHandler(RuntimeException.class)//异常处理器
	@ResponseBody
    public SysResult exception(Throwable throwable) {
		throwable.printStackTrace();
		log.info(throwable.getMessage());
    	return SysResult.fail();
    }
}
