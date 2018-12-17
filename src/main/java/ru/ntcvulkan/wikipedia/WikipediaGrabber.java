package ru.ntcvulkan.wikipedia;

import lombok.extern.slf4j.Slf4j;
import ru.ntcvulkan.grab.GrabberException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class WikipediaGrabber {
    private ExecutorService executor;
    private int maxLevel = 4;
    private int maxDelay = 2;
    private int countThreads = 10;//Integer.MAX_VALUE;
    private WikipediaDAO dao = new WikipediaDAO();

    public void grab(String url) throws GrabberException {
        init();

        try {
            Future<Article> future = executor.submit(new ArticleWorker(this, url));
            Article article = future.get();
            grab(article, 0);

        } catch (InterruptedException ie) {
            log.error("", ie);

        } catch (ExecutionException ee) {
            log.error("", ee);
        }

        executor.shutdown();
    }

    private void grab(Article parent, int currentLevel) throws InterruptedException, ExecutionException {
        if (currentLevel<getMaxLevel()) {
            currentLevel++;
            Collection<Future<Article>> results = new LinkedList<>();
            for(Article r : parent.getRelations()) {
                results.add(executor.submit(new ArticleWorker(this, r.getUrl())));
            }

            for(Future<Article> f : results) {
                grab(f.get(), currentLevel);
            }
        }
    }

    private void init() {
        executor = Executors.newFixedThreadPool(countThreads);

        log.debug("countThreads: {}", this.countThreads);
        log.debug("maxDelay: {}", this.maxDelay);
        log.debug("maxLevel: {}", this.maxLevel);
    }

    public WikipediaDAO getDao() {
        return dao;
    }

    public void setCountThreads(int countThreads) {
        this.countThreads = countThreads;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
