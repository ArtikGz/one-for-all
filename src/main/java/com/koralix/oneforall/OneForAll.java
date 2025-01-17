package com.koralix.oneforall;

import com.koralix.oneforall.platform.Platform;
import com.koralix.oneforall.settings.SettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class OneForAll {
    public static final String MOD_ID = "oneforall";

    private final Logger logger = LoggerFactory.getLogger("OneForAll");
    private final Platform platform;

    public static OneForAll getInstance() {
        return Initializer.instance;
    }

    public OneForAll(Platform platform) {
        this.platform = platform;
    }

    abstract void onInitialize();

    public Logger getLogger() {
        return logger;
    }

    public Platform getPlatform() {
        return platform;
    }
}
