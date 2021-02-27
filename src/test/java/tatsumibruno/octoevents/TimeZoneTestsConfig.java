package tatsumibruno.octoevents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Configuration
public class TimeZoneTestsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeZoneTestsConfig.class);

    @Bean
    public TimeZone timeZone() {
        TimeZone defaultTimeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(defaultTimeZone);
        LOGGER.info("Spring boot tests running in UTC timezone :" + ZonedDateTime.now());
        return defaultTimeZone;
    }

}
