package com.mobiquity.backbase.data;

import com.mobiquity.backbase.config.AppConfig;
import com.mobiquity.backbase.config.CamelConfig;
import com.mobiquity.backbase.model.Atm;
import com.mobiquity.backbase.service.AtmService;
import com.mobiquity.backbase.service.impl.AtmServiceImpl;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by sjoshi on 6/23/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class AtmDataStoreTest {

    @Autowired
    private AtmDataStore atmDataStore;

    @Test
    public void testGetAllAtms() {
        atmDataStore.getAtmMap().clear();
        atmDataStore.initDataStore();
        MatcherAssert.assertThat(atmDataStore.getAtmMap().isEmpty(), is(false));
    }
}
