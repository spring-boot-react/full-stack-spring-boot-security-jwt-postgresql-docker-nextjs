package com.example.backend.payload.i18n;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * With each HTTP Servlet Request a language (locale) is provided.
 * This class returns the related message in the provided request locale.
 * The message files are located in resources/i18n
 *
 * @author Jeffrey Spaan
 * @since 2024-03-16
 */

@Service
@RequiredArgsConstructor
public class TranslateServiceImpl implements TranslateService {

    private final MessageSource messageSource;

    private final HttpServletRequest request;

    @Value("${logging.language}")
    private String loggingLanguage;

    @Override
    public String getMessage(String code) throws NoSuchMessageException {
        return messageSource.getMessage(code, null, request.getLocale());
    }

    @Override
    public String getLogMessage(String code) throws NoSuchMessageException {
        return messageSource.getMessage(code, null, Locale.of(loggingLanguage));
    }

    @Override
    public String getMessage(String code, @Nullable String[] args) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, request.getLocale());
    }
}