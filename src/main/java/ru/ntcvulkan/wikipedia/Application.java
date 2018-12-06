package ru.ntcvulkan.wikipedia;

import lombok.extern.slf4j.Slf4j;
import ru.ntcvulkan.grab.GrabberException;
import ru.ntcvulkan.jmx.GrabberJMX;
import ru.ntcvulkan.jmx.GrabberJMXMBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {
        WikipediaGrabber grabber = new WikipediaGrabber();

        GrabberJMXMBean grabberMBean = new GrabberJMX(grabber);

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(grabberMBean, new ObjectName("grabber:name=wikipedia"));


        try {
            grabber.grab("https://ru.wikipedia.org/wiki/%D0%9D%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D0%B8");
        } catch (GrabberException gex) {
            log.error(gex.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
