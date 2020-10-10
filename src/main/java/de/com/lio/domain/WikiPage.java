package de.com.lio.domain;

public class WikiPage {
    private PageCrawler pageCrawler;

    public WikiPage(PageCrawler pageCrawler) {
        this.pageCrawler = pageCrawler;
    }

    public PageCrawler getPageCrawler() {
        return pageCrawler;
    }
}
