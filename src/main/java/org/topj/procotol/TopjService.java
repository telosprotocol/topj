package org.topj.procotol;

import org.topj.methods.response.ResponseBase;

import java.io.IOException;
import java.util.Map;

public interface TopjService {

    <T> ResponseBase<T> send(Map<String, String> args, Class<T> responseClass);

    /**
     * Closes resources used by the service.
     *
     * @throws IOException thrown if a service failed to close all resources
     */
    void close() throws IOException;
}
