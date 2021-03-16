package com.itkee.core.exception;

import com.itkee.core.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * @author rabbit
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_EXCEPTION_FORMAT = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";

    /**
     * 运行时异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResult runtimeExceptionHandler(RuntimeException ex) {
        return resultFormat(1, ex);
    }
    /**
     * 空指针异常
     * @param ex
     * @return BaseResult
     */
    @ExceptionHandler(NullPointerException.class)
    public BaseResult nullPointerExceptionHandler(NullPointerException ex) {
        return resultFormat(2, ex);
    }

    /**
     * 类型转换异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    public BaseResult classCastExceptionHandler(ClassCastException ex) {
        return resultFormat(3, ex);
    }

    /**
     * IO异常
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    public BaseResult iOExceptionHandler(IOException ex) {
        return resultFormat(4, ex);
    }

    /**
     * 未知方法异常
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public BaseResult noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return resultFormat(5, ex);
    }

    /**
     * 数组越界异常
     * @param ex
     * @return
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public BaseResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return resultFormat(6, ex);
    }

    /**
     * 400错误
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public BaseResult requestNotReadable(HttpMessageNotReadableException ex) {
        return resultFormat(400, ex);
    }
    /**
     * 400错误
     * @param ex
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    public BaseResult requestTypeMismatch(TypeMismatchException ex) {
        return resultFormat(400, ex);
    }
    /**
     * 400错误
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public BaseResult requestMissingServletRequest(MissingServletRequestParameterException ex) {
        return resultFormat(400, ex);
    }
    /**
     * 405错误
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public BaseResult request405(HttpRequestMethodNotSupportedException ex) {
        return resultFormat(405, ex);
    }
    /**
     * 406错误
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public BaseResult request406(HttpMediaTypeNotAcceptableException ex) {
        return resultFormat(406, ex);
    }
    /**
     * 500错误
     * @param ex
     * @return
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public BaseResult server500(RuntimeException ex) {
        return resultFormat(500, ex);
    }
    /**
     * 除数不能为0
     * @param ex
     * @return
     */
    @ExceptionHandler({ArithmeticException.class})
    public BaseResult arithmeticException(ArithmeticException ex) {
        return resultFormat(13, ex);
    }
    /**
     * 其他错误
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public BaseResult exception(Exception ex) {
        return resultFormat(14, ex);
    }

    private <T extends Throwable> BaseResult resultFormat(Integer code, T ex) {
        log.error(String.format(LOG_EXCEPTION_FORMAT, code, ex.getMessage()));
        return BaseResult.builder().code(code).msg(ex.getMessage()).build();
    }
}