package io.graversen.requestbin.websocket;

import io.graversen.fiber.config.base.ServerConfig;

public class WebSocketServerConfig extends ServerConfig
{
    public WebSocketServerConfig()
    {
        super(18001, "0.0.0.0");
    }
}
