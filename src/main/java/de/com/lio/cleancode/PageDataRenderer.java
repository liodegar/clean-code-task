package de.com.lio.cleancode;

import de.com.lio.domain.*;
import de.com.lio.exception.RenderException;

import static de.com.lio.domain.Constants.NEW_LINE;
import static de.com.lio.domain.Constants.TEST;

/**
 * Class that models a page data renderer. The mutator methods of this class (add*) change only the builder attribute.
 */
public class PageDataRenderer {
    private PageData pageData;
    private boolean includeSuiteSetup;
    private StringBuilder builder;

    /**
     * Creates a new instance of PageDataRenderer by defensively copying the PageData parameter to avoid side effects.
     * This is to ensure idempotence and consistency.
     * @param pageData The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     */
    public PageDataRenderer(PageData pageData, boolean includeSuiteSetup) {
        this.includeSuiteSetup = includeSuiteSetup;
        this.pageData = PageData.of(pageData);
    }

    /**
     * Renders the page data HTML. Be aware that this method is idempotent and consistent.
     *
     * @return the page data properly rendered as HTML.
     * @throws RenderException if any error is caught, containing a contextual message and the root exception.
     */
    public String renderHtml() throws RenderException {
        try {
            initializeBuilder();
            addSetupSection(this.pageData, includeSuiteSetup, this.pageData.getWikiPage());
            addPageContent(this.pageData.getContent());
            addTearDownSection(this.pageData, includeSuiteSetup, this.pageData.getWikiPage());
            updatePageContent();
            return pageData.getHtml();
        } catch (Exception e) {
            throw new RenderException("Exception caught while rendering the page data HTML", e);
        }
    }

    /**
     * Initializes the builder with a new StringBuilder instance.
     */
    private void initializeBuilder() {
        this.builder = new StringBuilder();
    }

    /**
     * Updates the page data content.
     */
    private void updatePageContent() {
        pageData.setContent(builder.toString());
    }

    /**
     * Adds the page data content to the builder.
     *
     * @param content the page data content.
     */
    private void addPageContent(String content) {
        builder.append(content);
    }

    /**
     * Adds to the builder the Setup section.
     *
     * @param pageData          The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @param pageDataWikiPage  The page data Wiki page.
     */
    private void addSetupSection(PageData pageData, boolean includeSuiteSetup, WikiPage pageDataWikiPage) {
        if (isTestPageData(pageData)) {
            addSuiteInfo(includeSuiteSetup, pageDataWikiPage, Suite.SETUP);
            addInheritedPageInfo(pageDataWikiPage, Suite.SETUP);
        }
    }

    /**
     * Adds to the builder the TearDown section.
     *
     * @param pageData          The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @param pageDataWikiPage  The page data Wiki page.
     */
    private void addTearDownSection(PageData pageData, boolean includeSuiteSetup, WikiPage pageDataWikiPage) {
        if (isTestPageData(pageData)) {
            builder.append(Suite.TEAR_DOWN.getInitialSeparator());
            addInheritedPageInfo(pageDataWikiPage, Suite.TEAR_DOWN);
            addSuiteInfo(includeSuiteSetup, pageDataWikiPage, Suite.TEAR_DOWN);
        }
    }

    /**
     * Adds to the builder the Wiki page path name.
     *
     * @param message  The suite message.
     * @param pagePath The Wiki page path.
     */
    private void addPathName(String message, WikiPagePath pagePath) {
        builder.append(message)
                .append(PathParser.render(pagePath))
                .append(NEW_LINE);
    }

    /**
     * Adds to the builder the inherited page info.
     *
     * @param pageDataWikiPage The page data Wiki page.
     * @param suite            enum that contains all the suite related attributes.
     * @return a StringBuilder containing the inherited page info if it is present, empty StringBuilder otherwise.
     */
    private void addInheritedPageInfo(WikiPage pageDataWikiPage, Suite suite) {
        WikiPage inheritedPage = PageCrawlerImpl.getInheritedPage(suite.getType(), pageDataWikiPage);
        if (inheritedPage != null) {
            WikiPagePath pagePath = pageDataWikiPage.getPageCrawler().getFullPath(inheritedPage);
            addPathName(suite.getMessage(), pagePath);
        }
    }

    /**
     * Adds to the builder the suite info.
     *
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @param wikiPage          The Wiki page.
     * @param suite             enum that contains all the suite related attributes.
     */
    private void addSuiteInfo(boolean includeSuiteSetup, WikiPage wikiPage, Suite suite) {
        if (includeSuiteSetup) {
            WikiPage inheritedPage = PageCrawlerImpl.getInheritedPage(suite.getName(), wikiPage);
            if (inheritedPage != null) {
                WikiPagePath pagePath = inheritedPage.getPageCrawler().getFullPath(inheritedPage);
                addPathName(suite.getMessage(), pagePath);
            }
        }
    }

    /**
     * Determines if the given page data is a test.
     *
     * @param pageData The page data.
     * @return true if the page data Test attribute is present, false otherwise.
     */
    private boolean isTestPageData(PageData pageData) {
        return pageData.hasAttribute(TEST);
    }
}
