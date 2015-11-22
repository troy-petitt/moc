package cs3500.hw07;

/**
 * One participant in a coin game.
 */
public interface CoinGamePlayer {
  /**
   * Returns the player's name.
   *
   * @return the name
   */
  String getName();

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition},
   * if the requested move is legal. Throws {@code IllegalMoveException} if
   * the requested move is illegal, which can happen in several ways:
   *
   * <ul>
   *   <li>There is no coin with the requested index.</li>
   *   <li>The new position is occupied by another coin.</li>
   *   <li>There is some other reason the move is illegal,
   *   as specified by the concrete game.</li>
   *   <li>It is not this player's turn to move ({@code !isTurn()}).
   * </ul>
   *
   * @param coinIndex   which coin to move (numbered from the left)
   * @param newPosition where to move it to
   * @throws IllegalArgumentException the move is illegal
   */
  void move(int coinIndex, int newPosition);

  /**
   * Determines whether it is this player's turn to move.
   *
   * @return whether it's this player's turn
   */
  boolean isTurn();
}
