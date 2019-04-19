package me.chanjar.reactorcorelearn.flux.factory;

import reactor.core.publisher.Flux;

/**
 * <a href="https://projectreactor.io/docs/core/release/reference/#producing.create">4.4.2. Asynchronous & multi-threaded: create</a>
 * <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#create-java.util.function.Consumer-">javadoc</a>
 */
public class FluxCreate {

  public static void main(String[] args) {

    ExternalEventEmitter externalEventEmitter = new ExternalEventEmitter();

    Flux
        .create(sink -> {
              externalEventEmitter.register(new ExternalEventListener() {
                @Override
                public void onData(Integer num) {
                  sink.next(num);
                }

                @Override
                public void onComplete() {
                  sink.complete();
                }
              });
            }
        )
        .log()
        .subscribe();

    new Thread(externalEventEmitter::emit).start();
    //    new Thread(externalEventEmitter::emit).start();
    externalEventEmitter.emit();
  }

}
