package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

  private final SelenideElement newSpendingBtn = $("a[href='/spending']");
  private final SelenideElement statisticsBlock = $("#stat");
  private final SelenideElement spendingBlock = $("#spendings");
  private final ElementsCollection tableRows = $$("#spendings tbody tr");

  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription))
        .$$("td")
        .get(5)
        .click();
    return new EditSpendingPage();
  }

  public void checkThatTableContains(String spendingDescription) {
    tableRows.find(text(spendingDescription))
        .should(visible);
  }

  public void checkThatPageLoaded() {
    newSpendingBtn.should(exist);
    statisticsBlock.should(exist);
    spendingBlock.should(exist);
  }
}
