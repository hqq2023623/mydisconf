package com.disconf.client.store.processor.impl;

import com.disconf.client.common.update.IDisconfUpdatePipeline;
import com.disconf.client.store.DisconfStorePipelineProcessor;
import com.disconf.client.store.inner.DisconfCenterStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class DisconfStorePipelineProcessorImpl implements DisconfStorePipelineProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfStorePipelineProcessorImpl.class);

    @Override
    public void setDisconfUpdatePipeline(IDisconfUpdatePipeline iDisconfUpdatePipeline) {

        DisconfCenterStore.getInstance().setiDisconfUpdatePipeline(iDisconfUpdatePipeline);
    }
}
