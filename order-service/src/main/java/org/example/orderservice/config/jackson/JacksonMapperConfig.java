package org.example.orderservice.config.jackson;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_EXCEPTIONS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import insure.pulse.pdp.cadencetools.impl.converter.JacksonDataConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonMapperConfig {

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    //В исходящем джейсоне не должны выводиться строчки со значением null
    mapper.setSerializationInclusion(Include.NON_NULL);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    mapper.registerModule(javaTimeModule);

    SimpleModule simpleModule = new SimpleModule();
    mapper.registerModule(simpleModule);

    mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    mapper.disable(FAIL_ON_EMPTY_BEANS);
    mapper.disable(WRAP_EXCEPTIONS);

    // IMPORTANT TO NOT LOSE PRECISION !!
    mapper.enable(USE_BIG_DECIMAL_FOR_FLOATS);
    mapper.enable(Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    mapper.addHandler(new UnMarshallingErrorHandler());

    return mapper;
  }

  @Bean
  public JacksonDataConverter jacksonDataConverter() {
    return new JacksonDataConverter(objectMapper());
  }

  static final class UnMarshallingErrorHandler extends DeserializationProblemHandler {

    @Override
    public JavaType handleUnknownTypeId(DeserializationContext ctxt, JavaType baseType, String subTypeId,
        TypeIdResolver idResolver, String failureMsg) {
      return baseType;
    }
  }
}
