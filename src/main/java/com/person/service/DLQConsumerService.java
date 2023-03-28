package com.person.service;

import com.person.model.Person;
import com.person.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class DLQConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(PersonConsumerService.class);

    @Value("${db-service}")
    private String dbServiceUrl;

    @RabbitListener(queues = {"deadLetter.queue"})
    public void consumeMessage(Person person){
        logger.info("Received Message From DLQ: " + person);
        try {
            HttpUtil.sendHttpPostRequest(dbServiceUrl, person);
            logger.info("Message successfully sent to db service: " + person);
        }
        catch (RestClientException e){
            logger.error("Couldnt send Message to db-service for "+person+", will now throw exception and retry");
            throw e;
        }
    }
}
