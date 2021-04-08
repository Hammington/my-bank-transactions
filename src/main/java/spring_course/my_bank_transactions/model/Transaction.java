package spring_course.my_bank_transactions.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
   private final String id_;
   private final LocalDateTime timeStamp_;
   private final BigDecimal amount_;
   private final String reference_;
   private final String slogan_;

   public Transaction( final String id,
                       final LocalDateTime timeStamp,
                       final BigDecimal amount,
                       final String reference,
                       final String slogan ) {
      id_ = id;
      timeStamp_ = timeStamp;
      amount_ = amount;
      reference_ = reference;
      slogan_ = slogan;
   }

   public String getId() {
      return id_;
   }

   public LocalDateTime getTimeStamp() {
      return timeStamp_;
   }

   public BigDecimal getAmount() {
      return amount_;
   }

   public String getReference() {
      return reference_;
   }

   public String getSlogan() {
      return slogan_;
   }
}
