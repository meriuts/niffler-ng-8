package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension.StaticUser;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.Type.*;

@WebTest
public class FriendsTest {

    private final static String LOGIN_URL = Config.getInstanceForLocale().loginPageUrl();
    private static final String FRIEND_URL =  Config.getInstanceForLocale().friendPageUrl();
    private static final String ALL_PEOPLE_URL =  Config.getInstanceForLocale().allPeoplePageUrl();

    @Test
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin(user.username(), user.password())
                .checkThatPageLoaded();

        List<String> expectedFriend = List.of(user.friend());

        Selenide.open(FRIEND_URL, FriendsPage.class)
                .checkThatUserHasFriends(expectedFriend);
    }

    @Test
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin(user.username(), user.password())
                .checkThatPageLoaded();

        Selenide.open(FRIEND_URL, FriendsPage.class)
                .checkThatFriendsNotExist();
    }

    @Test
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin(user.username(), user.password())
                .checkThatPageLoaded();

        List<String> expectedIncomeFriend = List.of(user.income());

        Selenide.open(FRIEND_URL, FriendsPage.class)
                .checkThatUserHasIncomeFriend(expectedIncomeFriend);
    }

    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin(user.username(), user.password())
                .checkThatPageLoaded();

        Selenide.open(ALL_PEOPLE_URL, AllPeoplePage.class)
                .checkInvitationSentToUser(user.outcome());
    }
}
