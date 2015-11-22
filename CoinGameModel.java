package cs3500.hw07;

/**
 * An interface for playing a coin game. The rules of a particular coin game
 * will be implemented by classes that implement this interface.
 */
public interface CoinGameModel {
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
   * Gets the (zero-based) position of coin number {@code coinIndex}.
   *
   * @param coinIndex which coin to look up
   * @return the coin's position
   * @throws IllegalArgumentException
   *     if there is no coin with the requested index
   */
  int getCoinPosition(int coinIndex);

  /**
   * Returns whether the current game is over. The game is over if there are
   * no valid moves.
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Moves coin number {@code coinIndex} to position {@code newPosition}.
   * Throws {@code IllegalMoveException} if the requested move is illegal,
   * which can happen in several ways:
   *
   * <ul>
   *   <li>There is no coin with the requested index.
   *   <li>The new position is occupied by another coin.
   *   <li>There is some other reason the move is illegal,
   *       as specified by the concrete game class.
   * </ul>
   *
   * Note that {@code coinIndex} refers to the coins as numbered from 0
   * to {@code coinCount() - 1}, not their absolute position on the board.
   * However, coins have no identity, so if one coin passes another, their
   * indices are exchanged. The leftmost coin is always coin 0, the next
   * leftmost is coin 1, and so on.
   *
   * @param coinIndex   which coin to move (numbered from the left)
   * @param newPosition where to move it to
   * @throws IllegalMoveException the move is illegal
   */
  void move(int coinIndex, int newPosition);

  /**
   * Returns the name of the winner of the coin game.
   * PRECONDITION: The game is over.
   * Throws {@code IllegalMoveException} if there is no winner.
   *
   * @return the name of the winning player
   * @throws IllegalMoveException if {@code !(isGameOver())}
   */
  String winner();

  /**
   * Returns the name of the current player.
   * PRECONDITION:  The game is not over.
   * Throws {@code IllegalMoveException} if the game is over.
   *
   * @return the name of the player whose turn it is
   * @throws IllegalMoveException if {@code isGameOver()}
   */
  String whoseTurn();

  /**
   * Add a player to the collection of players playing the coin game.
   * Throws {@code IllegalArgumentException} if the String name parameter
   * already exists in the list of names or if the added name is null.
   * Players after {@code index} are moved back one place in the order.
   *
   * @param name the name of the player to be added
   * @param index the index in players for the new player to be placed
   * @throws IllegalArgumentException if {@code contains(name)}
   * @throws IllegalArgumentException if {@code name == null}
   */
  void addPlayer(String name, int index);

  /**
   * Determines the number of players playing the game.
   *
   * @return the number of players
   */
  int playerCount();

  /**
   * Determines the String representation of the player at
   * the given index of the play order
   *
   * @return the String name of the player at that index
   * @throws IndexOutOfBoundsException if index is out of bounds
   */
  String getPlayerAt(int index);

  /**
   * The exception thrown by {@code move} when the requested move is illegal.
   *
   * <p>(Implementation Note: Implementing this interface doesn't require
   * "implementing" the {@code IllegalMoveException} class—it's already
   * implemented right here. Nesting a class within an interface is a way to
   * strongly associate that class with the interface, which makes sense here
   * because the exception is intended to be used specifically by
   * implementations and clients of this interface.)
   */
  static class IllegalMoveException extends IllegalArgumentException {
    /**
     * Constructs a illegal move exception with no description.
     */
    public IllegalMoveException() {
      super();
    }

    /**
     * Constructs a illegal move exception with the given description.
     *
     * @param msg the description
     */
    public IllegalMoveException(String msg) {
      super(msg);
    }
  }
}
