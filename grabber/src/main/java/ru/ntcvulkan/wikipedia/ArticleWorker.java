package ru.ntcvulkan.wikipedia;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ArticleWorker implements Callable<Article> {
    private WikipediaGrabber grabber;
    private String url;

    public ArticleWorker(WikipediaGrabber grabber, String url) {
        this.grabber = grabber;
        this.url = url;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Article call() throws Exception {
        Thread.sleep(ThreadLocalRandom.current().nextInt(0, grabber.getMaxDelay() + 1)*1000);

        ArticleGrabber grabable = new ArticleGrabber();

        Article article = grabber.getDao().get(this.url);
        if (article!=null)
            return article;

        article = grabable.grab(this.url);

        grabber.getDao().save(article);

        return article;
    }
}
