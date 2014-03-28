/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.lightblue.rest.metadata.hystrix;

import com.redhat.lightblue.util.Error;
import com.redhat.lightblue.Response;
import com.redhat.lightblue.config.metadata.MetadataManager;
import com.redhat.lightblue.rest.metadata.RestMetadataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nmalik
 */
public class GetDependenciesCommand extends AbstractRestCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetDependenciesCommand.class);

    private final String entity;
    private final String version;

    public GetDependenciesCommand(String clientKey, String entity, String version) {
        super(GetDependenciesCommand.class.getSimpleName(), GetDependenciesCommand.class.getSimpleName(), clientKey);
        this.entity = entity;
        this.version = version;
    }

    @Override
    protected String run() throws Exception {
        LOGGER.debug("run: entity={}, version={}", entity, version);
        Error.reset();
        Error.push(getClass().getSimpleName());
        if (entity != null) {
            Error.push(entity);
        }
        if (version != null) {
            Error.push(version);
        }
        try {
            Response r = MetadataManager.getMetadata().getDependencies(entity, version);
            return r.toJson().toString();
        } catch (Error e) {
            return e.toString();
        } catch (Exception e) {
            LOGGER.error("Failure: {}", e);
            return Error.get(RestMetadataConstants.ERR_REST_ERROR, e.toString()).toString();
        } finally {
            Error.reset();
        }
    }
}