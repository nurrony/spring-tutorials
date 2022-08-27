package info.nmrony.spring.tutorials.security_rbac.rest.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import info.nmrony.spring.tutorials.security_rbac.configs.security.JwtTokenProvider;
import info.nmrony.spring.tutorials.security_rbac.rest.dtos.AuthRequest;
import info.nmrony.spring.tutorials.security_rbac.rest.dtos.AuthResponse;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {

    public static final AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    private JwtTokenProvider jwtTokenProvider;
    private UserViewMapper userViewMapper;

    @Autowired
    public void setUserViewMapper(UserViewMapper userViewMapper) {
        this.userViewMapper = userViewMapper;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public abstract AuthResponse toAuthResponse(AuthRequest request) throws Exception;

    @AfterMapping
    protected void sendTokenAndUserInfo(AuthRequest request, @MappingTarget AuthResponse response) throws Exception {
        Authentication authentication = jwtTokenProvider.authenticate(request.getUsername(), request.getPassword());
        response.setToken(jwtTokenProvider.generateToken(authentication));
        response.setUser(userViewMapper.toUserViewByUsername(request.getUsername()));
    }
}
