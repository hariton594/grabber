package ru.ntcvulkan.monitoring.service.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.ntcvulkan.monitoring.service.WikipediaInfoService;
import ru.ntcvulkan.monitoring.service.WikipediaStatus;

@Endpoint(id="grabber-status")
@Component
public class GrabberStatusEndpoint {

    @Autowired
    WikipediaInfoService wikipediaInfoService;

    @ReadOperation
    public WikipediaStatus status() {
        return wikipediaInfoService.status();
    }
}
