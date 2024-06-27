package com.example.reactive_demo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class SinkServiceImpl implements SinkService {

    private final Sinks.Many<Mono<String>> chatHistory = Sinks.many().replay().limit(1000);

    @Override
    public void post(Mono<String> message) {

        chatHistory.tryEmitNext(message);
    }

    @Override
    public Flux<Mono<String>> history() {

        return chatHistory.asFlux();
    }
}
