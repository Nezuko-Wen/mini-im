package com.wen.open.miniim.core.protocol;

import com.wen.open.miniim.common.context.ConfigContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Wen
 * @date 2023/4/10 23:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SerializerTest {
    @Test
    public void beanTest() {
        System.out.println(ConfigContextHolder.config().getDefaultHostname());
    }
}
