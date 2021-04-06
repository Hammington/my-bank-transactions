package spring_course.my_bank_transactions.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring_course.my_bank_transactions.model.Account;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Objects;

@Component
public class AccountService
{
   private final JdbcTemplate jdbcTemplate_;
   private final TransactionService transactionService_;

   public AccountService( final JdbcTemplate jdbcTemplate, final TransactionService transactionService )
   {
      jdbcTemplate_ = jdbcTemplate;
      transactionService_ = transactionService;
   }

   @Transactional
   public Account supplyAccount( final String userId )
   {
      final var accounts = jdbcTemplate_.query( "select * from account where user_Id = ?",
                                                ( resultSet, rowNum ) -> new Account( resultSet.getObject( "id" ).toString(),
                                                                                      resultSet.getString( "user_id" ),
                                                                                      resultSet.getBigDecimal( "balance" ) ),
                                                userId );
      final var keyHolder = new GeneratedKeyHolder();

      if( !accounts.isEmpty() ) {
         final var account = accounts.get( 0 );
         transactionService_.findByAccount( account ).forEach( account::addTransaction );
         return account;
      }

      jdbcTemplate_.update( connection -> {
         final var preparedStatement = connection.prepareStatement( "insert into account ( balance, user_id ) values ( ?, ? )",
                                                                    Statement.RETURN_GENERATED_KEYS );
         preparedStatement.setBigDecimal( 1, BigDecimal.ZERO );
         preparedStatement.setString( 2, userId );

         return preparedStatement;
      }, keyHolder );

      final String id = !Objects.requireNonNull( keyHolder.getKeys() ).isEmpty()
                        ? keyHolder.getKeys().values().iterator().next().toString()
                        : null;

      return new Account( id, userId, BigDecimal.ZERO );
   }

   @Transactional
   public void updateBalance( final Account userAccount, final BigDecimal amount )
   {
      jdbcTemplate_.update( connection -> {
         final var preparedStatement = connection.prepareStatement( "update ACCOUNT set BALANCE = ? where ID = ?" );
         preparedStatement.setBigDecimal( 1, userAccount.getBalance().add( amount ) );
         preparedStatement.setString( 2, userAccount.getId() );
         return preparedStatement;
      } );
   }
}
