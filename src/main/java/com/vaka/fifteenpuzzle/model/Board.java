package com.vaka.fifteenpuzzle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.vaka.fifteenpuzzle.util.exception.TileMoveException;
import com.vaka.fifteenpuzzle.util.exception.TileNotExistsException;

/**
 * @author i.vakatsiienko.
 */
public class Board {

  public static final int EMPTY_TILE_NUMBER = 0;
  private final List<Integer> grid;
  private final Integer gridWidth;

  private Board(List<Integer> grid, Integer gridWidth) {
    this.grid = new ArrayList<>(grid);
    this.gridWidth = gridWidth;
  }

  public static Board of(List<Integer> tileList, Integer gridWidth) {
    int expectedTilesAmount = gridWidth * gridWidth;
    if (gridWidth < 0 || expectedTilesAmount != tileList.size())
      throw new IllegalArgumentException(String.format("The grid width and tile list doesn't match to each other. Grid width: %s, tile list: %s", gridWidth, tileList));

    validateContentOfTileList(tileList);
    return new Board(tileList, gridWidth);
  }

  private static void validateContentOfTileList(List<Integer> tileList) {
    Set<Integer> expectedTiles = IntStream.range(1, tileList.size()).boxed().collect(Collectors.toSet());
    expectedTiles.add(EMPTY_TILE_NUMBER);

    Set<Integer> givenTilesWithoutOrder = new HashSet<>(tileList);
    if (!givenTilesWithoutOrder.equals(expectedTiles))
      throw new IllegalArgumentException(String.format("The content of tile list isn't correct. Tile list: %s", tileList));
  }

  public List<Integer> toTileList() {
    return Collections.unmodifiableList(grid);
  }

  public void moveTile(Integer tileToMove) {
    validateMove(tileToMove);
    int emptyCellIndex = grid.indexOf(EMPTY_TILE_NUMBER);
    int movingTileIndex = grid.indexOf(tileToMove);
    grid.set(emptyCellIndex, tileToMove);
    grid.set(movingTileIndex, EMPTY_TILE_NUMBER);
  }

  private void validateMove(int tileToMoveNumber) {
    int tileToMoveIndex = grid.indexOf(tileToMoveNumber);
    if (tileToMoveIndex == -1)
      throw new TileNotExistsException(tileToMoveNumber);
    if (!getAvailableToMoveIndexes().contains(tileToMoveIndex))
      throw new TileMoveException(tileToMoveNumber);
  }

  private Set<Integer> getAvailableToMoveIndexes() {
    int emptyTileIndex = grid.indexOf(EMPTY_TILE_NUMBER);
    return Stream.of(emptyTileIndex - 1, emptyTileIndex + 1, emptyTileIndex - gridWidth, emptyTileIndex + gridWidth)
        .filter(index -> index >= 0 && index < grid.size())
        .collect(Collectors.toSet());
  }

  public Integer getGridWidth() {
    return gridWidth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Board board = (Board) o;

    return Objects.equals(gridWidth, board.gridWidth) && Objects.equals(grid, board.grid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gridWidth, grid);
  }
}
