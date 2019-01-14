package com.voting.web.converter;

import com.voting.model.Resto;
import com.voting.service.RestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public  class IdToRestoConverter implements Converter<String, Resto> {
    @Autowired
    private RestoService restoService;

    @Override
    public Resto convert(String id) {
        return restoService.get(Integer.parseInt(id));
    }
}