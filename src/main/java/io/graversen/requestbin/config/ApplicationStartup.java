package io.graversen.requestbin.config;

import io.graversen.requestbin.services.IBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>
{
    private final IBinService binService;

    @Autowired
    public ApplicationStartup(IBinService binService)
    {
        this.binService = binService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event)
    {
        binService.createNewBin("test-bin");
    }
}
