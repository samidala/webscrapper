package com.techdisqus.parser;

import com.techdisqus.dao.ArticleWriteDao;

import com.techdisqus.model.Article;
import com.techdisqus.model.Author;

import com.techdisqus.service.CacheService;
import com.techdisqus.service.AuthorService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class TheHinduHtmlParserImpl implements HtmlParser{

    private static final LocalDate WEB_START_DATE = LocalDate.of(2009,07,16);
    private static final LocalDate PRINT_START_DATE = LocalDate.of(2000,01,01);
    private static final Logger logger = LoggerFactory.getLogger(TheHinduHtmlParserImpl.class);
    private final Set<String> authors = new HashSet<>();
    private final Set<Article> articles = new HashSet<>();
    private final Map<Author,List<Article>> articleMap = new HashMap<>();

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ArticleWriteDao articleWriteDao;

    /**
     * parses given URL and traverse through the articles
     * @param root
     * @throws IOException
     */
    public void parse(String root) throws IOException {

        List<String> urls = generateAllUrls(root+"/web",WEB_START_DATE);

        for(String url : urls) {
            process(url);
        }
        urls = generateAllUrls(root+"/print",PRINT_START_DATE);

        for(String url : urls) {
            process(url);
        }

    }


    /**
     * process URL and process each article and fetches author, article name and description and updates in cache
     * @param url
     * @throws IOException
     */
    private void process(String url) throws IOException {
        System.out.println("process "+url);
        logger.info("processing {}",url);
        Document doc = Jsoup.connect(url).get();
        Elements newsHeadlines = doc.select(".archive-list");


        for (Element headline : newsHeadlines) {

            Elements liElements = headline.children();

            for(Element li : liElements){
                Elements aElements = li.children();
                try {
                    processAnchorElements(aElements);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("error ",e);

                }
            }


        }
    }

    /**
     * process Anchor elements
     * @param aElements
     */

    private void processAnchorElements(Elements aElements) throws IOException {

        for(Element aElement : aElements){
            String url = aElement.absUrl("href");
            Document finalDoc = Jsoup.connect(url).get();
            String author = finalDoc.select(".auth-nm").html();
            String title = finalDoc.select(".title").html();
            if(title.contains("<a")){
                try {
                    title = title.substring(title.indexOf(">" + 1, title.indexOf("</a>")));
                }catch (Exception e){
                    logger.error("error ",e , title);
                }

            }
            String description = finalDoc.select(".intro").html();


            Article article = new Article().setArticleName(title).setDescription(description).setUrl(url);

            //ignore for now
            //cacheService.addArticle(article);
            //cacheService.addAuthor(author);

            //printToConsole(article);
            log(article);

            saveArticleData(article,author);
        }

    }
    private void printToConsole(Article article){
        System.out.println("Author "+article.getAuthor());
        System.out.println("title "+article.getArticleName());
        System.out.println("description "+article.getDescription());
    }
    private void log(Article article){
        logger.info("Author ",article.getAuthor());
        logger.info("title ",article.getArticleName());
        logger.info("description ",article.getDescription());
    }

    /**
     *
     * @param article
     * @param authorName
     */
    private void saveArticleData(Article article, String authorName){

        if(authorName == null || authorName.trim().isEmpty()){
            authorName = "UNKNOWN";
        }
        article.setAuthor(new Author().setAuthorName(authorName.toUpperCase().trim()));
        printToConsole(article);
        articleWriteDao.saveArticle(article);
    }


    /**
     * returns current date
     * @return
     */
    private LocalDate getDate(){
        return LocalDate.now();
    }

    private static final String url = "%s/%s/%s/%s";

    /**
     * generates URLS
     * @param base: bease to be appended
     * @param startDate start date from where the URL should be appended
     * @return
     */
    private List<String> generateAllUrls(String base, LocalDate startDate){

        LocalDate currentDate = getDate();
        List<String> urls = new ArrayList<>();
        long noOfDays =  noOfDays(startDate,currentDate);
        for(int i = 0; i<= noOfDays; i++){
            LocalDate dateProcessing = startDate.plusDays(i);
            urls.add(String.format(url, base, dateProcessing.getYear(), dateProcessing.getMonthValue(), dateProcessing.getDayOfMonth()));
        }
        return urls;
    }


    /**
     * calculates day diff between two dates
     * @param startDate
     * @param endDate
     * @return
     */
    private long noOfDays(LocalDate startDate, LocalDate endDate){
        return ChronoUnit.DAYS.between(startDate,endDate);
    }

}
