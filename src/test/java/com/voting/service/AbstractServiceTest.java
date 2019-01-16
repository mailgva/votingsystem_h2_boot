package com.voting.service;

import com.voting.TimingExtension;
import com.voting.configuration.PersistenceJPAConfig;
import com.voting.configuration.SecurityConfig;
import com.voting.configuration.WebMvcConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.text.SimpleDateFormat;

import static com.voting.util.ValidationUtil.getRootCause;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
@SpringBootTest
@ExtendWith(TimingExtension.class)
abstract class AbstractServiceTest {
    public final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    static {
        SLF4JBridgeHandler.install();
    }

    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}