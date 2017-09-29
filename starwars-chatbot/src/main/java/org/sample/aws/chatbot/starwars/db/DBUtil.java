package org.sample.aws.chatbot.starwars.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Arun Gupta
 */
public class DBUtil {

    private static AmazonDynamoDB dynamodbClient;

    private static AmazonDynamoDB getClient() {
        if (null != dynamodbClient) {
            return dynamodbClient;
        }

        String region = System.getProperty("DYNAMODB_REGION");
        if (null == region) {
            System.err.println("Region not set, default \"" + Regions.US_EAST_1.name() + "\" is used");
            region = Regions.US_EAST_1.name();
        }
        System.out.println("DynamoDB region: " + region);

        dynamodbClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();

        return dynamodbClient;
    }

    private static int getCharactersCount() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("starwars");
        ScanResult result = getClient().scan(scanRequest);
        System.out.println("Total number of characters found: " + result.getCount());

        // scanning only returns 1MB of data
        // need to aggressively scan and return an accurate count

        return result.getCount();
    }

    public static StarWarsCharacter getRandomCharacter() {
        DynamoDBMapper mapper = new DynamoDBMapper(getClient());
        Random random = new Random();

        return mapper.load(StarWarsCharacter.class, random.nextInt(getCharactersCount()) + 1);
    }

    public static StarWarsCharacter getCharacter(String name) {
        System.out.println("Name: " + name);
        DynamoDBMapper mapper = new DynamoDBMapper(getClient());
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":name", new AttributeValue().withS(name.toLowerCase()));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("whoami = :name")
                .withExpressionAttributeValues(eav);
        List<StarWarsCharacter> list = mapper.scan(StarWarsCharacter.class, scanExpression);
        
        if (!list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }
}
