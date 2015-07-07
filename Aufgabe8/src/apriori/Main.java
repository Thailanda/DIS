package apriori;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.websocket.server.ServerContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class Main {
	public static void main(String[] args) {

        Injector injector = Guice.createInjector(new AprioriModule());

	    String[] inputFiles = new String[] { "trans_test", "transactions.txt", "transactionslarge.txt"};	    		
		double minSup = 0.01d;

		String inputFile = Apriori.class.getResource(inputFiles[0]).getFile();
		
//		Apriori algo = new Apriori();
//		algo.execute(inputFile, minSup);

        Server server = new Server(1337);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setBaseResource(Resource.newClassPathResource("/web"));

        // Setup the basic application "context" for this application at "/data"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/data");

        final HandlerList handler = new HandlerList();
        handler.addHandler(resourceHandler);
        handler.addHandler(context);
        server.setHandler(handler);

        try
        {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);

            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(AprioriSocket.class);

            server.start();
            server.dump(System.err);
            server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
	}
}
