package ru.ntcvulkan.monitoring.service.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ntcvulkan.monitoring.service.WikipediaInfoService;

@Component
@RestControllerEndpoint(id = "rest-grabber-info")
public class RestGrabberInfoEndpoint {

    @Autowired
    WikipediaInfoService wikipediaInfoService;

    @GetMapping("/grabbered")
    public @ResponseBody ResponseEntity grabbered() {
        return  new ResponseEntity<>(wikipediaInfoService.status().getGrabbered(), HttpStatus.OK);
    }

    @GetMapping("/threads")
    public @ResponseBody ResponseEntity threads() {
        return  new ResponseEntity<>(wikipediaInfoService.status().getThreads(), HttpStatus.OK);
    }
}
