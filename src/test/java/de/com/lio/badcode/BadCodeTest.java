package de.com.lio.badcode;

import de.com.lio.domain.PageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit test to ensure the proper functionality of BadCode class.
 * This test case enable us to capture the contract of the original code, in such a way that,
 * we can ensure that the refactored code complies the original contract.
 */
class BadCodeTest {

    @ParameterizedTest(name = "{index} {0} with includeSuiteSetup={1}")
    @MethodSource("de.com.lio.util.TestUtils#testArguments")
    void testableHtml(PageData pageData, boolean includeSuiteSetup, String expectedResult) throws Exception {
        //given parameters from testArguments method

        //when
        String actualResult = BadCode.testableHtml(pageData, includeSuiteSetup);

        //then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}