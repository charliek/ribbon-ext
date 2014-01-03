package com.charlieknudsen.ribbon.retrofit;

import com.netflix.client.ClientException;
import com.netflix.client.IResponse;
import retrofit.client.Header;
import retrofit.mime.TypedInput;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse implements IResponse {
    private final int status;
    private final String reason;
    private final List<Header> headers;
    private final TypedInput body;
    private final URI requestedURI;

    public HttpResponse(int status, String reason, List<Header> headers, TypedInput body, URI requestedURI) {
        this.status = status;
        this.reason = reason;
        this.headers = headers;
        this.body = body;
        this.requestedURI = requestedURI;
    }

    @Override
    public Object getPayload() throws ClientException {
        return body;
    }

    @Override
    public boolean hasPayload() {
        return isSuccess() && body != null;
    }

    @Override
    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }

    @Override
    public URI getRequestedURI() {
        return requestedURI;
    }

    @Override
    public Map<String, ?> getHeaders() {
        Map<String, String> localHeaders = new HashMap<String, String>();
        for (Header header : headers) {
            localHeaders.put(header.getName(), header.getValue());
        }
        return localHeaders;
    }

    @Override
    public void close() throws IOException {
        // nothing to close
    }

    public List<Header> getRetrofitHeaders() {
        return headers;
    }

    public int getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public TypedInput getBody() {
        return body;
    }
}
