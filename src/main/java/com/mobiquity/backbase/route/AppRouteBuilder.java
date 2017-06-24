package com.mobiquity.backbase.route;

import com.mobiquity.backbase.model.Atm;
import com.mobiquity.backbase.service.AtmService;
import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.model.OnExceptionDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.camel.spi.AuthorizationPolicy;
import org.apache.camel.spi.ExceptionHandler;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.spring.SpringRouteBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by sjoshi on 6/22/17.
 */
@Component
public class AppRouteBuilder extends RouteBuilder {

    @Autowired
    private AuthProcessor authProcessor;

    @Autowired
    private AtmService atmService;

    @Override
    public void configure() throws Exception {

        restConfiguration().component("restlet")
                .host("localhost")
                .port(8089)
                .bindingMode(RestBindingMode.json);

        onException(CamelAuthorizationException.class, AccessDeniedException.class)
                .handled(true)
                .transform().constant("Unauthorized Access! You do not have the necessary permissions to access this resource.");


        onException(BadCredentialsException.class)
                .handled(true)
                .transform().constant("Invalid Credentials! Please try again with the correct username and password.");

        /**
         * This route is called to get a list of all ATMs
         */
        rest("/demo/api/atms")
                .get()
                .route().process(authProcessor).policy("user")
                .bean(atmService, "findAllATMs");

        /**
         * This route is called to create an ATM
         */
        rest("/demo/api/atms")
                .post().type(Atm.class).produces("application/json")
                .route().process(authProcessor).policy("admin")
                .bean(atmService, "saveATM");

        /**
         * This route is called to get a list of ATMs within a City
         */
        rest("/demo/api/atms").get("/{city}")
                .param().name("id").type(RestParamType.path).description("The id of the user to get").endParam()
                .route().process(authProcessor).policy("user").bean(atmService, "findATMsByCity(${header.city})");

        /**
         * The default route that redirects to the index page where user can view the list of all ATMs
         */
        rest("/").get().produces("text/html").route().transform().simple("resource:classpath:view/index.html");
    }


}
