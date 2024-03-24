package com.restaurant.reservationreview.frameworks.db.rating;

import jakarta.annotation.Resource;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class RatingRepositoryCustomImpl implements RatingRepositoryCustom {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, BigDecimal> calculateAvgScoreByRestaurant() {
        GroupOperation groupOperation = group("$restaurant.$id").avg("score").as("restaurant_score");

        Aggregation aggregation = newAggregation(
                groupOperation
        );

        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, "ratings", Document.class);

        List<Document> documents = result.getMappedResults();

        Map<String, BigDecimal> ratings = new HashMap<>();

        if (documents.isEmpty()) {
            return ratings;
        }

        documents.forEach(document -> ratings.put(
                document.get("_id").toString(),
                BigDecimal.valueOf(document.getDouble("restaurant_score")))
        );

        return ratings;
    }
}
