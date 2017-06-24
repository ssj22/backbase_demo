package com.mobiquity.backbase.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mobiquity.backbase.model.Atm;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sjoshi on 6/22/17.
 */
public interface AtmService {

    /**
     * This API returns a list of all ATMs
     * @return List of ATMs
     */
    List<Atm> findAllATMs();

    /**
     * This API finds a list of ATMs within a city
     * @return Atm object
     */
    List<Atm> findATMsByCity(String city);

    /**
     * This API persists an ATM object
     * @return Atm object
     */
    Atm saveATM(Atm atm);
}
