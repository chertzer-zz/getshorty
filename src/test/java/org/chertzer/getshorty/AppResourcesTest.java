package org.chertzer.getshorty;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by chertzer on 7/21/16.
 */
public class AppResourcesTest {
    AppResources ar;
    @Before
    public void setUp() {
        ar = new AppResources();
    }

    @Test
    public void testLinkAndGetNormalURL() {
        String netflixURL = "http://www.netflix.com";
        Response r0 = ar.link(netflixURL);
        assertThat(r0.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertNotNull(r0.getEntity());
        String shortURL = ((String) r0.getEntity().toString());
        assertNotNull(shortURL);

        Response r1 = ar.lookup(shortURL);
        assertNotNull(r1);

        String originalURL = r1.getLocation().toString();
        assertThat(originalURL, is(netflixURL));

        assertThat(r1.getStatus(), is(Response.Status.TEMPORARY_REDIRECT.getStatusCode()));
    }

    @Test
    public void testLinkAndGetDupeURL() {
        String netflixURL = "http://www.netflix.com";
        Response r0 = ar.link(netflixURL);
        assertThat(r0.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertNotNull(r0.getEntity());
        String firstShortURL = ((String) r0.getEntity().toString());
        assertNotNull(firstShortURL);

        Response r1 = ar.lookup(firstShortURL);
        assertNotNull(r1);
        String firstOriginalURL = r1.getLocation().toString();
        assertThat(r1.getStatus(), is(Response.Status.TEMPORARY_REDIRECT.getStatusCode()));
        assertThat(firstOriginalURL, is(netflixURL));

        Response r2 = ar.link(netflixURL);
        assertThat(r2.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertNotNull(r2.getEntity());
        String secondShortURL = ((String) r2.getEntity().toString());
        assertNotNull(secondShortURL);
        assert(!firstShortURL.equals(secondShortURL));
        
        Response r3 = ar.lookup(secondShortURL);
        String secondOriginalURL = r3.getLocation().toString();
        assertThat(secondOriginalURL, is(netflixURL));
        assertThat(r3.getStatus(), is(Response.Status.TEMPORARY_REDIRECT.getStatusCode()));
    }
    /*
     * Error log and stacktrace of java.net.URISyntaxException expected from this test
     */
    @Test
    public void testLinkInvalidURL() {
        String badSyntaxURL = "http://www.net flix.com";
        Response r = ar.link(badSyntaxURL);
        assertThat(r.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testLinkAndGetReallyLongURL() {
        String reallyLongURL = "http://www.netflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflixnetflix.com";
        Response r0 = ar.link(reallyLongURL);
        assertThat(r0.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertNotNull(r0.getEntity());

        Response r1 = ar.lookup(((String) r0.getEntity().toString()));
        assertThat(r1.getStatus(), is(Response.Status.TEMPORARY_REDIRECT.getStatusCode()));
        // For some reason we have to call toString or we get confused running this test...
        assertEquals(reallyLongURL, r1.getLocation().toString());

    }
}