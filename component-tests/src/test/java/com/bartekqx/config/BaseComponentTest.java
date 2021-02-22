package com.bartekqx.config;

import com.bartekqx.test.util.TestContainers;
import com.bartekqx.test.util.TestTags;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlConfig;

@Tag(TestTags.COMPONENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
@SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.INFERRED)
public class BaseComponentTest extends TestContainers {
}
