package cs3500.hw07;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of NewCoinGameModel that adapts a CoinGameModel.
 */
public class CoinGameModelAdapter implements NewCoinGameModel {
  /**
   * WARNING:
   * if adaptee is further mutated except through the adapter
   * interface, undefined behavior or exceptions could result
   */
  private final CoinGameModel adaptee;

  /**
   * Standard constructor for {@code CoinGameModelAdapter}
   *
   * @param adaptee the adaptee in the adapter pattern
   */
  public CoinGameModelAdapter(CoinGameModel adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * Creates a {@code CoinGameModel} adapter from a string representing
   * the board and an array of players according to the static
   * factory creation pattern.  Useful for testing.
   *
   * @param initialBoard the initial board setup
   * @param players      the initial list of players
   * @return a new {@code CoinGameModelAdapter}
   */
  public static CoinGameModelAdapter fromString(String initialBoard, String... players) {
    List<String> playersAsList = Arrays.asList(players);
    CoinGameModel adaptee = new StrictCoinGameModel(playersAsList, initialBoard);
    return new CoinGameModelAdapter(adaptee);
  }

  @Override
  public int boardSize() {
    return adaptee.boardSize();
  }

  @Override
  public int coinCount() {
    return adaptee.coinCount();
  }

  @Override
  public int[] getCoinPositions() {
    int count = adaptee.coinCount();
    int[] result = new int[count];
    for(int i = 0; i < count; ++i) {
      result[i] = adaptee.getCoinPosition(i);
    }
    return result;
  }

  @Override
  public CoinGamePlayer[] getPlayOrder() {
    int playerCount = adaptee.playerCount();
    CoinGamePlayer[] result = new CoinGamePlayer[playerCount];
    for (int i = 0; i < playerCount; i++) {
      result[i] = new CoinGamePlayerImpl(adaptee.getPlayerAt(i));
    }
    return result;
  }

  @Override
  public CoinGamePlayer getWinner() {
    if (!adaptee.isGameOver()) {
    return null;
    } else {
    return new CoinGamePlayerImpl(adaptee.winner());
    }
  }

  @Override
  public CoinGamePlayer getCurrentPlayer() {
    if (adaptee.isGameOver()) {
      return null;
    } else {
      return new CoinGamePlayerImpl(adaptee.whoseTurn());
    }
  }

  @Override
  public CoinGamePlayer addPlayerAfter(CoinGamePlayer predecessor, String name) {
    Objects.requireNonNull(predecessor);
    Objects.requireNonNull(name);
    for(int i = 0; i < adaptee.playerCount(); i++) {
      if(predecessor.getName().equals(adaptee.getPlayerAt(i))) {
        adaptee.addPlayer(name, i + 1);
        return new CoinGamePlayerImpl(name);
      }
    }
    throw new IllegalArgumentException("Unknown predecessor.");
  }

  /**
   * Implements {@code CoinGamePlayer}.
   */
  private class CoinGamePlayerImpl implements CoinGamePlayer {
    private final String name;

    /**
     * Constructs a new CoinGamePlayerImpl using the param name
     * @param name the name of the player
     */
    public CoinGamePlayerImpl(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      CoinGamePlayerImpl that = (CoinGamePlayerImpl) o;

      return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }

    @Override
    public void move(int coinIndex, int newPosition) {
      if (!isTurn()) {
        throw new IllegalArgumentException("Not this player's turn");
      }
      adaptee.move(coinIndex, newPosition);
    }

    @Override
    public boolean isTurn() {
      return getName().equals(adaptee.whoseTurn());
    }
  }
}
