package com.mobiquity.backbase.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.nio.charset.Charset;

/**
 * Created by sjoshi on 6/23/17.
 */
@Component
public class AuthProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        // get the username and password from the HTTP header
        final String authorization = exchange.getIn().getHeader("Authorization", String.class);
        String base64Credentials;
        if (authorization != null && authorization.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            base64Credentials = authorization.substring("Basic".length()).trim();
        }
        else {
            base64Credentials = authorization;
        }

        String credentials = new String(Base64.decodeBase64(base64Credentials),
                Charset.forName("UTF-8"));
        // credentials = username:password
        final String[] tokens = credentials.split(":", 2);

        // create an Authentication object
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokens[0], tokens[1]);

        // wrap it in a Subject
        Subject subject = new Subject();
        subject.getPrincipals().add(authToken);

        // place the Subject in the In message
        exchange.getIn().setHeader(Exchange.AUTHENTICATION, subject);

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
