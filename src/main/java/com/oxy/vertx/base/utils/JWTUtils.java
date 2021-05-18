package com.oxy.vertx.base.utils;

import com.oxy.vertx.base.conf.CommonConfig;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class JWTUtils {
    private static class JWTUtilsHolder {
        static final JWTUtils instance = new JWTUtils();
    }

    public static JWTUtils getInstance() {
        return JWTUtilsHolder.instance;
    }

    public JWTAuth getProvider() {
        return JWTAuth.create(CommonConfig.getVertx(), new JWTAuthOptions()
                .setKeyStore(new KeyStoreOptions()
                        .setType("jceks")
                        .setPath("keystore.jceks")
                        .setPassword("secret")));
    }
}
