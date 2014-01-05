package com.charlieknudsen.ribbon.retrofit;

import com.netflix.client.ClientRequest;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.mime.TypedOutput;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HttpRequest extends ClientRequest {
    private final String method;
    private final List<Header> headers;
    private final TypedOutput body;

    public static URI toURI(String url) throws RetrofitError {
        URI uri;
        try {
            uri = new URI(url);
            int port = uri.getPort();
            if (port == -1) {
                switch (uri.getScheme()) {
                    case "http":
                        port = 80;
                        break;
                    case "https":
                        port = 443;
                        break;
                    default:
                        throw new IllegalArgumentException("Unable to determine port of passed in url: " + url);
                }
            }
            return new URI(uri.getScheme(), uri.getUserInfo(), null, port, uri.getPath(), uri.getQuery(), uri.getFragment());
        } catch (URISyntaxException e) {
            throw RetrofitError.unexpectedError(url, e);
        }
    }

    public HttpRequest(URI uri, String method, List<Header> headers, TypedOutput body) {
        super(uri, null, false, null);
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public TypedOutput getBody() {
        return body;
    }
}
