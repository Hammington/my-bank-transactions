package spring_course.my_bank_transactions.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring_course.my_bank_transactions.dto.TransactionDto;
import spring_course.my_bank_transactions.service.AccountService;
import spring_course.my_bank_transactions.service.TransactionService;

import java.util.Objects;

@Controller
public class WebsiteController
{
   private final AccountService accountService_;
   private final TransactionService transactionService_;

   public WebsiteController( final AccountService accountService, final TransactionService transactionService )
   {
      accountService_ = accountService;
      transactionService_ = transactionService;
   }

   @GetMapping( "account/{userId}" )
   public String account( final Model model, @PathVariable( "userId" ) final String userId )
   {
      final var account = accountService_.supplyAccount( userId );
      model.addAttribute( "transactions", account.getTransactions() );
      model.addAttribute( "userId", userId );
      model.addAttribute( "balance", account.getBalance() );
      return "account.html";
   }

   @PostMapping( "account/{userId}/transaction" )
   public String createTransaction(
      @PathVariable( "userId" ) final String userId,
      @ModelAttribute final TransactionDto transactionDto,
      final BindingResult result,
      final Model model )
   {
      final var account = accountService_.supplyAccount( transactionDto.getReceivingUserId() );
      accountService_.updateBalance( account, transactionDto.getAmount() );
      account.setBalance( account.getBalance().add( transactionDto.getAmount() ) );
      transactionService_.create( account, transactionDto.getReference(), transactionDto.getAmount() );

      final var isPayIn = Objects.equals( userId, transactionDto.getReceivingUserId() );

      if( !isPayIn ) {
         final var userAccount = accountService_.supplyAccount( userId );
         accountService_.updateBalance( userAccount, transactionDto.getAmount().negate() );
         transactionService_.create( userAccount, transactionDto.getReference(), transactionDto.getAmount().negate() );
      }
      return "redirect:/account/" + userId;
   }

   @ModelAttribute( "newTransaction" )
   public TransactionDto getNewTransaction()
   {
      return new TransactionDto();
   }
}
