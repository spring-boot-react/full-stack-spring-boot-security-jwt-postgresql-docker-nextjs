package com.example.backend.config;

import com.example.backend.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfiguration {

    private final List<Mapper> mappers;
    private final List<Converter<?, ?>> converters;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        mappers.forEach(mapper -> mapper.createMapper(modelMapper));
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }
}
