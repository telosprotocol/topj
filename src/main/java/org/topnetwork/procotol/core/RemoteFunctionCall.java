package org.topnetwork.procotol.core;

import java.util.concurrent.Callable;

public class RemoteFunctionCall<T> {

    private Callable<T> callable;

    public RemoteFunctionCall(Callable<T> callable) {
        this.callable = callable;
    }

    public T send() throws Exception {
        return callable.call();
    }
}
