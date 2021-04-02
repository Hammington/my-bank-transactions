package spring_course.my_bank_transactions.service;

import org.springframework.stereotype.Component;
import spring_course.my_bank_transactions.model.Account;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountService
{
   private static final ConcurrentHashMap< String, Account > ACCOUNTS_BY_USER = new ConcurrentHashMap<>();

   public Account supplyAccount( final String userId )
   {
      return ACCOUNTS_BY_USER.computeIfAbsent( userId, Account::new );
   }
}
