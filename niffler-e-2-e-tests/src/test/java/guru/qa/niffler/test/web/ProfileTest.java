package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.*;

@WebTest
public class ProfileTest {

    private final static String LOGIN_URL = Config.getInstanceForLocale().loginPageUrl();
    private final static String PROFILE_URL = Config.getInstanceForLocale().profilePageUrl();

    @BeforeEach
    void login() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin("test", "test")
                .checkThatPageLoaded();
    }

    @Test
    @User(
            username = "test",
            categories = @Category(archived = true)
    )
    void archivedCategoryShouldPresentInCategoryList(CategoryJson category) {
        Selenide.open(PROFILE_URL, ProfilePage.class)
                .checkArchivedCategoryExists(category.name());
    }

    @Test
    @User(
            username = "test",
            categories = @Category(archived = false)
    )
    void activeCategoryShouldPresentInCategoryList(CategoryJson category) {
        Selenide.open(PROFILE_URL, ProfilePage.class)
                .checkActiveCategoryExists(category.name());
    }
}
