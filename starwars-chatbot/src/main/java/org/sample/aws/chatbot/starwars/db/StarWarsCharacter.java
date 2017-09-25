package org.sample.aws.chatbot.starwars.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

/**
 * @author Arun Gupta
 */
@DynamoDBTable(tableName = "starwars")
public class StarWarsCharacter {

    private int id;
    private String name;
    private String planet;
    private String lightsaberColor;
    private String ship;
    private String weapon;
    private boolean dead;
    private boolean forceSensitive;
    private String forceSide;
    private List<String> quotes;

    public StarWarsCharacter() {
    }

    public StarWarsCharacter(int id, String name, String planet) {
        this.id = id;
        this.name = name;
        this.planet = planet;
    }
    
    @DynamoDBHashKey
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DynamoDBHashKey(attributeName = "whoami")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    @DynamoDBAttribute(attributeName = "lightsaber")
    public String getLightsaberColor() {
        return lightsaberColor;
    }

    public void setLightsaberColor(String lightsaberColor) {
        this.lightsaberColor = lightsaberColor;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @DynamoDBAttribute(attributeName = "force-sensitive")
    public boolean isForceSensitive() {
        return forceSensitive;
    }

    public void setForceSensitive(boolean forceSensitive) {
        this.forceSensitive = forceSensitive;
    }

    @DynamoDBAttribute(attributeName = "force-side")
    public String getForceSide() {
        return forceSide;
    }

    public void setForceSide(String forceSide) {
        this.forceSide = forceSide;
    }

    public List<String> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<String> quotes) {
        this.quotes = quotes;
    }
}
