package ru.ntcvulkan.wikipedia;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
    @EqualsAndHashCode.Include
    private String url;
    private String text;
    private String caption;
    private Set<Article> relations = new HashSet<>();
}
