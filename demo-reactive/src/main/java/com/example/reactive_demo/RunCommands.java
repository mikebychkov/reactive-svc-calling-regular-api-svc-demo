package com.example.reactive_demo;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Random;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RunCommands implements CommandLineRunner {

    SinkService sinkService;
    WebClient.Builder webClientBuilder;
    @NonFinal
    WebClient webClient;

    @PostConstruct
    void init() {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8888/")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Override
    public void run(String... args) throws Exception {

        Random random = new Random();

        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(random.nextInt(1, 10)))
                .subscribe(i -> {
                    Mono<String> mono = webClient.get()
                    .uri("/mock/get")
                    .retrieve()
                    .bodyToMono(String.class);
                    sinkService.post(mono);
                });
    }
}
