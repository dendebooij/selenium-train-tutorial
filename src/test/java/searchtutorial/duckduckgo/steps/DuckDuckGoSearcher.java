package searchtutorial.duckduckgo.steps;

import net.thucydides.core.annotations.Step;
import searchtutorial.duckduckgo.pageObjects.DuckDuckGoResultPage;
import searchtutorial.duckduckgo.pageObjects.DuckDuckGoSearchPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DuckDuckGoSearcher {

    DuckDuckGoSearchPage searchPage;
    DuckDuckGoResultPage resultPage;

    @Step
    public void searches_by_keyword_for(String keyword) {
        searchPage.enterSearchTerms(keyword);
        searchPage.submitSearch();
    }


    @Step
    public void should_see_only_results_containing(String expectedTerms) {
        List<String> results = resultPage.getDisplayedResults();

        assertThat(results, everyItem(containsString(expectedTerms)));
    }
}

