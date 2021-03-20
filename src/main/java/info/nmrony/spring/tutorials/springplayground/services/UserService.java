
package info.nmrony.spring.tutorials.springplayground.services;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import info.nmrony.spring.tutorials.springplayground.domain.constants.Roles;
import info.nmrony.spring.tutorials.springplayground.domain.entities.Role;
import info.nmrony.spring.tutorials.springplayground.domain.entities.User;
import info.nmrony.spring.tutorials.springplayground.domain.exceptions.ResourceNotFoundException;
import info.nmrony.spring.tutorials.springplayground.repositories.UserRepository;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.CreateUserRequest;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.UpdateUserRequest;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.UserView;
import info.nmrony.spring.tutorials.springplayground.rest.mappers.UserEditMapper;
import info.nmrony.spring.tutorials.springplayground.rest.mappers.UserViewMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEditMapper userEditMapper;
    private final UserViewMapper userViewMapper;
    private final PasswordEncoder passwordEncoder;

    public UserView create(final CreateUserRequest request) throws Exception {
        if (request.getRoles() == null) {
            final Role role = new Role();
            role.setName(Roles.USER);
            request.setRoles(Set.<Role>of(role));
        }

        final User user = userEditMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userViewMapper.toUserView(userRepository.save(user));
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findWithRolesByUsername(final String username) {
        return userRepository.findWithRolesByUsername(username);
    }

    public User findWithRolesByUserNameAndPassword(final String username, final String password) {
        final User user = findWithRolesByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, username));
        return passwordEncoder.matches(password, user.getPassword()) ? user : null;
    }

    public boolean usernameExists(final String username) {
        return findByUsername(username).isPresent();
    }

    @Transactional
    public UserView upsert(final CreateUserRequest request) throws Exception {
        final Optional<User> optionalUser = findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return create(request);
        } else {
            final UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setFirstName(request.getFirstName());
            updateUserRequest.setLastName(request.getLastName());
            return update(optionalUser.get().getId(), updateUserRequest);
        }
    }

    @Transactional
    public UserView update(final Long id, final UpdateUserRequest request) {
        final User user = userRepository.getById(id);
        userEditMapper.update(request, user);
        return userViewMapper.toUserView(userRepository.save(user));
    }

    @Transactional
    public UserView delete(final Long id) {
        User user = userRepository.getById(id);
        user.setUsername(user.getUsername().replace("@", String.format("_%s@", user.getId().toString())));
        user.setEnabled(false);
        user = userRepository.save(user);
        return userViewMapper.toUserView(user);
    }

    public UserView getUser(final Long id) {
        return userViewMapper.toUserView(userRepository.getById(id));
    }

    public Page<UserView> list(final Pageable pageable) {
        return userRepository.findAll(pageable).map(userViewMapper::toUserView);
    }

    // @PostMapping("search")
    // public ListResponse<UserView> search(@RequestBody
    // SearchRequest<SearchUsersQuery> request) {
    // return new ListResponse<>(userService.searchUsers(request.getPage(),
    // request.getQuery()));
    // }
}
