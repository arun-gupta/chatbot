package org.sample.aws.alexa.chatbot;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author Arun Gupta
 */
@DynamoDBTable(tableName = "starwars")
public class StarWarsCharacter {

    private int id;
    private String name;
    private String planet;
    private String lightsaberColor;

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
}
