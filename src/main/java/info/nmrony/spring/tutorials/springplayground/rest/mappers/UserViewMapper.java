package info.nmrony.spring.tutorials.springplayground.rest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import info.nmrony.spring.tutorials.springplayground.domain.entities.User;
import info.nmrony.spring.tutorials.springplayground.domain.exceptions.ResourceNotFoundException;
import info.nmrony.spring.tutorials.springplayground.repositories.UserRepository;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.UserView;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    private UserRepository userRepo;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
      this.userRepo = userRepo;
    }

    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);

    public UserView toUserViewById(Long id) {
        if (id == null) return null;
        return toUserView(userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(User.class, id)));
    }

    public UserView toUserViewByUsername(String username) {
        if (username == null) return null;
        return toUserView(userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(User.class, username)));
    }

}
