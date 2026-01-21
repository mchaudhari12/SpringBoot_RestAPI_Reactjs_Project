package org.studyeasy.SpringRestdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.studyeasy.SpringRestdemo.model.Account;
import org.studyeasy.SpringRestdemo.service.AccountService;
import org.studyeasy.SpringRestdemo.util.constant.Authority;

@Component
public class seedData implements CommandLineRunner{

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        
        Account account01 = new Account();
        Account account02 = new Account();

        account01.setEmail("manish@gmail.com");
        account01.setPassword("manish");
        account01.setAuthorities(Authority.USER.toString());
        accountService.save(account01);

        account02.setEmail("Jitendra@gmail.com");
        account02.setPassword("manish");
        account02.setAuthorities(Authority.ADMIN.toString() + " "+Authority.USER.toString());
        accountService.save(account02);

    }
    
}
