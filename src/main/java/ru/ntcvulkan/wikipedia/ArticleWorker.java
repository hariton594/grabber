package ru.ntcvulkan.wikipedia;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class ArticleWorker implements Callable<Article> {
    private WikipediaGrabber grabber;
    private String url;
    private int level;

    public ArticleWorker(WikipediaGrabber grabber, String url, int level) {
        this.grabber = grabber;
        this.url = url;
        this.level = level;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Article call() throws Exception {
        ArticleGrabber grabable = new ArticleGrabber();

        Article article = grabber.getDao().get(this.url);
        if (article!=null)
            return article;

        article = grabable.grab(this.url);

        if (level<grabber.getMaxLevel()) {
            level++;

            ArrayList<Future<Article>> results = new ArrayList();

            for (Article a : article.getRelations()) {
                Future<Article> film = grabber.getExecutor().submit(new ArticleWorker(grabber, a.getUrl(), level));
                results.add(film);
            }

            for (Future<Article> r : results) {
                try {
                    Article a = r.get();
                    a.setText(null);
                    article.getRelations().add(a);
                } catch (InterruptedException e) {
                    log.error("", e);
                } catch (ExecutionException e) {
                    log.error("", e);
                }
            }
        }

        grabber.getDao().save(article);

        return article;
    }
}
