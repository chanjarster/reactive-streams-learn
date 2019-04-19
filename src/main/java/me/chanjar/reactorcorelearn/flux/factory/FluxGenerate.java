package me.chanjar.reactorcorelearn.flux.factory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.Arrays;

/**
 *  <a href="https://projectreactor.io/docs/core/release/reference/#producing.generate">4.4.1. Synchronous generate</a>
 *  <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#generate-java.util.concurrent.Callable-java.util.function.BiFunction-">javadoc</a>
 */
public class FluxGenerate {

  public static void main(String[] args) {

    Flux
        .generate(
            () -> Arrays.asList(1, 2, 3, 4, 5, 6).iterator(),
            (iter, sink) -> {
              if (iter.hasNext()) {
                sink.next(iter.next());
              } else {
                sink.complete();
              }
              return iter;
            }
        )
        .log()
        .subscribe();

  }


}
