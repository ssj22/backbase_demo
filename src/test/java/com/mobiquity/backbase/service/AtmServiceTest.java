package com.mobiquity.backbase.service;

import com.mobiquity.backbase.config.AppConfig;
import com.mobiquity.backbase.config.CamelConfig;
import com.mobiquity.backbase.model.Atm;
import com.mobiquity.backbase.service.impl.AtmServiceImpl;
import org.hamcrest.Matcher;
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
public class AtmServiceTest{

    @Autowired
    private AtmService atmService;

    @Test
    public void testAtmService() {
        MatcherAssert.assertThat(atmService, instanceOf(AtmServiceImpl.class));
    }

    @Test
    public void testGetAllAtms() {
        List<Atm> atms = atmService.findAllATMs();
        Assert.assertNotNull(atms);
        MatcherAssert.assertThat(atms.isEmpty(), is(false));
    }

    @Test
    public void testfindATMsByCity() {
        List<Atm> allAtms = atmService.findAllATMs();
        String city = allAtms.get(0).getAddress().getCity();

        List<Atm> cityAtms = atmService.findATMsByCity(city);
        Assert.assertNotNull(cityAtms);
        MatcherAssert.assertThat(cityAtms.isEmpty(), is(false));
    }

    @Test
    public void testSaveATMs() {
        List<Atm> allAtms = atmService.findAllATMs();
        Atm atm = allAtms.get(0);
        String city = atm.getAddress().getCity();

        String newStreet = atm.getAddress().getStreet() + " _new_";
        String newHouseNumber = atm.getAddress().getHousenumber() + "_new_";

        atm.getAddress().setStreet(newStreet);
        atm.getAddress().setHousenumber(newHouseNumber);

        atmService.saveATM(atm);

        List<Atm> cityAtms = atmService.findATMsByCity(city);

        Assert.assertNotNull(cityAtms);
        MatcherAssert.assertThat(cityAtms.isEmpty(), is(false));

        Atm matchingAtm = null;
        for (Atm cityAtm: cityAtms) {
            if (cityAtm.getAddress().getStreet().equals(newStreet)
                    && cityAtm.getAddress().getHousenumber().equals(newHouseNumber)) {
                matchingAtm = cityAtm;
                break;
            }
        }
        Assert.assertNotNull(matchingAtm);
    }

}
