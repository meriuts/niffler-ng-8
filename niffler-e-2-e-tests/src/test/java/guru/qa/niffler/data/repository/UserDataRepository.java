package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository {

    UserEntity createUser(UserEntity user);

    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findByUsername(String username);

    void addFriend(UserEntity requester, UserEntity addressee);

    void addFriendshipRequest(UserEntity requester, UserEntity addressee);
}
