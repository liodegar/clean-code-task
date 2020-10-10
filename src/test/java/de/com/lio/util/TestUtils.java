package de.com.lio.util;

import de.com.lio.domain.*;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Utility class with handy methods used for unit testing purposes.
 */
public final class TestUtils {
    /**
     * To avoid instantiation from outside.
     */
    private TestUtils() {
    }

    public static final String CONTENT = "content";

    public static final String PATH_NAME = "pathName";

    public static final WikiPage WIKI_PAGE = new WikiPage(new PageCrawler(new WikiPagePath(PATH_NAME)));

    /**
     * Expected value for a test data page with enabled suite setup.
     */
    public static final String TEST_PAGE_DATA_SUITE_SETUP = "<html>!include -setup .pathName\n" +
            "!include -setup .pathName\n" +
            "content\n" +
            "!include -teardown .pathName\n" +
            "!include -teardown .pathName\n" +
            "</html>";

    /**
     * Expected value for a test data page with disabled suite setup.
     */
    public static final String TEST_PAGE_DATA_NON_SUITE_SETUP = "<html>!include -setup .pathName\n" +
            "content\n" +
            "!include -teardown .pathName\n" +
            "</html>";

    /**
     * Expected value for a non test data page with enabled suite setup.
     */
    public static final String NON_TEST_PAGE_DATA_SUITE_SETUP = "<html>content</html>";

    /**
     * Expected value for a non test data page with disabled suite setup.
     */
    public static final String NON_TEST_PAGE_DATA_NON_SUITE_SETUP = "<html>content</html>";

    /**
     * Defines the execution times of the system under test method.
     */
    public static final int EXECUTION_TIMES = 5;

    public static PageData getTestPageData() {
        PageData pageData = new PageData(CONTENT, WIKI_PAGE);
        pageData.putAttribute(Constants.TEST, Boolean.TRUE);
        return pageData;
    }

    public static PageData getNonTestPageData() {
        return new PageData(CONTENT, WIKI_PAGE);
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(getTestPageData(), true, TEST_PAGE_DATA_SUITE_SETUP),
                Arguments.of(getTestPageData(), false, TEST_PAGE_DATA_NON_SUITE_SETUP),
                Arguments.of(getNonTestPageData(), true, NON_TEST_PAGE_DATA_SUITE_SETUP),
                Arguments.of(getNonTestPageData(), false, NON_TEST_PAGE_DATA_NON_SUITE_SETUP)
        );
    }
}
