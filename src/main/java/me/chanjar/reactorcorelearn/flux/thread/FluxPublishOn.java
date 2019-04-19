package me.chanjar.reactorcorelearn.flux.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * <a href="https://projectreactor.io/docs/core/release/reference/#_publishon">4.5.1. publishOn</a>
 * <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#publishOn-reactor.core.scheduler.Scheduler-">javadoc</a>
 */
public class FluxPublishOn {

  private static final Logger LOGGER = LoggerFactory.getLogger("Subscriber");

  public static void main(String[] args) {

    Scheduler scheduler1 = Schedulers.newParallel("sch-pub-1");
    Scheduler scheduler2 = Schedulers.newParallel("sch-pub-2");

    Flux.just(1, 2, 3, 4, 5, 6)
        .publishOn(scheduler1)
        .log("Logger 1")
        .publishOn(scheduler1)
        .log("Logger 2")
        .publishOn(scheduler2)
        .log("Logger 3")
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
