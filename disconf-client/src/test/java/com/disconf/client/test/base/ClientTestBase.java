package com.disconf.client.test.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lzj
 * @date 2018/1/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-test.xml"})
public abstract class ClientTestBase {

    @Before
    public void initSetUp() {
        MockitoAnnotations.initMocks(this);
    }


}
