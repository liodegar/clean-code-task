package de.com.lio.cleancode;

import de.com.lio.domain.*;
import de.com.lio.exception.RenderException;

import static de.com.lio.domain.Constants.NEW_LINE;
import static de.com.lio.domain.Constants.TEST;

/**
 * Refactoring based on pure functions without any side-effects.
 */
public class RefactoringOne {

    /**
     * Renders the page data HTML.
     *
     * @param pageDataParam          The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @return the page data properly rendered as HTML.
     * @throws RenderException if any error is caught, containing a contextual message and the root exception.
     */
    public static String renderHtml(PageData pageDataParam, boolean includeSuiteSetup) throws RenderException {
        try {
            PageData pageData = PageData.of(pageDataParam);
            StringBuilder builder = new StringBuilder();
            builder.append(getSetupSection(pageData, includeSuiteSetup, pageData.getWikiPage()));
            builder.append(pageData.getContent());
            builder.append(getTearDownSection(pageData, includeSuiteSetup, pageData.getWikiPage()));
            pageData.setContent(builder.toString());
            return pageData.getHtml();
        } catch (Exception e) {
            throw new RenderException("Exception caught while rendering the page data HTML", e);
        }
    }

    /**
     * Gets the Setup section.
     *
     * @param pageData          The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @param pageDataWikiPage  The page data Wiki page.
     * @return a StringBuilder containing the setup section if the given page data is a test, empty StringBuilder otherwise.
     */
    private static StringBuilder getSetupSection(PageData pageData, boolean includeSuiteSetup, WikiPage pageDataWikiPage) {
        StringBuilder result = new StringBuilder();
        if (isTestPageData(pageData)) {
            result.append(getSuiteInfo(includeSuiteSetup, pageDataWikiPage, Suite.SETUP));
            result.append(getInheritedPageInfo(pageDataWikiPage, Suite.SETUP));
        }
        return result;
    }

    /**
     * Gets the TearDown section.
     *
     * @param pageData          The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @param pageDataWikiPage  The page data Wiki page.
     * @return a StringBuilder containing the TearDown section if the given page data is a test, empty StringBuilder otherwise.
     */
    private static StringBuilder getTearDownSection(PageData pageData, boolean includeSuiteSetup, WikiPage pageDataWikiPage) {
        StringBuilder result = new StringBuilder();
        if (isTestPageData(pageData)) {
            result.append(Suite.TEAR_DOWN.getInitialSeparator());
            result.append(getInheritedPageInfo(pageDataWikiPage, Suite.TEAR_DOWN));
            result.append(getSuiteInfo(includeSuiteSetup, pageDataWikiPage, Suite.TEAR_DOWN));
        }
        return result;
    }

    /**
     * Gets the Wiki page path name.
     *
     * @param message  The suite message.
     * @param pagePath The Wiki page path.
     * @return a StringBuilder containing the Wiki page path name.
     */
    private static StringBuilder getPathName(String message, WikiPagePath pagePath) {
        return new StringBuilder(message)
                .append(PathParser.render(pagePath))
                .append(NEW_LINE);
    }

    /**
     * Gets the inherited page info.
     *
     * @param pageDataWikiPage The page data Wiki page.
     * @param suite            enum that contains all the suite related attributes.
     * @return a StringBuilder containing the inherited page info if it is present, empty StringBuilder otherwise.
     */
    private static StringBuilder getInheritedPageInfo(WikiPage pageDataWikiPage, Suite suite) {
        StringBuilder result = new StringBuilder();
        WikiPage inheritedPage = PageCrawlerImpl.getInheritedPage(suite.getType(), pageDataWikiPage);
        if (inheritedPage != null) {
            WikiPagePath pagePath = pageDataWikiPage.getPageCrawler().getFullPath(inheritedPage);
            result.append(getPathName(suite.getMessage(), pagePath));
        }
        return result;
    }

    /**
     * Gets the suite info.
     *
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @param wikiPage          The Wiki page.
     * @param suite             enum that contains all the suite related attributes.
     * @return a StringBuilder containing the suite info if the given flag is true and the inherited page is present,
     * empty StringBuilder otherwise.
     */
    private static StringBuilder getSuiteInfo(boolean includeSuiteSetup, WikiPage wikiPage, Suite suite) {
        StringBuilder result = new StringBuilder();
        if (includeSuiteSetup) {
            WikiPage inheritedPage = PageCrawlerImpl.getInheritedPage(suite.getName(), wikiPage);
            if (inheritedPage != null) {
                WikiPagePath pagePath = inheritedPage.getPageCrawler().getFullPath(inheritedPage);
                result.append(getPathName(suite.getMessage(), pagePath));
            }
        }
        return result;
    }

    /**
     * Determines if the given page data is a test.
     *
     * @param pageData The page data.
     * @return true if the page data Test attribute is present, false otherwise.
     */
    private static boolean isTestPageData(PageData pageData) {
        return pageData.hasAttribute(TEST);
    }
}
