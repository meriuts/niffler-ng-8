package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SuccessRegisterPage {

    private final SelenideElement successMessage = $(".form__paragraph.form__paragraph_success");
    private final SelenideElement toLoginLink = $("//a[text()='Sign in']");

    public LoginPage navigateToLogin() {
        toLoginLink.click();
        return new LoginPage();
    }

    public void checkSuccessMessage() {
        successMessage
                .should(exist)
                .shouldHave(text("Congratulations! You've registered!"));
    }

}
