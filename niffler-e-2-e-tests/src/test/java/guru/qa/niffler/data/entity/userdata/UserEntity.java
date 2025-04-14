package guru.qa.niffler.data.userdata;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.UserJson;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
public class UserEntity {

    private UUID id;
    private String username;
    private CurrencyValues currency;
    private String firstname;
    private String surname;
    private String fullname;
    private byte[] photo;
    private byte[] photoSmall;

    public static UserEntity fromJson(UserJson json) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId( json.id());
        userEntity.setUsername(json.username());
        userEntity.setCurrency(json.currency());
        userEntity.setFirstname(json.firstname());
        userEntity.setSurname(json.surname());
        userEntity.setFullname(json.fullname());
        userEntity.setPhoto(json.photo() != null ? json.photo().getBytes(StandardCharsets.UTF_8) : null);
        userEntity.setPhotoSmall(json.photoSmall() != null ? json.photoSmall().getBytes(StandardCharsets.UTF_8) : null);

        return userEntity;
    }
}


