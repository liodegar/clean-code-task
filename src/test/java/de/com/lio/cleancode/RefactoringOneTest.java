package de.com.lio.cleancode;

import de.com.lio.domain.PageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static de.com.lio.util.TestUtils.EXECUTION_TIMES;

/**
 * Unit test to ensure the proper functionality of the RefactoringOne class.
 */
class RefactoringOneTest {
    @ParameterizedTest(name = "{index} {0} with includeSuiteSetup={1}")
    @MethodSource("de.com.lio.util.TestUtils#testArguments")
    void renderHtml(PageData pageData, boolean includeSuiteSetup, String expectedResult) {
        //given parameters from testArguments method

        //when
        String actualResult = null;
        //The loop is only to ensure that there are no side-effects between the different invocations.
        for (int i = 0; i < EXECUTION_TIMES; i++) {
            actualResult = RefactoringOne.renderHtml(pageData, includeSuiteSetup);
        }

        //then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}