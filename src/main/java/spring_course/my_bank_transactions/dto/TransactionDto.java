package spring_course.my_bank_transactions.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionDto {

   private final String reference_;
   private final BigDecimal amount_;

   @JsonCreator
   public TransactionDto( @JsonProperty final String reference, @JsonProperty final BigDecimal amount ) {
      reference_ = reference;
      amount_ = amount;
   }

   public String getReference() {
      return reference_;
   }

   public BigDecimal getAmount() {
      return amount_;
   }
}
