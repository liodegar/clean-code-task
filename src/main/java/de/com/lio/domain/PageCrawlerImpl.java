package de.com.lio.domain;

public class PageCrawlerImpl extends PageCrawler {
    public PageCrawlerImpl(WikiPagePath wikiPagePath) {
        super(wikiPagePath);
    }

    public static WikiPage getInheritedPage(String suiteSetupName, WikiPage wikiPage) {
        return new WikiPage(wikiPage.getPageCrawler());
    }
}
