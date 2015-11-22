package cs3500.hw07;

import java.util.List;

public final class StrictCoinGameModel implements CoinGameModel {
  // (Exercise 2) Declare the fields needed to support the methods in
  // the interface you’ve designed:
  private boolean[] board;
  private List<String> players;
  private int currentIdx;

  // (Exercise 3) Describe, as precisely as you can, your
  // representation’s class invariants:
  // NOTE: THESE ARE ALL/WOULD BE GUARANTEED BY
  // THE CONSTRUCTOR AND PRESERVED BY THE PUBLIC METHODS
  /*
   * 0 <= currentIdx < players.size()
   * players is not null.
   * Every String in players is not null.
   * No two Strings in players are equal.
   * boardSize() >= 2
   * players.size() >= 2
   */

  // (Exercise 4) Describe your constructor API here by filling in
  // whatever arguments you need and writing good Javadoc. (You may
  // declare any combination of constructors and static factory
  // methods that you like, but you need not get fancy.)
  /**
   * Constructs an instance of {@code StrictCoinGameModel}.
   * Throws {@code IllegalArgumentException} if the arguments are nonsensical,
   * which can happen in several ways:
   *
   * <ul>
   *   <li>{@code boardSize() <= coinCount()}
   *   <li>{@code coinCount() < 2}
   *   <li>{@code players.size() < 2}
   *   <li>{@code players == null}
   *   <li>{@code boardConfig == null}
   *   <li>there are any duplicate names in {@code Players}
   *   <li>any String in {@code players} is null
   * </ul>
   *
   * For this reason, clients are permitted
   * to try to create {@code StrictCoinGameModel}s.
   *
   * @param players the list of players
   * @param boardConfig the input board
   */
  public StrictCoinGameModel(List<String> players, String boardConfig) {
    if (boardConfig == null) {
      throw new IllegalArgumentException("Bad input.");
    } else if (constCoinCount(boardConfig) < 2) {
      throw new IllegalArgumentException("Bad input.");
    } else if (players == null) {
      throw new IllegalArgumentException("Bad input.");
    } else if (players.size() < 2) {
      throw new IllegalArgumentException("Bad input.");
    } else if (hasDupsOrNull(players)) {
      throw new IllegalArgumentException("Bad input.");
    } else {
      this.players = players;
      this.currentIdx = 0;
      board = new boolean[boardConfig.length()];
      for (int i = 0; i < boardConfig.length(); i++) {
        if (boardConfig.charAt(i) == 'O') {
          board[i] = true;
        } else if (boardConfig.charAt(i) == '-') {
          board[i] = false;
        } else {
          throw new IllegalArgumentException("Bad input.");
        }
      }
    }
  }

  // You don't need to implement any methods or constructors. However,
  // if you want to make sure your code compiles, you could have your
  // IDE generate stubs for all the missing methods. This would also
  // allow you to make sure that your tests in StrictCoinGameModelTest
  // actually type check and compile against this class (though you
  // don’t need to make them pass, because you don’t need to implement
  // StrictCoinGameModel’s methods).

  /**
   * Check for duplicate strings in nameList for
   * use in ensuring that the constructor guarantees
   * the fourth invariant listed above and that none of
   * the strings are null.
   *
   * @param nameList the list of names
   * @return whether the list of strings has duplicates
   */
  private boolean hasDupsOrNull(List<String> nameList) {
    for (int i = 0; i < nameList.size(); i++) {
      if (!(i == nameList.lastIndexOf(nameList.get(i))) ||
              nameList.get(i) == null) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int boardSize() {
    return board.length;
  }

  @Override
  public int coinCount() {
    int ans = 0;
    for (boolean i : board) {
      if (i) {
        ans++;
      }
    }
    return ans;
  }

  private static int constCoinCount(String boardConfig) {
    int ans = 0;
    for (int i = 0; i < boardConfig.length(); i++) {
      if (boardConfig.charAt(i) == 'O') {
        ans++;
      } else if (boardConfig.charAt(i) == '-') {

      } else {
        throw new IllegalArgumentException("Bad input.");
      }
    }
    return ans;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    if (coinIndex >= coinCount() || coinIndex < 0) {
      throw new IllegalArgumentException("Bad input.");
    }
    int ans = -1;
    for (int i = 0; coinIndex >= 0; i++) {
      if (board[i]) {
        coinIndex--;
      }
      ans++;
    }
    return ans;
  }

  @Override
  public boolean isGameOver() {
    boolean ans = true;
    for (int i = 0; i < coinCount(); i++) {
      if (!board[i]) {
        ans = false;
        break;
      }
    }
    return ans;
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    try {
      getCoinPosition(coinIndex);
    } catch (IllegalArgumentException e) {
      throw new IllegalMoveException("No such coin.");
    }
    if (newPosition < 0) {
      throw new IllegalMoveException("Not a valid space.");
    } else if (board[newPosition]) {
      throw new IllegalMoveException("Space already occupied.");
    } else if (newPosition >= getCoinPosition(coinIndex)) {
      throw new IllegalMoveException("Coins can only move left.");
    } else if (isGameOver()) {
      throw new IllegalMoveException("Game is already over.");
    } else if (coinIndex > 0 && getCoinPosition(coinIndex - 1) > newPosition) {
      throw new IllegalMoveException("Can't jump over coins.");
    } else {
      currentIdx = (currentIdx + 1) % players.size();
      board[getCoinPosition(coinIndex)] = false;
      board[newPosition] = true;
    }
  }

  @Override
  public String winner() {
    if (!isGameOver()) {
      throw new IllegalMoveException("Game not over yet");
    } else if (currentIdx == 0) {
      return players.get(players.size() - 1);
    } else {
      return players.get((currentIdx - 1));
    }
  }

  @Override
  public String whoseTurn() {
    if (isGameOver()) {
      throw new IllegalMoveException("Game is over.");
    }
    return players.get(currentIdx);
  }

  @Override
  public void addPlayer(String name, int index) {
    if (index > players.size() || index < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    players.add(index, name);
    if (index == currentIdx) {
      currentIdx++;
    }
    if (hasDupsOrNull(players)) {
      throw new IllegalArgumentException("Name is duplicate or null.");
    }
  }

  @Override
  public int playerCount() {
    return players.size();
  }

  @Override
  public String getPlayerAt(int index) {
    if (index >= playerCount()) {
      throw new IndexOutOfBoundsException("Index is out of bounds.");
    }
    return players.get(index);
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < boardSize(); i++) {
      if (board[i]) {
        str.append('O');
      } else {
        str.append('-');
      }
    }
    return str.toString();
  }

}
