package com.padawanbr.alfredfood.api.exeptionhandler;

import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handlerEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest webRequest) {

        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        final ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                httpStatus,
                message)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), httpStatus, webRequest);
    }


    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<?> handlerBussinesException(EntidadeNaoEncontradaException ex, WebRequest webRequest) {

        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ProblemType problemType = ProblemType.BUSSINESS_EXCEPTION;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                httpStatus,
                message)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), httpStatus, webRequest);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handlerEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest webRequest) {

        final HttpStatus httpStatus = HttpStatus.CONFLICT;
        final ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                httpStatus,
                message)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), httpStatus, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        if (body == null) {
            body = CustomExceptionHandler.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = CustomExceptionHandler.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    private CustomExceptionHandler.CustomExceptionHandlerBuilder customExceptionHandlerBuilder(ProblemType problemType,
                                                                                               HttpStatus httpStatus,
                                                                                               String detail) {
        return CustomExceptionHandler.builder()
                .title(problemType.getTitle())
                .status(httpStatus.value())
                .type(problemType.getUri())
                .detail(detail);
    }

}
