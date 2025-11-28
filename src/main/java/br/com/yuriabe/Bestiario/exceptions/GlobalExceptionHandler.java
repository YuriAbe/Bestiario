package br.com.yuriabe.Bestiario.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final String handleAllExceptions(
            Exception ex, WebRequest request, Model model) {
        String message;
        String redirectTo;
        System.out.println("classe: " + ex.getClass().toString());
        if (ex instanceof NoResourceFoundException) {
            message = "Página ou arquivo não encontrado!";
            redirectTo = "error404";
        } else {
            message = ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado.";
            redirectTo = "error";
        }

        CustomExceptionResponse response = new CustomExceptionResponse(new Date(),
                message, request.getDescription(true));
        // add the model atribute, a data to show
        model.addAttribute("error", response);
        // redirect to error page
        return redirectTo;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final String handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request, Model model) {
        CustomExceptionResponse response = new CustomExceptionResponse(new Date(),
                "Recurso não encontrado!", request.getDescription(true));
        model.addAttribute("error", response);
        return "error";
    }
}
