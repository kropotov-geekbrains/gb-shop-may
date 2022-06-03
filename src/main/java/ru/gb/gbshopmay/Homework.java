package ru.gb.gbshopmay;

import feign.*;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.support.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapimay.category.api.CategoryGateway;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.gb.gbapimay.config.GbApiProperties;
import ru.gb.gbapimay.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapimay.product.api.ProductGateway;
import ru.gb.gbapimay.product.dto.ProductDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static feign.FeignException.errorStatus;

public class Homework {
    public interface ProductGateway {

        @GetMapping
        List<ProductDto> getProductList();

        @GetMapping("/{productId}")
        ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long id);

        @PostMapping
        ResponseEntity<ProductDto> handlePost(@Validated @RequestBody ProductDto productDto);

        @PutMapping("/{productId}")
        ResponseEntity<ProductDto> handleUpdate(@PathVariable("productId") Long id,
                                                @Validated @RequestBody ProductDto productDto);

        @DeleteMapping("/{productId}")
        void deleteById(@PathVariable("productId") Long id);
    }
    public interface CategoryGateway {

        @GetMapping
        List<CategoryDto> getCategoryList();

        @GetMapping("/{categoryId}")
        ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long id);

        @PostMapping
        ResponseEntity<CategoryDto> handlePost(@Validated @RequestBody CategoryDto categoryDto);

        @PutMapping("/{categoryId}")
        ResponseEntity<CategoryDto> handleUpdate(@PathVariable("categoryId") Long id,
                                                 @Validated @RequestBody CategoryDto categoryDto);

        @DeleteMapping("/{categoryId}")
        void deleteById(@PathVariable("categoryId") Long id);
    }

    Configuration
    @EnableConfigurationProperties(GbApiProperties.class)
    @RequiredArgsConstructor
    public class FeignConfig {

        private final ObjectFactory<HttpMessageConverters> messageConverters;
        private final ObjectProvider<HttpMessageConverterCustomizer> customizers;
        private final GbApiProperties gbApiProperties;

        public ManufacturerGateway manufacturerGateway() {
            return Feign.builder()
                    .encoder(new SpringEncoder(messageConverters))
                    .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters, this.customizers))))
                    .retryer(new Retryer.Default(
                            gbApiProperties.getConnection().getPeriod(),
                            gbApiProperties.getConnection().getMaxPeriod(),
                            gbApiProperties.getConnection().getMaxAttempts()
                    ))
                    .errorDecoder(errorDecoder())
                    .options(new Request.Options(
                            gbApiProperties.getConnection().getConnectTimeout(),
                            TimeUnit.MILLISECONDS,
//                    gbApiProperties.getConnection().getConnectTimeoutUnit(),
                            gbApiProperties.getConnection().getReadTimeout(),
//                    gbApiProperties.getConnection().getReadTimeoutUnit(),
                            TimeUnit.MILLISECONDS,
                            gbApiProperties.getConnection().isFollowRedirects()
                    ))
//                .client()
                    .contract(new SpringMvcContract())
                    .logLevel(Logger.Level.FULL)
                    .logger(new Slf4jLogger(ManufacturerGateway.class))
                    .target(ManufacturerGateway.class, gbApiProperties.getEndpoint().getManufacturerUrl());
        }

        private ErrorDecoder errorDecoder() {
            return (methodKey, response) -> {
                FeignException exception = errorStatus(methodKey, response);
                if (exception.status() == 500 || exception.status() == 503) {
                    return new RetryableException(
                            response.status(),
                            exception.getMessage(),
                            response.request().httpMethod(),
                            exception,
                            null,
                            response.request());
                }
                return exception;
            };
        }

        public ru.gb.gbapimay.product.api.ProductGateway productGateway() {
            return Feign.builder()
                    .encoder(new SpringEncoder(messageConverters))
                    .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters, this.customizers))))
                    .retryer(new Retryer.Default(
                            gbApiProperties.getConnection().getPeriod(),
                            gbApiProperties.getConnection().getMaxPeriod(),
                            gbApiProperties.getConnection().getMaxAttempts()
                    ))
                    .errorDecoder(productErrorDecoder())
                    .options(new Request.Options(
                            gbApiProperties.getConnection().getConnectTimeout(),
                            TimeUnit.MILLISECONDS,
                            gbApiProperties.getConnection().getReadTimeout(),
                            TimeUnit.MILLISECONDS,
                            gbApiProperties.getConnection().isFollowRedirects()
                    ))
                    .contract(new SpringMvcContract())
                    .logLevel(Logger.Level.FULL)
                    .logger(new Slf4jLogger(ru.gb.gbapimay.product.api.ProductGateway.class))
                    .target(ru.gb.gbapimay.product.api.ProductGateway.class, gbApiProperties.getEndpoint().getProductUrl());
        }

        private ErrorDecoder productErrorDecoder() {
            return (methodKey, response) -> {
                FeignException exception = errorStatus(methodKey, response);
                if (exception.status() == 500 || exception.status() == 503) {
                    return new RetryableException(
                            response.status(),
                            exception.getMessage(),
                            response.request().httpMethod(),
                            exception,
                            null,
                            response.request());
                }
                return exception;
            };
        }

        public ru.gb.gbapimay.category.api.CategoryGateway categoryGateway() {
            return Feign.builder()
                    .encoder(new SpringEncoder(messageConverters))
                    .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters, this.customizers))))
                    .retryer(new Retryer.Default(
                            gbApiProperties.getConnection().getPeriod(),
                            gbApiProperties.getConnection().getMaxPeriod(),
                            gbApiProperties.getConnection().getMaxAttempts()
                    ))
                    .errorDecoder(categoryErrorDecoder())
                    .options(new Request.Options(
                            gbApiProperties.getConnection().getConnectTimeout(),
                            TimeUnit.MILLISECONDS,
                            gbApiProperties.getConnection().getReadTimeout(),
                            TimeUnit.MILLISECONDS,
                            gbApiProperties.getConnection().isFollowRedirects()
                    ))
                    .contract(new SpringMvcContract())
                    .logLevel(Logger.Level.FULL)
                    .logger(new Slf4jLogger(ru.gb.gbapimay.category.api.CategoryGateway.class))
                    .target(ru.gb.gbapimay.category.api.CategoryGateway.class, gbApiProperties.getEndpoint().getCategoryUrl());
        }

        private ErrorDecoder categoryErrorDecoder() {
            return (methodKey, response) -> {
                FeignException exception = errorStatus(methodKey, response);
                if (exception.status() == 500 || exception.status() == 503) {
                    return new RetryableException(
                            response.status(),
                            exception.getMessage(),
                            response.request().httpMethod(),
                            exception,
                            null,
                            response.request());
                }
                return exception;
            };
        }
    }

    @Getter
    @Setter
//    @ConfigurationProperties(prefix = "gb.api")
    public class GbApiProperties {

        private ru.gb.gbapimay.config.GbApiProperties.Connection connection;
        private ru.gb.gbapimay.config.GbApiProperties.Endpoint endpoint;

        @Getter
        @Setter
        public static class Endpoint {
            private String manufacturerUrl;
            private String productUrl;
            private String categoryUrl;
        }

        @Getter
        @Setter
        public static class Connection {
            private long period;
            private long maxPeriod;
            private int maxAttempts;
            long connectTimeout;
            long readTimeout;
            boolean followRedirects;
        }
    }
}
