package com.example.reactive_demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SinkService {

    void post(Mono<String> message);

    Flux<Mono<String>> history();
}
