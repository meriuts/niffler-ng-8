package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.AuthAuthorityDao;
import guru.qa.niffler.data.dao.AuthUserDao;
import guru.qa.niffler.data.dao.UdUserDao;
import guru.qa.niffler.data.dao.impl.AuthAuthorityDaoSpringJdbc;
import guru.qa.niffler.data.dao.impl.AuthUserDaoSpringJdbc;
import guru.qa.niffler.data.dao.impl.UdUserDaoSpringJdbc;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.entity.auth.Authority;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.data.repository.AuthUserRepository;
import guru.qa.niffler.data.repository.impl.AuthUserRepositoryJdbc;
import guru.qa.niffler.data.tpl.XaTransactionTemplate;
import guru.qa.niffler.model.UserJson;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;

public class UsersDbClient {
    private static final Config CFG = Config.getInstanceForLocale();
    private static final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final AuthUserRepository authUserRepository = new AuthUserRepositoryJdbc();
    private final UdUserDao udUserDao = new UdUserDaoSpringJdbc();

    private final TransactionTemplate transactionTemplate = new TransactionTemplate(
            new JdbcTransactionManager(Databases.dataSource(CFG.authJdbcUrl()))
    );

    private final XaTransactionTemplate xaTransactionTemplate = new XaTransactionTemplate(
            CFG.authJdbcUrl(),
            CFG.userdataJdbcUrl()
    );

    TransactionTemplate txChainedTemplate = new TransactionTemplate(
            new ChainedTransactionManager(
                    new JdbcTransactionManager(
                            Databases.dataSource(CFG.authJdbcUrl())
                    ),
                    new JdbcTransactionManager(
                            Databases.dataSource(CFG.userdataJdbcUrl())
                    )
            )
    );

    public UserJson createUser(UserJson user) {
        return xaTransactionTemplate.execute(() -> {
                    AuthUserEntity authUser = new AuthUserEntity();
                    authUser.setUsername(user.username());
                    authUser.setPassword(pe.encode("12345"));
                    authUser.setEnabled(true);
                    authUser.setAccountNonExpired(true);
                    authUser.setAccountNonLocked(true);
                    authUser.setCredentialsNonExpired(true);
                    authUser.setAuthorities(
                            Arrays.stream(Authority.values()).map(
                                    e -> {
                                        AuthorityEntity ae = new AuthorityEntity();
                                        ae.setUser(authUser);
                                        ae.setAuthority(e);
                                        return ae;
                                    }
                            ).toList()
                    );
                    authUserRepository.create(authUser);
                    return UserJson.fromEntity(
                            udUserDao.createUser(UserEntity.fromJson(user))
                    );
                }
        );
    }

//    public UserJson txChainedCreateUser(UserJson user) {
//        return txChainedTemplate.execute(status -> {
//                    AuthUserEntity aue = new AuthUserEntity();
//                    aue.setUsername(user.username());
//                    aue.setPassword(pe.encode("12345"));
//                    aue.setEnabled(true);
//                    aue.setAccountNonLocked(true);
//                    aue.setAccountNonExpired(true);
//                    aue.setCredentialsNonExpired(true);
//
//                    AuthUserEntity createdAuthUser = authUserDao.create(aue);
//
//                    AuthorityEntity[] authorityEntities = Arrays.stream(Authority.values()).map(
//                            e -> {
//                                AuthorityEntity ae = new AuthorityEntity();
//                                ae.setUser(createdAuthUser);
//                                ae.setAuthority(e);
//                                return ae;
//                            }
//                    ).toArray(AuthorityEntity[]::new);
//                    authAuthorityDao.create(authorityEntities);
//
//
//                    UserEntity ue = udUserDao.createUser(UserEntity.fromJson(user));
//                    return UserJson.fromEntity(ue);
//                }
//        );
//    }
}
