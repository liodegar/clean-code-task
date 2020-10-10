package de.com.lio.domain;

import java.util.HashMap;
import java.util.Map;

import static de.com.lio.domain.Constants.HTML_TEMPLATE;
import static de.com.lio.domain.Constants.TEST;

public class PageData {

    private String content;

    private WikiPage wikiPage;

    private Map<String, Object> attributes = new HashMap<>();

    public PageData() {
    }

    public PageData(String content, WikiPage wikiPage) {
        this.content = content;
        this.wikiPage = wikiPage;
    }

    public PageData(String content, WikiPage wikiPage, Map<String, Object> attributes) {
        this.content = content;
        this.wikiPage = wikiPage;
        this.attributes = attributes;
    }

    /**
     * Factory to create a new PageData, based on the given parameter.
     * @param pageData a PageData instance.
     * @return a new fresh PageData instance.
     */
    public static PageData of(PageData pageData) {
        return new PageData(pageData.content, pageData.wikiPage, pageData.attributes);
    }


    public WikiPage getWikiPage() {
        return wikiPage;
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void putAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtml() {
        return String.format(HTML_TEMPLATE, getContent());
    }

    @Override
    public String toString() {
        return "isTestPageData=" + hasAttribute(TEST);
    }
}
