package me.chanjar.reactorcorelearn.flux.factory;

import reactor.core.publisher.Flux;

/**
 * <a href="https://projectreactor.io/docs/core/release/reference/#_handle">4.4.4. Handle</a>
 * <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#handle-java.util.function.BiConsumer-">javadoc</a>
 */
public class FluxHandle {

  public static void main(String[] args) {

    Flux
        .just(1, 2, 3, 4, 5, 6, 7)
        .handle((i, sink) -> {
          if (i < 5) {
            sink.next(i);
          }
        })
        .log()
        .subscribe();

  }
}
