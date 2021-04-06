package spring_course.my_bank_transactions.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring_course.my_bank_transactions.model.Account;
import spring_course.my_bank_transactions.model.Transaction;

import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionService {
   private static final ConcurrentHashMap< String, Transaction > TRANSACTIONS = new ConcurrentHashMap<>();
   private final String slogan_;
   private final JdbcTemplate jdbcTemplate_;

   public TransactionService( @Value( "${bank.slogan}" ) final String slogan, final JdbcTemplate jdbcTemplate )
   {
      slogan_ = slogan;
      jdbcTemplate_ = jdbcTemplate;
   }

   @Transactional
   public Transaction find( String id ) {
      final var transactions = jdbcTemplate_.query( "select * from transaction where id = ?",
                                             ( resultSet, rowNum ) -> new Transaction( resultSet.getObject( "id" ).toString(),
                                                                                       resultSet.getTimestamp( "timeStamp" )
                                                                                                .toLocalDateTime(),
                                                                                       resultSet.getBigDecimal( "amount" ),
                                                                                       resultSet.getString( "reference" ),
                                                                                       slogan_ ),
                                             id );
      if( transactions.isEmpty() ) {
         return null;
      }
      return transactions.get( 0 );
   }

   @Transactional
   public Transaction create( final Account account, final String reference, final BigDecimal amount ) {
      final var keyHolder = new GeneratedKeyHolder();
      final var transactionTime = LocalDateTime.now();
      jdbcTemplate_.update( connection -> {
         final var preparedStatement = connection.prepareStatement(
            "insert into transaction(amount, timeStamp, reference, account_id) values(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS );

         preparedStatement.setBigDecimal( 1, amount );
         preparedStatement.setTimestamp( 2, Timestamp.valueOf( transactionTime ) );
         preparedStatement.setString( 3, reference );
         preparedStatement.setString( 4, account.getId() );

         return preparedStatement;
      }, keyHolder );

      final String id = !Objects.requireNonNull( keyHolder.getKeys() ).isEmpty()
                        ? keyHolder.getKeys().values().iterator().next().toString()
                        : null;


      return new Transaction( id, transactionTime, amount, reference, slogan_ );
   }

   @Transactional
   public Collection< Transaction > getAll()
   {
      return jdbcTemplate_.query( "select * from transaction",
                                  ( resultSet, rowNum ) -> new Transaction( resultSet.getObject( "id" ).toString(),
                                                                            resultSet.getTimestamp( "timeStamp" )
                                                                                     .toLocalDateTime(),
                                                                            resultSet.getBigDecimal( "amount" ),
                                                                            resultSet.getString( "reference" ),
                                                                            slogan_ ) );
   }

   @Transactional
   public List< Transaction > findByAccount( final Account account )
   {
      return jdbcTemplate_.query( "select * from transaction where account_id = ?",
                                  ( resultSet, rowNum ) -> new Transaction( resultSet.getObject( "id" ).toString(),
                                                                            resultSet.getTimestamp( "timeStamp" )
                                                                                     .toLocalDateTime(),
                                                                            resultSet.getBigDecimal( "amount" ),
                                                                            resultSet.getString( "reference" ),
                                                                            slogan_ ),
                                  account.getId() );

   }
}
