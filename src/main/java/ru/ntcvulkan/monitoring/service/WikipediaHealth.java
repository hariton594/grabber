package ru.ntcvulkan.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WikipediaHealth extends SocketHealthChecking {

    protected static Logger logger = LoggerFactory.getLogger(WikipediaHealth.class);

    @Autowired
    public WikipediaHealth() {
        host = "ru.wikipedia.org";
        port = 443;
    }

}
