package com.ssamz.jblog.controller.advice;

import com.ssamz.jblog.dto.ResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationCheckAdvice {

    @Around("execution(* com.ssaz..controller.*Controller.*(..))")
    public Object validationCheck(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            // 인자 목록에 BindingResult의 객체가 있다면
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    // 에러가 하나라도 있다면, 에러 메시지를 Map에 저장
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }

        return jp.proceed();
    }
}
