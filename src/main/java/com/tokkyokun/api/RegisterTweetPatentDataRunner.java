package com.tokkyokun.api;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnProperty(name = "command", havingValue = "register")
public class RegisterTweetPatentDataRunner implements ApplicationRunner {

    @Override
    public void run(final ApplicationArguments args) throws Exception {

        final var docNums = Flux.just(1, 2, 3, 4, 5, 6);
        final var token = Mono.just("token");

        docNums.subscribe(s -> {
            token.subscribe(x -> {
                System.out.println(x);
                System.out.println(s);
            });
        });

    }
}
