package com.charlieknudsen.ribbon.retrofit;

import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.config.IClientConfig;

public class LoadBalancedClient extends AbstractLoadBalancerAwareClient<HttpRequest, HttpResponse> {

    // TODO need to actually use okhttp by overloading the connect method
    // TODO pull default timeouts and other configuration from configuration
    private final UrlConnectionClient client;

    public LoadBalancedClient() {
        super();
        this.client = new UrlConnectionClient();
    }

    public LoadBalancedClient(UrlConnectionClient client) {
        super();
        this.client = client;
    }

    public LoadBalancedClient(IClientConfig clientConfig, UrlConnectionClient client) {
        super(clientConfig);
        this.client = client;
    }

    @Override
    protected boolean isCircuitBreakerException(Throwable e) {
        // TODO implement proper rules here
        return false;
    }

    @Override
    protected boolean isRetriableException(Throwable e) {
        // TODO implement proper rules here
        return false;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws Exception {
        return client.execute(request);
    }
}
