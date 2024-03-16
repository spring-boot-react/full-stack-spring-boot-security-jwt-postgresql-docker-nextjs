package com.example.backend.payload.i18n;

import org.springframework.lang.Nullable;

public interface TranslateService {
    public abstract String getMessage(String code);
    public abstract String getLogMessage(String code);
    public abstract String getMessage(String code, @Nullable String[] args);
}
