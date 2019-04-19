package me.chanjar.reactorcorelearn.flux.factory;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxDefer {

  public static void main(String[] args) {

    Flux<Integer> flux = Flux.just(1, 2, 3, 4);
//    flux.subscribe();
//    flux.subscribe();

    Flux<Integer> deferedFlux = Flux.defer(() -> flux);
    deferedFlux.log().subscribe();
//    deferedFlux.log().subscribe();
  }
}
