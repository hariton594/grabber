package ru.ntcvulkan.jmx;

public interface GrabberJMXMBean {
    int getCountArticles();

    int getMaxDelay();

    void setMaxDelay(int delay);

    int getGrabLevel();

    void setGrabLevel(int level);

}
