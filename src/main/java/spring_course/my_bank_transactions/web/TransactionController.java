package spring_course.my_bank_transactions.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring_course.my_bank_transactions.dto.TransactionDto;
import spring_course.my_bank_transactions.model.Transaction;
import spring_course.my_bank_transactions.service.TransactionService;

import java.util.Collection;

@RestController
public class TransactionController {

   private final TransactionService transactionService_;

   public TransactionController( final TransactionService transactionService ) {
      transactionService_ = transactionService;
   }

   @GetMapping( "/transactions" )
   public Collection< Transaction > getTransactions() {
      return transactionService_.getAll();
   }

   @GetMapping( "/transactions/find" )
   public Transaction getTransaction( @RequestParam final String id ) {
      return transactionService_.find( id );
   }

   @PostMapping( "/transactions" )
   public Transaction createTransaction( final TransactionDto transaction ) {
      System.out.println("create me");
      return transactionService_.create( transaction.getReference(), transaction.getAmount() );
   }
}
