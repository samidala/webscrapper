create table AUTHOR(
 AUTHOR_ID int auto_increment,
 AUTHOR_NAME varchar(100),
 primary key(AUTHOR_ID));

 create table ARTICLE(
 ARTICLE_ID int auto_increment,
 AUTHOR_ID int references AUTHOR.AUTHOR_ID,
 ARTICLE_NAME varchar(1000),
 ARTICLE_DESC varchar(1000),
 primary key(ARTICLE_ID));