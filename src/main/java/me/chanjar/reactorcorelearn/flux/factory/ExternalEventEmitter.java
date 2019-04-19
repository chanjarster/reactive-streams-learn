package me.chanjar.reactorcorelearn.flux.factory;

import java.time.Duration;

class ExternalEventEmitter {

  private ExternalEventListener listener;

  public void register(ExternalEventListener listener) {
    this.listener = listener;
  }

  public void unregister() {
    this.listener = null;
  }

  public void emit() {
    try {
      int count = 0;
      while (count < 10) {
        Thread.sleep(Duration.ofSeconds(1L).toMillis());
        listener.onData(count++);
        listener.onData(count++);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      listener.onComplete();
    }
  }

}
