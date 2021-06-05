package com.sixshop.sixspace.config;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@RequiredArgsConstructor
@Component
public class UserCreater {

    private final UserCreateService userCreateService;

    @PostConstruct
    public void init() {
        userCreateService.create();
    }

    @RequiredArgsConstructor
    @Transactional
    @Service
    static class UserCreateService {

        private final UserRepository userRepository;

        public void create() {
            List<UserCreateRequest> dtos = createDto();

            for (UserCreateRequest dto : dtos) {
                userRepository.save(User.of(dto));
            }
        }

        private List<UserCreateRequest> createDto() {

            List<UserCreateRequest> result = new ArrayList<>();

            UserCreateRequest hongjun = new UserCreateRequest();
            hongjun.setId("hongjun");
            hongjun.setPassword("1");
            hongjun.setEmail("hongjun@sixshop.com");
            hongjun.setName("홍준");
            hongjun.setNickName("홍준");
            result.add(hongjun);

            UserCreateRequest ohtaeg = new UserCreateRequest();
            ohtaeg.setId("ohtaeg");
            ohtaeg.setPassword("1");
            ohtaeg.setEmail("ohtaeg@sixshop.com");
            ohtaeg.setName("태경");
            ohtaeg.setNickName("태경");
            result.add(ohtaeg);

            UserCreateRequest nashil = new UserCreateRequest();
            nashil.setId("nashil");
            nashil.setPassword("1");
            nashil.setEmail("nashil@sixshop.com");
            nashil.setName("나실");
            nashil.setNickName("나실");
            result.add(nashil);

            UserCreateRequest hongkeun = new UserCreateRequest();
            hongkeun.setId("hongkeun");
            hongkeun.setPassword("1");
            hongkeun.setEmail("hongkeun@sixshop.com");
            hongkeun.setName("홍근");
            hongkeun.setNickName("홍근");
            result.add(hongkeun);

            UserCreateRequest jeongbin = new UserCreateRequest();
            jeongbin.setId("jeongbin");
            jeongbin.setPassword("1");
            jeongbin.setEmail("jeongbin@sixshop.com");
            jeongbin.setName("정빈");
            jeongbin.setNickName("정빈");
            result.add(jeongbin);

            UserCreateRequest dahye = new UserCreateRequest();
            dahye.setId("dahye");
            dahye.setPassword("1");
            dahye.setEmail("dahye@sixshop.com");
            dahye.setName("다혜");
            dahye.setNickName("다혜");
            result.add(dahye);

            return result;
        }

    }

}

