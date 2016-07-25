package org.chertzer.getshorty;
import org.chertzer.getshorty.util.URLMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.chertzer.getshorty.util.URLStore;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.*;

/**
 * URL shortener REST API
 * @author Cynthia Hertzer
 */
@Path("/getshorty")
public final class AppResources {
    private static final Logger logger = getLogger(AppResources.class);
    URLStore urls = URLMap.getInstance();

    public AppResources(){}

    /**
     * Create a new random short URL.
     * Usage:
     *  curl -X POST -H "Content-Type: text/plain; "http://google.com" http://localhost:8080/getshorty/link
     * @return javax.ws.rs.core.Response HTTP 400 if input syntax is not a valid URL
     *   javax.ws.rs.core.Response HTTP 200 and the shortURL text
     *
     */
    @POST
    @Path("link")
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.TEXT_PLAIN })
    public Response link(String originalUrl) {
        Response response;
        URI newURI = null;
        try {
            newURI = new URI(originalUrl);
            String shortURL = urls.setURL(originalUrl);
            response = Response.ok(shortURL).build();
        } catch (URISyntaxException e) {
            logger.error("Invalid URL " + e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid URL.").build();
            e.printStackTrace();
        }
        return response;
    }
    /**
     * Look up the originalURL that the given shortURL maps to
     * @return Response with a 307 Redirect if successful
     */
    @GET
    @Path("/{id}")
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.TEXT_PLAIN })
    public Response lookup(@PathParam("id") String id) {
        String originalUrl = urls.getURL(id);
        Response response;
        URI newURI = null;
        if (null == originalUrl) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            try {
                newURI = new URI(originalUrl);
            } catch (URISyntaxException e) {
                // This should never happen
                Response.status(500).entity("Retrieved bad URL from map ").build();
                logger.error("Retrieved bad URL from map. " + e);
                e.printStackTrace();
            }
        }
        response = Response.temporaryRedirect(newURI).build();
        return response;
    }
}

