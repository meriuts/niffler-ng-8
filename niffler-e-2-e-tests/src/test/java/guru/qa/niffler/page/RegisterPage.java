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

    public void checkThatPageLoaded() {
        usernameInput.should(exist);
        passwordInput.should(exist);
        passwordSubmitInput.should(exist);
        submitBtn.should(exist);
    }

    public String getUsernameErrorMessage() {
        return usernameErrorMessage.text();
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage.text();
    }

}
