package com.mvc.springwebmvc.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component @Slf4j
public class DateConverter implements Converter<String, Date> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");

    @Override
    public Date convert(String source) {
        try {
            return this.simpleDateFormat.parse(source);
        } catch (Exception e) {
            log.info("Failled convert Strig to date "+e.getMessage());
            return null;
        }
    }
}
