package spring_course.my_bank_transactions;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import spring_course.my_bank_transactions.context.MyBankTransactionsApplicationConfiguration;

import java.util.Optional;

public class ApplicationLauncher {
   public static void main( String[] args ) throws LifecycleException {
      final var tomcat = new Tomcat();
      final var serverPort = System.getProperty( "server.port" );
      tomcat.setPort( Optional.ofNullable( serverPort ).map( Integer::valueOf ).orElse( 8080 ) );
      tomcat.getConnector();

      final var context = tomcat.addContext( "", null );
      final var applicationContext = new AnnotationConfigWebApplicationContext();
      applicationContext.register( MyBankTransactionsApplicationConfiguration.class );
      applicationContext.setServletContext( context.getServletContext() );
      applicationContext.refresh();
      applicationContext.registerShutdownHook();

      final var servlet = Tomcat.addServlet( context, "myFirstServlet", new DispatcherServlet( applicationContext ) );
      servlet.setLoadOnStartup( 1 );
      servlet.addMapping( "/*" );

      tomcat.start();
   }
}
