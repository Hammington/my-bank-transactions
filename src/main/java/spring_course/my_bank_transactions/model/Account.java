package spring_course.my_bank_transactions.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account
{
   private final String id_;
   private final String userId_;
   private BigDecimal balance_;
   private final List< Transaction > transactions_ = new ArrayList<>();

   public Account( String id, final String userId, BigDecimal balance )
   {
      id_ = id;
      userId_ = userId;
      balance_ = balance;
   }

   public void addTransaction( final Transaction transaction )
   {
      transactions_.add( transaction );
   }

   public List< Transaction > getTransactions()
   {
      return Collections.unmodifiableList( transactions_ );
   }

   public String getUserId()
   {
      return userId_;
   }

   public BigDecimal getBalance()
   {
      return balance_;
   }

   public void setBalance( BigDecimal balance )
   {
      balance_ = balance;
   }

   public String getId()
   {
      return id_;
   }
}
