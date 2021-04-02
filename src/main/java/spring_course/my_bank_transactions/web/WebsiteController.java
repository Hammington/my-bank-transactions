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
      return "account.html";
   }

   @PostMapping( "account/{userId}/transaction" )
   public String createTransaction( @PathVariable( "userId" ) final String userId,
                                    @ModelAttribute final TransactionDto transactionDto,
                                    final BindingResult result,
                                    Model model ) {
      final var transaction = transactionService_.create( transactionDto.getReference(), transactionDto.getAmount() );
      accountService_.supplyAccount( transactionDto.getReceivingUserId() ).addTransaction( transaction );
      model.addAttribute( "transactions", accountService_.supplyAccount( userId ).getTransactions() );
      model.addAttribute( "userId", userId );
      return "account.html";
   }

   @ModelAttribute( "newTransaction" )
   public TransactionDto getNewTransaction()
   {
      return new TransactionDto();
   }
}
