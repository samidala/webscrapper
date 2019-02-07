package com.techdisqus.process;


import com.techdisqus.parser.HtmlParser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;




public class TheHinduWebScrapper {

    public static void main(String[] args) throws IOException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        HtmlParser htmlParser = applicationContext.getBean(HtmlParser.class);
        htmlParser.parse("https://www.thehindu.com/archive/");


    }


}
