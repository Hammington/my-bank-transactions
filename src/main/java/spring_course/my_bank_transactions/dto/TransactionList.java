package spring_course.my_bank_transactions.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import spring_course.my_bank_transactions.model.Transaction;

import java.util.Collection;

@JacksonXmlRootElement( localName = "transactions" )
public class TransactionList
{
   private final Collection< Transaction > transactions_;

   public TransactionList( final Collection< Transaction > transactions )
   {
      transactions_ = transactions;
   }

   @JacksonXmlElementWrapper( useWrapping = false )
   @JacksonXmlProperty( localName = "transaction" )
   public Collection< Transaction > getTransactions() {
      return transactions_;
   }
}
