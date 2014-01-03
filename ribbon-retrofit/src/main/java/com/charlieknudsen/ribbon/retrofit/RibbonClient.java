package com.charlieknudsen.ribbon.retrofit;

import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.ClientException;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

import java.io.IOException;

public class RibbonClient implements Client {

    private final AbstractLoadBalancerAwareClient<HttpRequest, HttpResponse> client;

    public RibbonClient(LoadBalancedClient client) {
        this.client = client;
    }

    @Override
    public Response execute(Request request) throws IOException {
        String url = request.getUrl();
        HttpRequest httpRequest = new HttpRequest(HttpRequest.toURI(url), request.getMethod(), request.getHeaders(), request.getBody());
        HttpResponse httpResponse;
        try {
            httpResponse = client.executeWithLoadBalancer(httpRequest);
        } catch (ClientException e) {
            // TODO need to properly catch retrofit exceptions
            throw RetrofitError.unexpectedError(url, e);
        }
        Response response = new Response(
                httpResponse.getStatus(),
                httpResponse.getReason(),
                httpResponse.getRetrofitHeaders(),
                httpResponse.getBody());
        return response;
    }
}
