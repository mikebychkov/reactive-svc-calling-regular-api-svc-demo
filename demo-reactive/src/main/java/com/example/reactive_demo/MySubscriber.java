package com.example.reactive_demo;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class MySubscriber {

    SinkService sinkService;

    @PostConstruct
    void init() {

        log.info("Subscriber initialized");

        Disposable d = sinkService.history()
                .flatMap(Mono::flux)
                .onErrorContinue((e, s) -> {
                    log.error("API Call Error: {} {}", e.getMessage(), s);
                })
                .subscribe(s -> {
                    log.info(s);
                });
    }
}
