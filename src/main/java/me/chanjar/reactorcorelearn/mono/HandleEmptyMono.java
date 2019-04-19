package me.chanjar.reactorcorelearn.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class HandleEmptyMono {

  public static void main(String[] args) {

    HandleEmptyMono checker = new HandleEmptyMono();

    checker.oldCheck(null);
    checker.oldCheck(new Token(true));
    checker.oldCheck(new Token(false));

    checker.badCheck(Mono.empty()).subscribe();
    checker.badCheck(Mono.just(new Token(true))).subscribe();
    checker.badCheck(Mono.just(new Token(false))).subscribe();

    checker.goodCheck(Mono.empty()).subscribe();
    checker.goodCheck(Mono.just(new Token(true))).subscribe();
    checker.goodCheck(Mono.just(new Token(false))).subscribe();

  }

  public void oldCheck(Token token) {

    Logger logger = LoggerFactory.getLogger("oldChecker");

    if (token == null) {
      logger.info("Token is null");
      return;
    }

    if (token.isExpired) {
      logger.info("Token is expired");
      return;
    }

    logger.info("Token is not expired");
    return;
  }

  public Mono<Void> goodCheck(Mono<Token> tokenMono) {

    Logger logger = LoggerFactory.getLogger("goodCheck");

    return tokenMono

        // Transform Mono<Token> to Mono<Optional<Token>>.
        // If Mono<Token> is empty, flatMap will not be triggered,
        // then we will get a empty Mono<Optional<Token>>
        .flatMap(token -> Mono.just(Optional.of(token)))

        // If Mono<Optional<Token>> is empty, provide an empty Optional<Token>,
        // then we will get a non-empty Mono<Optional<Token>> anyway
        .defaultIfEmpty(Optional.empty())

        // Since Mono<Optional<Token>> is not empty, flatMap will always be triggered.
        .flatMap(tokenOptional -> {

          if (!tokenOptional.isPresent()) {
            logger.info("Token is null");
            return Mono.empty();
          }

          Token token = tokenOptional.get();

          if (token.isExpired) {
            logger.info("Token is expired");
            return Mono.empty();
          }

          logger.info("Token is not expired");
          return Mono.empty();

        })
        ;

  }

  public Mono<Void> badCheck(Mono<Token> tokenMono) {

    Logger logger = LoggerFactory.getLogger("badCheck");

    return tokenMono

        .flatMap(token -> {

          if (token == null) {
            // You will never be in here
            logger.info("Token is null");
            return Mono.empty();
          }

          if (token.isExpired) {
            logger.info("Token is expired");
            return Mono.empty();
          }

          logger.info("Token is not expired");
          return Mono.empty();

        })
        ;

  }

  private static class Token {

    private final boolean isExpired;

    public Token(boolean isExpired) {
      this.isExpired = isExpired;
    }

    public boolean isExpired() {
      return isExpired;
    }
  }
}
