package me.chanjar.reactorcorelearn.flux.factory;

import reactor.core.publisher.Flux;

/**
 * <a href="https://projectreactor.io/docs/core/release/reference/#_asynchronous_but_single_threaded_push">4.4.3. Asynchronous but single-threaded: push</a>
 * <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#push-java.util.function.Consumer-">javadoc</a>
 */
public class FluxPush {

  public static void main(String[] args) {

    ExternalEventEmitter externalEventEmitter = new ExternalEventEmitter();

    Flux
        .push(sink -> externalEventEmitter.register(new ExternalEventListener() {
          @Override
          public void onData(Integer num) {
            sink.next(num);
          }

          @Override
          public void onComplete() {
            sink.complete();
          }
        }))
        .log()
        .subscribe();

    new Thread(externalEventEmitter::emit).start();
  }

}
