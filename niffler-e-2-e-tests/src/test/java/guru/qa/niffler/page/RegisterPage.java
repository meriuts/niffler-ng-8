package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement usernameErrorMessage = usernameInput.parent().$(".form__error");

    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement passwordErrorMessage = passwordInput.parent().$(".form__error");

    private final SelenideElement passwordSubmitInput = $("input[name='passwordSubmit']");
    private final SelenideElement submitBtn = $("button[type='submit']");
    private final SelenideElement errorContainer = $(".form__error");

    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        passwordSubmitInput.setValue(password);
        return this;
    }

    public RegisterPage clickSubmit() {
        submitBtn.click();
        return this;
    }

    public SuccessRegisterPage doRegister(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        passwordSubmitInput.setValue(password);
        submitBtn.click();
        return new SuccessRegisterPage();
    }

    public RegisterPage doInvalidRegister(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        passwordSubmitInput.setValue(password);
        submitBtn.click();
        return this;
    }

    public RegisterPage checkThatPageLoaded() {
        usernameInput.should(exist);
        passwordInput.should(exist);
        passwordSubmitInput.should(exist);
        submitBtn.should(exist);
        return this;
    }

    public void checkRegisterError(String errorMessage) {
        errorContainer.shouldHave(text(errorMessage));
    }
}
