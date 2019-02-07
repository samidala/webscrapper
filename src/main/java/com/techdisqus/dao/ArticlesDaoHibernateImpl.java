package com.techdisqus.dao;


import com.techdisqus.model.Article;
import com.techdisqus.model.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;


import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

/**
 * DAO for queryinf and Persisting articles, authors
 */

@Component
public class ArticlesDaoHibernateImpl   implements ArticlesDao, ArticleWriteDao,AuthorDao, InitializingBean {

    /**
     * refer {@link ArticleWriteDao#saveArticle(Article)}
     * @param article
     * @return
     */
    public Article saveArticle(Article article){
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {


            Author author = session.createQuery("from Author where authorName = :authorName ",Author.class)
                    .setParameter("authorName", article.getAuthor().getAuthorName()).uniqueResult();

            //not working correctly, need to fix it.
            Statistics statistics1 = getSession().getSessionFactory().getStatistics();
            System.out.println("***********************second level cache hit count "+ statistics1.getSecondLevelCacheHitCount());
            getSession().getSessionFactory().getCurrentSession();
            if(author == null){
                session.save(article.getAuthor());
            }else{
                article.setAuthor(author);
            }

            session.save(article);
            session.flush();
            transaction.commit();

        }catch (Exception e){
            if(transaction != null && transaction.isActive())
                transaction.rollback();
        }
        finally {
            session.close();
        }
        return article;
    }




    /**
     * refer {@link AuthorDao#getAllAuthors()}
     * @return
     */
    public List<Author> getAllAuthors(){
        Session  session= getSession();
        try {
            List<Author>  authors = session.createQuery("from Author",Author.class).getResultList();
            return authors;
        }finally {
            session.close();
        }

    }

    /**
     * Fetches all the author with partial match
     * @param author
     * @return
     */
    public List<Author> searchByAuthorName(String author){
        Session  session= getSession();
        List<Author> authors;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Author> authorCriteriaQuery = criteriaBuilder.createQuery(Author.class);
            Root<Author> authorRoot = authorCriteriaQuery.from(Author.class);
            authorCriteriaQuery.select(authorRoot).where(criteriaBuilder.like(authorRoot.get("authorName"), "%" + author + "%"));
            authors = session.createQuery(authorCriteriaQuery).getResultList();
            return authors;
        }finally {
            session.close();
        }

    }

    /**
     * Fetch Author with exact match
     * @param authorName
     * @return
     */
    public Author getAuthorByName(String authorName){
        Session  session= getSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Author> authorCriteriaQuery = criteriaBuilder.createQuery(Author.class);
            Root<Author> authorRoot = authorCriteriaQuery.from(Author.class);
            authorCriteriaQuery.select(authorRoot).where(criteriaBuilder.equal(authorRoot.get("authorName"), authorName));

            Author author = session.createQuery(authorCriteriaQuery).uniqueResult();
            return author;
        }finally {
            session.close();
        }

    }

    /**
     * fetches all the articles from author
     * @param authorName
     * @return
     */
    public List<Article> getArticlesByAuthor(String authorName){

        Session  session= getSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Article> articleCriteriaQuery = criteriaBuilder.createQuery(Article.class);
            Root<Article> articleRoot = articleCriteriaQuery.from(Article.class);

            articleRoot.fetch("author", JoinType.INNER);

            articleCriteriaQuery.select(articleRoot).where(criteriaBuilder.equal(articleRoot.get("author").get("authorName"),
                    authorName));

            List<Article> articles = session.createQuery(articleCriteriaQuery).getResultList();
            return articles;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Article> getArticlesByAuthorById(int id) {
        Session  session= getSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Article> articleCriteriaQuery = criteriaBuilder.createQuery(Article.class);
            Root<Article> articleRoot = articleCriteriaQuery.from(Article.class);

            articleRoot.fetch("author", JoinType.INNER);

            articleCriteriaQuery.select(articleRoot).where(criteriaBuilder.equal(articleRoot.get("author").get("id"),
                    id));

            List<Article> articles = session.createQuery(articleCriteriaQuery).getResultList();
            return articles;
        }finally {
            session.close();
        }
    }

    /**
     * Search articles by title and description. it is partial search
     * @param articleName
     * @param articleDesc
     * @return
     */
    public List<Article> searchByArticleNameOrDesc(String articleName, String articleDesc){
        Session  session= getSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Article> articleCriteriaQuery = criteriaBuilder.createQuery(Article.class);
            Root<Article> authorRoot = articleCriteriaQuery.from(Article.class);

            if (articleName != null && !articleName.isEmpty() && articleDesc != null && !articleDesc.isEmpty()) {
                Predicate predicateArticleName = criteriaBuilder.like(authorRoot.get("articleName"), "%" + articleName + "%");
                Predicate predicateArticleDesc = criteriaBuilder.like(authorRoot.get("description"), "%" + articleDesc + "%");
                articleCriteriaQuery.select(authorRoot).where(criteriaBuilder.or(predicateArticleDesc, predicateArticleName));
            } else if (articleName != null && !articleName.isEmpty()) {
                articleCriteriaQuery.select(authorRoot).where(criteriaBuilder.like(authorRoot.get("articleName"), "%" + articleName + "%"));
            } else {
                articleCriteriaQuery.select(authorRoot).where(criteriaBuilder.like(authorRoot.get("description"), "%" + articleDesc + "%"));
            }


            List<Article> articles = session.createQuery(articleCriteriaQuery).getResultList();
            return articles;
        }finally {
            session.close();
        }


    }


    /**
     * returns session
     * @return
     */

    protected Session getSession(){
        return HibernateUtil.getSessionFactory().openSession();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        HibernateUtil.buildSessionFactory();
    }
}
