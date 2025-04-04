package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitBtn = $("button[type='submit']");
  private final SelenideElement createNewAccountBtn = $("a[href='/register']");
  private final SelenideElement errorMessage = $("p[class='form__error']");

  public MainPage doLogin(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitBtn.click();
    return new MainPage();
  }

  public RegisterPage navigateToRegister() {
    createNewAccountBtn.click();
    return new RegisterPage();
  }

  public void checkInvalidLoginMessage() {
    errorMessage
            .should(exist)
            .shouldHave(text("Неверные учетные данные пользователя"));
  }

  public void checkThatPageLoaded() {
    usernameInput.should(exist);
    passwordInput.should(exist);
    submitBtn.should(exist);
    createNewAccountBtn.should(exist);
  }
}
