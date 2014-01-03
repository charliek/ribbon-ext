package com.charlieknudsen.etcd;

import com.charlieknudsen.etcd.transfer.Action;
import retrofit.http.*;

public interface EtcdApi {

    @GET("/v2/keys/{key}")
    Action getKey(@Path("key") String key);

    @FormUrlEncoded
    @PUT("/v2/keys/{key}")
    Action setKey(@Path("key") String key, @Field("value") String value);

    @FormUrlEncoded
    @PUT("/v2/keys/{key}")
    Action setKeyTTL(@Path("key") String key, @Field("value") String value, @Field("ttl") long ttl);

    @DELETE("/v2/keys/{key}")
    Action deleteKey(@Path("key") String key);
}
