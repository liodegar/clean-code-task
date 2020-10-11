package de.com.lio.cleancode;

import de.com.lio.domain.PageData;
import de.com.lio.exception.RenderException;


/**
 * Solution based by introducing a new PageDataRenderer class that encapsulates the main logic, in conjunction with
 * OOP refactorings like "Introduce Parameter objects", "Extract method", "Move method", etc.
 * In this approach, methods change only the state of its owning object, i.e., PageDataRenderer.
 */
public final class RefactoringTwo {

    /**
     * To avoid instantiation from outside.
     */
    private RefactoringTwo() {
    }

    /**
     * Renders the page data HTML.
     *
     * @param pageData          The page data.
     * @param includeSuiteSetup flag to determine if suite setup must be included.
     * @return the page data properly rendered as HTML.
     */
    public static String renderHtml(PageData pageData, boolean includeSuiteSetup) throws RenderException {
        return new PageDataRenderer(pageData, includeSuiteSetup).renderHtml();
    }
}
