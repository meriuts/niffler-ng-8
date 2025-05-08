package guru.qa.niffler.service;

import guru.qa.niffler.model.UserJson;

public interface UsersClient {
    public UserJson createUser(UserJson user);
    public UserJson createUser(String username, String password);
    public void addFriend(UserJson user, int count);
}
