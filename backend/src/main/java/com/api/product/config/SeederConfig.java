package com.api.product.config;

import com.api.product.entity.UserInfo;
import com.api.product.repository.UserInfoRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeederConfig {
    private final PasswordEncoder passwordEncoder;

    public SeederConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner dataSeeder(UserInfoRepo userRepository) {
        return args -> {
            if (userRepository.count() == 0) { // Prevent duplicate seeding
                UserInfo account = new UserInfo();
                account.setUsername("admin");
                account.setPassword(passwordEncoder.encode("secret"));
                account.setRole("ROLE_ADMIN");
                userRepository.save(account);

                System.out.println("Database seeded successfully!");
            } else {
                System.out.println("Database already seeded!");
            }
        };
    }
}