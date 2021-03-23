package spring_course.my_bank_transactions.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JacksonXmlRootElement( localName = "transaction" )
public class TransactionDto {
   @NotBlank
   @NotNull
   @JacksonXmlProperty( isAttribute = true, localName = "reference" )
   private final String reference_;
   @Min( 100 ) @Max( 500 )
   @JacksonXmlProperty( isAttribute = true, localName = "amount" )
   private final BigDecimal amount_;

   @JsonCreator
   public TransactionDto( @JsonProperty( "reference" ) final String reference,
                          @JsonProperty( "amount" ) final BigDecimal amount ) {
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
