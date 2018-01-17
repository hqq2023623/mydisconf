package con.disconf.web.zk.test;

import static org.junit.Assert.*;

import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.test.base.TestBase;
import com.disconf.web.zookeeper.ZkConfigMgr;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lzj
 * @date 2018/1/9
 */
public class TestZkConfigMgr extends TestBase {

    @Autowired
    private ZkConfigMgr zkConfigMgr;

    //
    private String zkPath = "/disconf/group1_app1_1.0.0";

    private String expectedUPdateValue = "value for test";

    @Test
    public void testCreate() throws Exception {

        zkConfigMgr.createConsistentPath(1L);
        boolean exists = zkConfigMgr.checkExists(zkPath);
        assertTrue(exists);

        zkConfigMgr.deleteNode(zkPath);

        exists = zkConfigMgr.checkExists(zkPath);
        assertFalse(exists);

    }

    @Test
    public void testUpdate() throws Exception {

        boolean exists = zkConfigMgr.checkExists(zkPath);
        if (!exists) {
            zkConfigMgr.createConsistentPath(1L);
        }

        ConfigEntity configEntity = this.getMockConfig();
        zkConfigMgr.updateNode(configEntity);

        String str = zkConfigMgr.queryStringValue(zkPath);
        assertEquals(expectedUPdateValue, str);

    }


    private ConfigEntity getMockConfig() {
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setGroupName("group1");
        configEntity.setAppName("app1");
        configEntity.setVersion("1.0.0");
        configEntity.setValue(expectedUPdateValue);
        return configEntity;

    }


}
