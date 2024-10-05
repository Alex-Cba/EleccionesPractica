package ar.edu.utn.frc.tup.lc.iv.JpaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
    }

    //Literalmente es usarlo
    @Test
    public void testFindByEmail_UserExists() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        userRepository.save(user);

        Optional<UserEntity> result = userRepository.findByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    public void testFindByEmail_UserDoesNotExist() {
        Optional<UserEntity> result = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByUserName_UserExists() {
        UserEntity user = new UserEntity();
        user.setEmail("another@example.com");
        user.setUsername("anotheruser");
        userRepository.save(user);

        Optional<UserEntity> result = userRepository.findByUserName("anotheruser");
        assertTrue(result.isPresent());
        assertEquals("another@example.com", result.get().getEmail());
    }
}


 */