package io.graversen.requestbin.config;

import io.graversen.fiber.event.bus.AbstractEventBus;
import io.graversen.fiber.event.bus.DefaultEventBus;
import io.graversen.fiber.server.management.AbstractNetworkClientManager;
import io.graversen.fiber.server.websocket.SimpleWebSocketServer;
import io.graversen.fiber.server.websocket.base.AbstractWebSocketServer;
import io.graversen.requestbin.websocket.LoggingNetworkEventListener;
import io.graversen.requestbin.websocket.WebSocketClientManager;
import io.graversen.requestbin.websocket.WebSocketServerConfig;
import io.graversen.trunk.io.serialization.GsonSerializer;
import io.graversen.trunk.io.serialization.interfaces.ISerializer;
import io.graversen.trunk.network.IpAddressUtils;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ConfigBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
{
    @Override
    public void customize(ConfigurableServletWebServerFactory container)
    {
        container.setPort(18000);
    }

    @Bean(initMethod = "start")
    public AbstractWebSocketServer webSocketServer()
    {
        return new SimpleWebSocketServer(new WebSocketServerConfig(), networkClientManager(), eventBus());
    }

    @Bean
    public AbstractEventBus eventBus()
    {
        final DefaultEventBus defaultEventBus = new DefaultEventBus();
        new LoggingNetworkEventListener(defaultEventBus);

        return defaultEventBus;
    }

    @Bean
    public AbstractNetworkClientManager networkClientManager()
    {
        return new WebSocketClientManager();
    }

    @Bean
    public ISerializer serializer()
    {
        return new GsonSerializer();
    }

    @Bean
    public IpAddressUtils ipAddressUtils()
    {
        return new IpAddressUtils();
    }

    @Bean
    public Executor threadPoolTaskExecutor()
    {
        return new ThreadPoolTaskExecutor();
    }
}
