package com.fortune.app.user.repository.integration;

import com.fortune.app.user.dto.UserDto;
import com.fortune.app.user.dto.UserRequestDto;
import com.fortune.app.user.entity.User;
import com.fortune.app.user.repository.UserRepository;
import com.fortune.app.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    private UserDto savedUserDto;

    @BeforeEach
    void setUp() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("사용자")
                .birth(new Date(2025, 1, 1))
                .nickname("testUser")
                .email("testEmail@test.com")
                .provider("kakao")
                .providerUid("1234567890")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        savedUserDto = userService.signUp(userRequestDto);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testFindUserByUserId() {
        Optional<User> foundUser = userRepository.findByUserId(savedUserDto.getUserId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(savedUserDto.getEmail());
        assertThat(foundUser.get().getName()).isEqualTo(savedUserDto.getName());
        assertThat(foundUser.get().getBirth()).isEqualTo(savedUserDto.getBirth());
        assertThat(foundUser.get().getNickname()).isEqualTo(savedUserDto.getNickname());
    }
}
