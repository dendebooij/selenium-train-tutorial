package searchtutorial.duckduckgo.pageObjects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://duckduckgo.com")
public class DuckDuckGoSearchPage extends PageObject {

    @FindBy(id = "search_form_input_homepage")
    WebElementFacade searchField;

    @FindBy(id = "search_button_homepage")
    WebElementFacade searchButton;

    public void enterSearchTerms(String keyword) {
        searchField.type(keyword);
    }

    public void submitSearch() {
        searchButton.click();
    }
}
