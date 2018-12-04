package ru.ntcvulkan.wikipedia;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WikipediaDAO {
    private Map<String, Article> db = new ConcurrentHashMap<>();

    public Article get(String url) {
        return db.get(url);
    }

    public void save(Article article) {
        if (article==null || article.getUrl()==null)
            return;

        db.put(article.getUrl(), article);
    }
}
