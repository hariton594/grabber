package ru.ntcvulkan.wikipedia;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArtickeGrabberTest {

    @Test
    public void grab() {
        ArticleGrabber grabber = new ArticleGrabber();
        Article article = grabber.grab("https://ru.wikipedia.org/wiki/%D0%9D%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D0%B8");
        assertTrue(article.getRelations().size()>0);
    }
}