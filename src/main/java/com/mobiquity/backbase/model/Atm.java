package com.mobiquity.backbase.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sjoshi on 6/23/17.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Atm implements Serializable {
    private Address address;
    private String distance;
    private String type;
    private Date createdAt;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    static public class Address {
        private String street;
        private String housenumber;
        private String postalcode;
        private String city;
        private GeoLocation geoLocation;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHousenumber() {
            return housenumber;
        }

        public void setHousenumber(String housenumber) {
            this.housenumber = housenumber;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public GeoLocation getGeoLocation() {
            return geoLocation;
        }

        public void setGeoLocation(GeoLocation geoLocation) {
            this.geoLocation = geoLocation;
        }

        static public class GeoLocation {
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }
        }
    }



}
