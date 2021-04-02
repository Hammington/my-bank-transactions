package spring_course.my_bank_transactions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account
{
   private final String userId_;
   private final List< Transaction > transactions_ = new ArrayList<>();

   public Account( final String userId )
   {
      userId_ = userId;
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
}
