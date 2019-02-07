package com.techdisqus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE")
@Cache(region = "article",usage = CacheConcurrencyStrategy.READ_ONLY)
public class Article {

    @Id
    @Column(name = "ARTICLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;

    @Column(name = "ARTICLE_NAME")
    private String articleName;

    @Column(name = "ARTICLE_DESC")
    private String description;

    @Column(name = "URL")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID",referencedColumnName = "AUTHOR_ID")
    @JsonIgnore
    private Author author;

    public String getArticleName() {
        return articleName;
    }

    public Article setArticleName(String articleName) {
        this.articleName = articleName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Article setDescription(String description) {
        this.description = description;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Article setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public int getArticleId() {
        return articleId;
    }

    public Article setArticleId(int articleId) {
        this.articleId = articleId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return articleId == article.articleId;
    }

    public String getUrl() {
        return url;
    }

    public Article setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public int hashCode() {
        return articleId;
    }
}
