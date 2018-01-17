package com.disconf.client.support.registry;

import com.disconf.client.support.registry.impl.SpringRegistry;
import org.springframework.context.ApplicationContext;

/**
 * Created by knightliao on 15/11/26.
 */
public class RegistryFactory {

    /**
     *
     */
    public static Registry getSpringRegistry(ApplicationContext applicationContext) throws Exception {

        SpringRegistry registry = new SpringRegistry();
        registry.setApplicationContext(applicationContext);

        return registry;
    }
}
