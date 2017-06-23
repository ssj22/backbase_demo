package com.mobiquity.backbase.data;

import com.mobiquity.backbase.model.Atm;
import jdk.nashorn.internal.parser.JSONParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sjoshi on 6/22/17.
 */
@Component
public class AtmDataStore {

    private Map<String, List<Atm>> atmMap = new HashMap<>();

    @PostConstruct
    public void initDataStore() {

        StringBuilder stringBuilder;
        BufferedReader reader = null;
        try {
            URL ingAtmUrl = new URL("https://www.ing.nl/api/locator/atms/");
            HttpURLConnection conn = (HttpURLConnection) ingAtmUrl.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(30000);
            conn.connect();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            stringBuilder.replace(0,5, "");

            JSONArray atmRoot = new JSONArray(stringBuilder.toString());

            for (int i=0; i < atmRoot.length(); i++) {
                JSONObject atmJson = atmRoot.getJSONObject(i);
                ObjectMapper mapper = new ObjectMapper();
                Atm atm = mapper.readValue(atmJson.toString(), Atm.class);

                String city = atm.getAddress().getCity();
                if (atmMap.containsKey(city)) {
                    List<Atm> cityAtmList = atmMap.get(city);
                    cityAtmList.add(atm);
                } else {
                    List<Atm> cityAtmList = new ArrayList<>();
                    cityAtmList.add(atm);
                    atmMap.put(city, cityAtmList);
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<Atm>> getAtmMap() {
        return atmMap;
    }

    public void setAtmMap(Map<String, List<Atm>> atmMap) {
        this.atmMap = atmMap;
    }
}
