package de.com.lio.domain;

public class PageCrawler {

    private WikiPagePath wikiPagePath;

    public PageCrawler(WikiPagePath wikiPagePath) {
        this.wikiPagePath = wikiPagePath;
    }
    public WikiPagePath getFullPath(WikiPage suiteSetup) {
        return wikiPagePath;
    }
}
