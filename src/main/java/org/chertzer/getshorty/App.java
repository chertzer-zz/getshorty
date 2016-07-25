package org.chertzer.getshorty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Main app class. Starts a Jersey app with embetted Jetty server.
 */
public class App {
    private static final Logger logger = getLogger(AppResources.class);
    private static final int DEFAULT_PORT = 8080;

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

    /**
     * Start the getshorty service on the provided port or port 8080, if no port is
     * provided
     * @param args 0 or 1 args are accepted. If one arg is provided it must be a number
     *     > 1023 to avoid collisions or other problems having to do with reuse of
     *     UNIX system ports.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        App app = new App();
        if (args.length > 2) {
            usage();
            System.exit(1);
        } else if (args.length == 2 ) {
            int port = Integer.parseInt(args[1]);
            logger.info("Port is " + port);
            if (port <= 1023) {
                throw new IllegalArgumentException("Cannot use a reserved system port.");
            } else {
                app.initServer(port);
            }
        } else {
            app.initServer(DEFAULT_PORT);
        }
    }
}
