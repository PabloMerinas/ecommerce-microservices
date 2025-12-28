package com.ecommerce.batch_service.importUsers;

import org.springframework.batch.infrastructure.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<UserCsv, UserCsv> {

    @Override
    public UserCsv process(UserCsv item) {
        if (item == null) return null;

        String email = item.getEmail() != null ? item.getEmail().trim().toLowerCase() : null;
        String name = item.getName() != null ? item.getName().trim() : null;

        if(email == null || email.isBlank()) return null;
        if(name == null || name.isBlank()) return null;

        UserCsv cleaned = new UserCsv();
        cleaned.setEmail(email);
        cleaned.setName(name);

        return cleaned;
    }

}
