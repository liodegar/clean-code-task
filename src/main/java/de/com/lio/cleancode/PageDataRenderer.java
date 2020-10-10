package de.com.lio.cleancode;

import de.com.lio.domain.*;
import de.com.lio.exception.RenderException;

import static de.com.lio.domain.Constants.NEW_LINE;
import static de.com.lio.domain.Constants.TEST;

/**
 * Class that models a page data renderer. The add* methods of this class change only the builder attribute.
 */
public class PageDataRenderer {
    private PageData pageData;
    private boolean includeSuiteSetup;
    private StringBuilder builder;

    public PageDataRenderer(boolean includeSuiteSetup) {
        this.includeSuiteSetup = includeSuiteSetup;
    }

    /**
     * Renders the page data HTML. Be aware that this method is idempotent and consistent.
     * No side effects expected on the given parameter as it is defensively copied.
     *
     * @param pageDataParam The Page data.
     * @return the page data properly rendered as HTML.
     * @throws RenderException if any error is caught, containing a contextual message and the root exception.
     */
    public String renderHtml(PageData pageDataParam) throws RenderException {
        try {
            initAttributes(pageDataParam);
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
     * Initializes the builder with a new StringBuilder instance and the PageData attribute
     * with a defensive copy of the given parameter. This is to ensure idempotence and consistency.
     *
     * @param pageData The page data.
     */
    private void initAttributes(PageData pageData) {
        this.builder = new StringBuilder();
        this.pageData = PageData.of(pageData);
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
