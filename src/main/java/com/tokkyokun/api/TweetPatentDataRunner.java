package com.tokkyokun.api;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "command", havingValue = "tweet")
public class TweetPatentDataRunner implements ApplicationRunner {

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        System.out.println("tweet");
    }

}
