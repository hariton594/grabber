package ru.ntcvulkan.jmx;

import ru.ntcvulkan.wikipedia.WikipediaGrabber;

public class GrabberJMX implements GrabberJMXMBean {
    private WikipediaGrabber grabber;

    public GrabberJMX(WikipediaGrabber grabber) {
        this.grabber = grabber;
    }

    @Override
    public int getCountArticles() {
        return grabber.getDao().getCountArticles();
    }

    @Override
    public int getMaxDelay() {
        return grabber.getMaxDelay();
    }

    @Override
    public void setMaxDelay(int delay) {
        grabber.setMaxDelay(delay);
    }

    @Override
    public int getGrabLevel() {
        return grabber.getMaxLevel();
    }

    @Override
    public void setGrabLevel(int level) {
        grabber.setMaxLevel(level);
    }
}
