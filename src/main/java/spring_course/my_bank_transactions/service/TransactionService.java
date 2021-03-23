package spring_course.my_bank_transactions.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring_course.my_bank_transactions.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionService {
   private static final ConcurrentHashMap< String, Transaction > TRANSACTIONS = new ConcurrentHashMap<>();
   private final String slogan_;

   public TransactionService( @Value( "${bank.slogan}" ) final String slogan ) {
      slogan_ = slogan;
   }

   public Transaction find( String id ) {
      return TRANSACTIONS.get( id );
   }

   public Transaction create( String reference, BigDecimal amount ) {
      final var id = UUID.randomUUID().toString();
      return TRANSACTIONS.computeIfAbsent( id,
                                           ( theId ) -> new Transaction( theId,
                                                                         LocalDateTime.now(),
                                                                         amount,
                                                                         reference,
                                                                         slogan_ ) );
   }

   public Collection< Transaction > getAll() {
      return TRANSACTIONS.values();
   }
}
