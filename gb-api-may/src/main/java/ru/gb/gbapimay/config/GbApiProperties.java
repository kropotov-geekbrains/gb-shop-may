package ru.gb.gbapimay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.TimeUnit;

/**
 * @author Artem Kropotov
 * created at 27.05.2022
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "gb.api")
public class GbApiProperties {

    private Connection connection;
    private Endpoint endpoint;

    @Getter
    @Setter
    public static class Endpoint {
        private String manufacturerUrl;
        private String categoryUrl;
        private String productUrl;
    }

    @Getter
    @Setter
    public static class Connection {
        private long period;
        private long maxPeriod;
        private int maxAttempts;
        long connectTimeout;
        //        TimeUnit connectTimeoutUnit; todo
        long readTimeout;
        //        TimeUnit readTimeoutUnit; todo
        boolean followRedirects;
    }
//    this(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
}
