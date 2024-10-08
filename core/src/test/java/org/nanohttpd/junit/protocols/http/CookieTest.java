package org.nanohttpd.junit.protocols.http;

/*
 * #%L
 * NanoHttpd-Core
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the nanohttpd nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.nanohttpd.core.http.content.Cookie;

public class CookieTest {

    @Test
    public void testGetHTTPTime() {
        Random random = new Random();
        int randomExpirationTime = random.nextInt(100);
        assertNotNull("getHTTPTime should return a non-null value for " + randomExpirationTime + " days", Cookie.getHTTPTime(randomExpirationTime));
    }

    @Test
    public void testCookieWithNoExplicitExpirationTime() {
        Cookie cookie = new Cookie("CookieKey", "CookieValue");
        assertTrue("Cookie header should contain cookie key", cookie.getHTTPHeader().contains("CookieKey"));
        assertTrue("Cookie header should contain cookie value", cookie.getHTTPHeader().contains("CookieValue"));
    }

    @Test
    public void testCookieWithExplicitExpirationTime() {
        Cookie cookie = new Cookie("CookieKey", "CookieValue", 40);
        assertFalse("The default 30 days expires string should not be avaialbe in the cookie header" + " because the expiry has been specified as 40 days",
                cookie.getHTTPHeader().contains(Cookie.getHTTPTime(30)));
        assertTrue("Cookie header should contain cookie key", cookie.getHTTPHeader().contains("CookieKey"));
        assertTrue("Cookie header should contain cookie value", cookie.getHTTPHeader().contains("CookieValue"));
    }

    @Test
    public void testCookieWithExpiresString() {
        Random random = new Random();
        int randomExpirationTime = random.nextInt(100);
        String expiresString = Cookie.getHTTPTime(randomExpirationTime);
        Cookie cookie = new Cookie("CookieKey", "CookieValue", expiresString);
        assertTrue("Cookie should contain the expirs string passed in the constructor", cookie.getHTTPHeader().contains(expiresString));
        assertTrue("Cookie header should contain cookie key", cookie.getHTTPHeader().contains("CookieKey"));
        assertTrue("Cookie header should contain cookie value", cookie.getHTTPHeader().contains("CookieValue"));
    }

}
