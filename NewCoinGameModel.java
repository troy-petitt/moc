package cs3500.hw07;

/**
 * A coin game. The rules of a particular coin game will be implemented
 * by classes that implement this interface.
 */
public interface NewCoinGameModel {
  /**
   * Gets the size of the board (the number of squares)
   *
   * @return the board size
   */
  int boardSize();

  /**
   * Gets the number of coins.
   *
   * @return the number of coins
   */
  int coinCount();

  /**
   * Gets the (zero-based) positions of the coins, in order. The resulting
   * array has length {@code coinCount()} and its elements are strictly
   * ascending. Modifying the resulting array must have no effect on the game
   * model.
   *
   * @return the coins' positions
   */
  int[] getCoinPositions();

  /**
   * Gets the play order as an array. This array should not reflect the
   * current position in the play order, but rather be fixed during game play
   * except when new players are added. Modifying the resulting array must
   * have no effect on the game model.
   *
   * @return the play order
   */
  CoinGamePlayer[] getPlayOrder();

  /**
   * Returns the winning player, or {@code null} if the game isn't over yet.
   *
   * @return the winning player or {@code null}
   */
  CoinGamePlayer getWinner();

  /**
   * Returns the player to move next, or {@code null} if the game is over.
   *
   * @return the current player or {@code null}
   */
  CoinGamePlayer getCurrentPlayer();

  /**
   * Adds a new player to the game, returning the {@link CoinGamePlayer}
   * object representing the new player. The position of the player in the
   * game order is specified by passing the player who should precede the new
   * player.
   *
   * @param predecessor the player to insert the new player after (not null)
   * @param name the name of the new player (not null)
   * @return the new player
   * @throws NullPointerException if either parameter is null
   * @throws IllegalArgumentException
   *     if {@code predecessor} is not a player in this game's play order.
   */
  CoinGamePlayer addPlayerAfter(CoinGamePlayer predecessor, String name);
}
