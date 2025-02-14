package uno.stan.myblogBack.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uno.stan.myblogBack.exception.BaseException;
import uno.stan.myblogBack.result.Result;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    //处理违反唯一约束的报错
    @ExceptionHandler(DataAccessException.class)
    public Result exceptionHandler(DataAccessException ex){
        String message=ex.getMessage();
        log.error("SQLIntegrityConstraintViolationException抛出");
        if (message.contains("Duplicate entry")){
            return Result.error("违反唯一约束(重复字段内容)");
        }else {
            return Result.error("未知错误"+message);
        }
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception ex){
        String message=ex.getMessage()+"未知错误抛出";
        log.error(message);
        return Result.error(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result exceptionHandler(IllegalArgumentException ex){
        String message=ex.getMessage()+"非法参数异常";
        return Result.error(message);
    }


    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
