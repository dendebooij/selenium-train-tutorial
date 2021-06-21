package searchtutorial.duckduckgo.pageObjects;

import net.serenitybdd.core.pages.PageObject;

import java.util.List;
import java.util.stream.Collectors;

public class DuckDuckGoResultPage extends PageObject{

    private static final String RESULT_TITLE = ".result__title";

    public List<String> getDisplayedResults() {
        return findAll(RESULT_TITLE)
                .stream()
                .map(element -> element.getAttribute("textContent"))
                .collect(Collectors.toList());
    }
}
