package org.chertzer.getshorty;

import org.chertzer.getshorty.util.Properties;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import sun.rmi.runtime.Log;

import static org.chertzer.getshorty.util.Properties.*;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * Main app class. Starts a Jersey app with embetted Jetty server.
 */
public class App {
    private static final Properties props = new Properties();
    private static final Logger logger = getLogger(AppResources.class);

    private static void usage() {
        logger.error("Usage: App [customPort] " +
                " By default the App runs on port 8080, but a single optional numeric " +
                " arg may be passed to specify an alternate port. ");
    }

    /**
     * Launch an embedded Jetty server to listen on the given port
     * @param port
     * @throws Exception
     */
    void initServer(int port) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(port);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Configure our REST resources
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                org.chertzer.getshorty.AppResources.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }

    }
    public static void main(String[] args) throws Exception {
        App app = new App();
        logger.info(" Got " + args.length); // TODO delete
        if (args.length > 2) {
            usage();
            System.exit(1);
        } else if (args.length == 2 ) {
            //TODO check if the port is in a reasonable range
            app.initServer(Integer.parseInt(args[1]));
        } else {
            app.initServer(getIntProp("getshorty.port"));
        }
    }
}
