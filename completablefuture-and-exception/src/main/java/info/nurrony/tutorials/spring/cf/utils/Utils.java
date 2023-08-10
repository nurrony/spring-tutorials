package info.nurrony.tutorials.spring.cf.utils;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    @SneakyThrows
    public static void loadingSimulator(int sec) {
        log.info("Sleeping for {} sec.", sec);
        String bar = " ".repeat(sec);
        for (int i = 0; i < sec; i++) {
            bar = bar.replaceFirst(" ", "=");
            log.info("[" + bar + "]");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
