package com.mobiquity.backbase.service.impl;

import com.mobiquity.backbase.data.AtmDataStore;
import com.mobiquity.backbase.model.Atm;
import com.mobiquity.backbase.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjoshi on 6/22/17.
 */
@Service
public class AtmServiceImpl implements AtmService {

    @Autowired
    private AtmDataStore atmDataStore;

    @Override
    public List<Atm> findAllATMs() {
        List<Atm> atmList = new ArrayList<>();
        for (List<Atm> atmInCity: atmDataStore.getAtmMap().values()) {
            atmList.addAll(atmInCity);
        }
        return atmList;
    }

    @Override
    public List<Atm> findATMsByCity(String city) {
        return atmDataStore.getAtmMap().get(city);
    }

    @Override
    public void saveATM(Atm atm) {
        String city = atm.getAddress().getCity();
        if (atmDataStore.getAtmMap().containsKey(city)) {
            List<Atm> atms = atmDataStore.getAtmMap().get(city);
            atms.add(atm);
        } else {
            List<Atm> atms = new ArrayList<>();
            atms.add(atm);
            atmDataStore.getAtmMap().put(city, atms);
        }
    }
}
