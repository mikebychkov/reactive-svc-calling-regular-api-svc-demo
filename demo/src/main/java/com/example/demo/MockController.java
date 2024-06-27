package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/mock")
@Log4j2
public class MockController {

    private final Random random = new Random();

    @GetMapping("/get")
    public ResponseEntity<String> get() throws InterruptedException {

        int rsl = random.nextInt(10);

        Thread.sleep(1000 * rsl);

        String rslString = "";

        if (rsl > 8) {
            rslString = String.format("I TRIED: %s %s", rsl, UUID.randomUUID());
            log.info(rslString);
            return ResponseEntity.badRequest().body(rslString);
        }
        rslString = String.format("I DID IT: %s %s", rsl, UUID.randomUUID());
        log.info(rslString);
        return ResponseEntity.ok(rslString);
    }
}
