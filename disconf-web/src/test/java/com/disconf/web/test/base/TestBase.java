package com.disconf.web.test.base;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * context-test
 *
 * @author lzj
 * @date 2018/1/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-test.xml"})
public abstract class TestBase {

    private boolean isMock = true;

    @Before
    public void initSetUp() {
        if (isMock) {
            MockitoAnnotations.initMocks(this);
        }
    }

    public void setIsMock(boolean isMock) {
        this.isMock = isMock;
    }

}
