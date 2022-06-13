package ru.gb.gbapimay.config;

import feign.*;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gb.gbapimay.category.api.CategoryGateway;
import ru.gb.gbapimay.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapimay.product.api.ProductGateway;

import java.util.concurrent.TimeUnit;

import static feign.FeignException.errorStatus;

@Configuration
//@EnableConfigurationProperties(GbApiProperties.class)
//@RequiredArgsConstructor
@EnableFeignClients(clients = {ProductGateway.class, ManufacturerGateway.class, CategoryGateway.class})

public class FeignConfig {
//    @FeignClient(url = "http://localhost:8080/api/v1/manufacturer", name = "ManufacturerGateway")

//    private final ObjectFactory<HttpMessageConverters> messageConverters;
//    private final ObjectProvider<HttpMessageConverterCustomizer> customizers;
//    private final GbApiProperties gbApiProperties;
//
//    @Bean
//    public ManufacturerGateway manufacturerGateway() {
//        return Feign.builder()
//                .encoder(new SpringEncoder(messageConverters))
//                .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters, this.customizers))))
//                .retryer(new Retryer.Default(
//                        gbApiProperties.getConnection().getPeriod(),
//                        gbApiProperties.getConnection().getMaxPeriod(),
//                        gbApiProperties.getConnection().getMaxAttempts()
//                ))
//                .errorDecoder(errorDecoder())
//                .options(new Request.Options(
//                        gbApiProperties.getConnection().getConnectTimeout(),
//                        TimeUnit.MILLISECONDS,
////                    gbApiProperties.getConnection().getConnectTimeoutUnit(),
//                        gbApiProperties.getConnection().getReadTimeout(),
////                    gbApiProperties.getConnection().getReadTimeoutUnit(),
//                        TimeUnit.MILLISECONDS,
//                        gbApiProperties.getConnection().isFollowRedirects()
//                ))
////                .client()
//                .contract(new SpringMvcContract())
//                .logLevel(Logger.Level.FULL)
//                .logger(new Slf4jLogger(ManufacturerGateway.class))
//                .target(ManufacturerGateway.class, gbApiProperties.getEndpoint().getManufacturerUrl());
//    }
//
//    private ErrorDecoder errorDecoder() {
//        return (methodKey, response) -> {
//            FeignException exception = errorStatus(methodKey, response);
//            if (exception.status() == 500 || exception.status() == 503) {
//                return new RetryableException(
//                        response.status(),
//                        exception.getMessage(),
//                        response.request().httpMethod(),
//                        exception,
//                        null,
//                        response.request());
//            }
//            return exception;
//        };
//    }
}
