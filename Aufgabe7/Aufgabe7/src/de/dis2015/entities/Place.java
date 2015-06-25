package de.dis2015.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Joanna on 25.06.2015.
 */
@Entity
@Table
public class Place implements Serializable {

    @Id
    @Column
    private int landId;

    @Id
    @Column
    private int regionId;

    @Id
    @Column
    private int stadtId;

    @Id
    @Column
    private int shopId;

    @Column
    private String regionName;

    @Column
    private String cityName;

    @Column
    private String shopName;

    @Column
    private String countryName;

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getStadtId() {
        return stadtId;
    }

    public void setStadtId(int stadtId) {
        this.stadtId = stadtId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
