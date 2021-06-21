package searchtutorial.duckduckgo;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import searchtutorial.duckduckgo.steps.DuckDuckGoSearcher;

@RunWith(SerenityRunner.class)
public class WhenSearchingByKeyWordInDuckDuckGo {

    @Steps
    DuckDuckGoSearcher sam;

    @Test
    public void should_see_relevant_search_results() {

        sam.searches_by_keyword_for("Cucumber Recipes");

        sam.should_see_only_results_containing("Cucumber");


    }
}
