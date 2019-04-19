package me.chanjar.reactorcorelearn.flux.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * <a href="https://projectreactor.io/docs/core/release/reference/#_subscribeon">4.5.2. subscribeOn</a>
 * <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#subscribeOn-reactor.core.scheduler.Scheduler-">javadoc</a>
 */
public class FluxSubscribeOn {

  private static final Logger LOGGER = LoggerFactory.getLogger("Subscriber");

  public static void main(String[] args) {

    Scheduler scheduler1 = Schedulers.newParallel("sch-pub");
    Scheduler scheduler2 = Schedulers.newParallel("sch-sub");

    Flux.just(1, 2, 3, 4, 5, 6)
        .log("Logger 1")
        .publishOn(scheduler1)
        .log("Logger 2")
        .subscribeOn(scheduler2)
        .subscribe(
            i -> LOGGER.info("Got " + i),
            null,
            () -> {
              scheduler1.dispose();
              scheduler2.dispose();
            }

        );



  }
}
