package ru.ntcvulkan.monitoring.service;

import org.springframework.stereotype.Service;

@Service
public class WikipediaInfoImpl implements WikipediaInfoService {
    @Override
    public WikipediaStatus status() {
        WikipediaStatus status = new WikipediaStatus();
        status.setGrabbered(13456);
        status.setThreads(5);
        return status;
    }
}
