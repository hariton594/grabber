package ru.ntcvulkan.wikipedia;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
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

    public int getCountArticles() {
        return db.size();
    }
}
