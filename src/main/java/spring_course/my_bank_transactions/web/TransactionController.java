package spring_course.my_bank_transactions.web;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring_course.my_bank_transactions.dto.TransactionDto;
import spring_course.my_bank_transactions.dto.TransactionList;
import spring_course.my_bank_transactions.model.Transaction;
import spring_course.my_bank_transactions.service.AccountService;
import spring_course.my_bank_transactions.service.TransactionService;

import javax.validation.Valid;

@RestController
@Validated
public class TransactionController {

   private final TransactionService transactionService_;
   private final AccountService accountService_;

   public TransactionController( final TransactionService transactionService, final AccountService accountService )
   {
      transactionService_ = transactionService;
      accountService_ = accountService;
   }

   @GetMapping( "/transactions" )
   public TransactionList getTransactions() {
      return new TransactionList( transactionService_.getAll() );
   }

   @GetMapping( "/transactions/find" )
   public Transaction getTransaction( @RequestParam final String id ) {
      return transactionService_.find( id );
   }

   @PostMapping( value = "/transactions", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
   public Transaction createTransaction( @RequestBody @Valid TransactionDto transaction )
   {
      final var account = accountService_.supplyAccount( transaction.getReceivingUserId() );
      return transactionService_.create( account, transaction.getReference(), transaction.getAmount() );
   }
}
