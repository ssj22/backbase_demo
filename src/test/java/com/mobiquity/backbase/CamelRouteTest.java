package com.mobiquity.backbase;

import com.mobiquity.backbase.config.CamelConfig;
import com.mobiquity.backbase.model.Atm;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sjoshi on 6/23/17.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = CamelSpringDelegatingTestContextLoader.class, classes = CamelConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@UseAdviceWith
@WebAppConfiguration
public class CamelRouteTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultAllAtms;

    @Produce(uri = "rest:GET:/demo/api/atms")
    protected ProducerTemplate template;

    @Autowired
    CamelContext camelContext;

    @Before
    public void before() throws Exception {
        camelContext.setTracing(true);

        camelContext.start();
    }

    @Test
    public void testGetAllATMs() throws Exception {
        //resultAllAtms.expectedMinimumMessageCount(1);

        //template.sendBody(null);

        resultAllAtms.assertIsSatisfied();
    }
}
