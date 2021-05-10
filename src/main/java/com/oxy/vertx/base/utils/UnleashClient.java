package com.oxy.vertx.base.utils;

import com.oxy.vertx.base.entities.BaseRequest;
import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.UnleashContext;
import no.finn.unleash.util.UnleashConfig;

public class UnleashClient {
    private static final Logger log = Logger.getLogger(UnleashClient.class);
    private static Unleash unleash;

    private UnleashClient() {
    }

    private static Unleash getDefaultInstance() {
        if (unleash == null) {
            UnleashConfig config = defaultConfig();
            log.info("Create Unleash client config appName: {}, instanceId: {}, api: {}",
                    config.getAppName(),
                    config.getInstanceId(),
                    config.getUnleashAPI());
            unleash = new DefaultUnleash(config);
        }
        return unleash;
    }

    private static UnleashConfig defaultConfig() {
        return UnleashConfig.builder()
                .appName("oxy")
                .instanceId("oxy")
                .unleashAPI("http://localhost:4242/api")
                .build();
    }

    public static boolean isEnabled(BaseRequest input, String featureName) {
        UnleashContext context = UnleashContext.builder()
                .userId(input.getUser()).build();
        boolean isEnable = getDefaultInstance().isEnabled(featureName, context);
        log.warn("Unleash feature {} is {} for id {} with featureName: {}", featureName, isEnable ? " enabled " : " not enable ", context.getUserId(), featureName);
        return isEnable;
    }

    public static boolean isEnabled(String featureName) {
        boolean isEnable = getDefaultInstance().isEnabled(featureName);
        log.warn("unLeash feature {} is : {}", featureName, isEnable);
        return isEnable;
    }
}
