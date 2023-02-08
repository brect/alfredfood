package com.padawanbr.alfredfood.api.exeptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.EntidadeEmUsoException;
import com.padawanbr.alfredfood.domain.exception.EntidadeNaoEncontradaException;
import com.padawanbr.alfredfood.domain.exception.ValidatitionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.JsonMappingException.*;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICO = "Ocorreu um erro internet inesperado no sistema. Tente novamente. Se o problema persistir, entre em contato com o admin do sistema";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(EntidadeEmUsoException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String message = MSG_ERRO_GENERICO;

        ex.printStackTrace();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String message = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable throwable = ExceptionUtils.getRootCause(ex);

        if (throwable instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) throwable, headers, status, request);
        } else if (throwable instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) throwable, headers, status, request);
        }

        final ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        final String message = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        final String message = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

        String message = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

        final String path = joinPath(ex.getPath());

        final String message = String.format("A propriedade '%s' recebeu o valor '%s', que é do tipo inválido. " +
                        "Corrija e informe um valor valido com o tipo %s.",
                path,
                ex.getValue(),
                ex.getTargetType().getSimpleName());

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                status,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handlerEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest webRequest) {

        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        final ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                httpStatus,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), httpStatus, webRequest);
    }

    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<?> handlerBussines(BussinesException ex, WebRequest webRequest) {

        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ProblemType problemType = ProblemType.BUSSINESS_EXCEPTION;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                httpStatus,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), httpStatus, webRequest);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handlerEntidadeEmUso(EntidadeEmUsoException ex, WebRequest webRequest) {

        final HttpStatus httpStatus = HttpStatus.CONFLICT;
        final ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        final String message = ex.getMessage();

        final CustomExceptionHandler exceptionHandler = customExceptionHandlerBuilder(
                problemType,
                httpStatus,
                message)
                .message(MSG_ERRO_GENERICO)
                .build();

        return handleExceptionInternal(ex, exceptionHandler, new HttpHeaders(), httpStatus, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler({ ValidatitionException.class })
    public ResponseEntity<Object> handleValidacaoException(ValidatitionException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<CustomExceptionHandler.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return CustomExceptionHandler.Object.builder()
                            .name(name)
                            .message(message)
                            .build();
                })
                .collect(Collectors.toList());

        CustomExceptionHandler problem = customExceptionHandlerBuilder(problemType, status, detail)
                .message(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        if (body == null) {
            body = CustomExceptionHandler.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .message(MSG_ERRO_GENERICO)
                    .build();
        } else if (body instanceof String) {
            body = CustomExceptionHandler.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .message(MSG_ERRO_GENERICO)
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
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

}
