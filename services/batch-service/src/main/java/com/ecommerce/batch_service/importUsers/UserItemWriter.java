package com.ecommerce.batch_service.importUsers;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserItemWriter implements ItemWriter<UserCsv> {

    private final JdbcTemplate jdbcTemplate;

    public UserItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void write(Chunk<? extends UserCsv> chunk) {
        String sql = """
            INSERT INTO users (email, name, created_at)
            VALUES (?, ?, now())
            ON CONFLICT (email) DO NOTHING
            """;

        for (UserCsv user : chunk.getItems()) {
            if (user == null) continue;
            int updated = jdbcTemplate.update(sql, user.getEmail(), user.getName());
            log.info("Inserted? " + updated + " email=" + user.getEmail());
        }
    }

}
