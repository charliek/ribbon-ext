package com.charlieknudsen.etcd;

import com.charlieknudsen.etcd.transfer.Action;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;
import retrofit.http.Field;
import retrofit.http.Path;

public class EtcdClient implements EtcdApi {

    private static String cleanKey(String key) {
        if(key.startsWith("/")) {
            key = key.substring(1, key.length());
        }
        return key;
    }

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true);

    private final EtcdApi api;

    public EtcdClient(String url) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(url)
                .setConverter(new JacksonConverter(objectMapper))
                .build();
        api = restAdapter.create(EtcdApi.class);
    }

    public EtcdClient() {
        this("http://127.0.0.1:4001");
    }

    @Override
    public Action getKey(@Path("key") String key) {
        return api.getKey(cleanKey(key));
    }

    @Override
    public Action setKey(@Path("key") String key, @Field("value") String value) {
        return api.setKey(cleanKey(key), value);
    }

    @Override
    public Action setKeyTTL(@Path("key") String key, @Field("value") String value, @Field("ttl") long ttl) {
        return api.setKeyTTL(cleanKey(key), value, ttl);
    }

    @Override
    public Action deleteKey(@Path("key") String key) {
        return api.deleteKey(cleanKey(key));
    }
}
