package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement saveChangesBtn = $("//button[text()='Sign in']");
    private final SelenideElement showArchivedToggle = $("input[type='checkbox']");
    private final SelenideElement newCategoryInput = $("input[name='category']");
    private final SelenideElement archivedToggle = $(".MuiSwitch-input");


    public ProfilePage archivedCategory(String categoryName) {
        SelenideElement categoryBox = $(byText(categoryName)).ancestor("div").parent();
        SelenideElement archiveBtn = categoryBox.find("[aria-label='Archive category']");
        archiveBtn.click();

        SelenideElement confirmBtn = $(byText("Archive category")).parent().find(byText("Archive"));
        confirmBtn.click();
        return this;
    }

    public void checkArchivedCategoryExists(String categoryName) {
        archivedToggle.click();
        $(byText(categoryName)).shouldBe(visible);
    }

    public void checkActiveCategoryExists(String categoryName) {
        $(byText(categoryName)).shouldBe(visible);
    }
}
