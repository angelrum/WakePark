package ru.project.wakepark.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.model.User;
import ru.project.wakepark.repository.CrudUserRepository;

import java.util.Objects;
import static ru.project.wakepark.util.ValidationUtil.*;
import static ru.project.wakepark.util.UserUtil.*;

@Service
public class UserService extends AbstractService<User> implements UserDetailsService {

    private final CrudUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(CrudUserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.getByLogin(login);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException(String.format("User %s is not found", login));

        return new AuthorizedUser(user);
    }

    @Override
    public User create(User user, int companyId, int userId) {
        checkNotNull(user);
        checkNew(user);
        if (Objects.isNull(this.passwordEncoder)) {
            return repository.save(user, companyId, userId);
        }
        return repository.save(prepareToSave(user, this.passwordEncoder), companyId, userId);
    }

    @Override
    public User update(User user, int companyId, int userId) {
        checkNotNull(user);
        return checkNotFoundWithId(repository.save(prepareToSave(user, this.passwordEncoder), companyId, userId), user.id(), companyId);
    }
}
