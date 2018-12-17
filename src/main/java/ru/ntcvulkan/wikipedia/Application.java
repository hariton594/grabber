package ru.ntcvulkan.wikipedia;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import ru.ntcvulkan.grab.GrabberException;
import ru.ntcvulkan.jmx.GrabberJMX;
import ru.ntcvulkan.jmx.GrabberJMXMBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        options.addOption(Option.builder("h")
                .argName("help")
                .longOpt("help")
                .desc("Выводит это сообщение")
                .required(false)
                .build());

        options.addOption(Option.builder("u")
                .argName("url")
                .longOpt("url")
                .hasArg()
                .desc("Адрес начальной страницы википедии")
                .required(false)
                .build());

        options.addOption(Option.builder("l")
                .argName("level")
                .longOpt("level")
                .hasArg()
                .desc("Уровень разбора")
                .required(false)
                .build());

        options.addOption(Option.builder("w")
                .argName("workers")
                .longOpt("workers")
                .hasArg()
                .desc("Количество потоков сбора")
                .required(false)
                .hasArg()
                .build());

        options.addOption(Option.builder("d")
                .argName("delay")
                .longOpt("delay")
                .hasArg()
                .desc("Максимальная пауза перед загрузкой статьи")
                .required(false)
                .build());

        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            log.error("Ошибка при разборе параметров: {}", exp.getMessage());
        }

        WikipediaGrabber grabber = new WikipediaGrabber();
        String url = "https://ru.wikipedia.org/wiki/%D0%9D%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D0%B8";
        if (line != null) {
            try {
                if (line.hasOption("url"))
                    url = line.getOptionValue("url");
                if (line.hasOption("workers"))
                    grabber.setCountThreads(Integer.parseInt(line.getOptionValue("workers")));
                if (line.hasOption("level"))
                    grabber.setMaxLevel(Integer.parseInt(line.getOptionValue("level")));
                if (line.hasOption("delay"))
                    grabber.setMaxDelay(Integer.parseInt(line.getOptionValue("delay")));
                if (line.hasOption("help")) {
                    HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("java -jar grabber.jar", options);
                    System.exit(0);
                }
            } catch (Exception ex) {
                log.error("Ошибка разбоа параметров", ex);
            }
        }

        GrabberJMXMBean grabberMBean = new GrabberJMX(grabber);

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(grabberMBean, new ObjectName("grabber:name=wikipedia"));


        try {
            grabber.grab(url);
        } catch (GrabberException gex) {
            log.error(gex.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
