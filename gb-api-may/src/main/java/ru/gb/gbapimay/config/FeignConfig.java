package ru.gb.gbapimay.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.gb.gbapimay.category.api.CategoryGateway;
import ru.gb.gbapimay.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapimay.product.api.ProductGateway;

@Configuration
@EnableConfigurationProperties(GbApiProperties.class)
@RequiredArgsConstructor
@Import(value = {FeignClientFactory.class})

public class FeignConfig {

    private final FeignClientFactory feignClientFactory;
    private final GbApiProperties gbApiProperties;

    @Bean
    public ManufacturerGateway manufacturerGateway() {
        return feignClientFactory.newFeignGateway(ManufacturerGateway.class, gbApiProperties.getEndpoint().getManufacturerUrl());
    }

    @Bean
    public CategoryGateway categoryGateway() {
        return feignClientFactory.newFeignGateway(CategoryGateway.class, gbApiProperties.getEndpoint().getCategoryUrl());
    }

    @Bean
    public ProductGateway productGateway() {
        return feignClientFactory.newFeignGateway(ProductGateway.class, gbApiProperties.getEndpoint().getProductUrl());
    }
}
