package ru.ntcvulkan.wikipedia;

import lombok.extern.slf4j.Slf4j;
import ru.ntcvulkan.grab.GrabberException;

@Slf4j
public class Application {
    public static void main(String[] args) {
        WikipediaGrabber grabber = new WikipediaGrabber();
        try {
            grabber.grab("https://ru.wikipedia.org/wiki/%D0%9D%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D0%B8");
        } catch (GrabberException gex) {
            log.error(gex.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
