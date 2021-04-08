package spring_course.my_bank_transactions.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement( name = "transaction" )
public class TransactionDto
{
   @NotBlank
   @NotNull
   private String reference_;
   @Min( 100 )
   @Max( 500 )
   private BigDecimal amount_;
   @NotBlank
   @NotNull
   private String receivingUserId_;

   @JsonCreator
   public TransactionDto(
      @JsonProperty( "reference" ) final String reference,
      @JsonProperty( "amount" ) final BigDecimal amount,
      @JsonProperty( "userId" ) final String receivingUserId )
   {
      this.reference_ = reference;
      this.amount_ = amount;
      this.receivingUserId_ = receivingUserId;
   }

   public TransactionDto()
   {
   }

   @JsonProperty( "reference" )
   public String getReference()
   {
      return reference_;
   }

   @JsonProperty( "amount" )
   public BigDecimal getAmount()
   {
      return amount_;
   }

   @JsonProperty( "userId" )
   public String getReceivingUserId()
   {
      return receivingUserId_;
   }
}
