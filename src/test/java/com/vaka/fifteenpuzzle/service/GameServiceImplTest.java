package com.vaka.fifteenpuzzle.service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.vaka.fifteenpuzzle.model.Board;
import com.vaka.fifteenpuzzle.service.impl.GameServiceImpl;

/**
 * @author i.vakatsiienko.
 */
public class GameServiceImplTest {

  private GameService sut = new GameServiceImpl();

  @Test
  public void testCreateGame_withProperTilesGenerated() throws Exception {
    // given
    final Integer gridWidth = 10;
    Set<Integer> expected = IntStream.range(1, gridWidth * gridWidth).boxed().collect(Collectors.toSet());
    expected.add(Board.EMPTY_TILE_NUMBER);

    // when
    final Board actual = sut.createGame(gridWidth);

    // then
    Assert.assertEquals(gridWidth, actual.getGridWidth());
    Assert.assertEquals("Expected to have the same number of tiles", actual.toTileList().size(), expected.size());
    Assert.assertEquals("Expected to contain all tiles", new HashSet<>(actual.toTileList()), expected);
  }

  @Test
  public void testCreateGame_that2GeneratedBoardsAreDifferent() throws Exception {
    // given
    final Integer gridWidth = 20;

    // when
    final Board randomBoard1 = sut.createGame(gridWidth);
    final Board randomBoard2 = sut.createGame(gridWidth);

    // then
    Assert.assertNotEquals("Expected to generate different tileLists", randomBoard1.toTileList(), randomBoard2.toTileList());
  }

  @Test
  public void testCreateGame_thatGeneratedBoardIsSolvable() throws Exception {
    // given
    final Integer gridWidth = 20;

    // when
    final Board actual = sut.createGame(gridWidth);

    // then
    Assert.assertTrue("Expected that generated board is solvable", isSolvable(actual));
  }

  private boolean isSolvable(Board board) {
    int inversionCount = countInversions(board.toTileList());
    boolean isSolvable;
    if (isEven(board.getGridWidth())) {
      isSolvable = (!isEven(getEmptyTileRowNumberFromTheBottom(board))) == (isEven(inversionCount));
    } else {
      isSolvable = isEven(inversionCount);
    }
    return isSolvable;
  }

  private int countInversions(List<Integer> tileList) {
    return (int) IntStream.range(0, tileList.size() - 1)
        .filter(i -> tileList.get(i) != Board.EMPTY_TILE_NUMBER)
        .flatMap(i ->
            IntStream.range(i + 1, tileList.size())
                .filter(j -> tileList.get(i) > tileList.get(j))
                .filter(j -> tileList.get(j) != Board.EMPTY_TILE_NUMBER))
        .count();
  }

  private int getEmptyTileRowNumberFromTheBottom(Board board) {
    return board.getGridWidth() - board.toTileList().indexOf(Board.EMPTY_TILE_NUMBER) / board.getGridWidth();
  }

  private boolean isEven(int x) {
    return x % 2 == 0;
  }

  @Test
  public void testIsPuzzleSolved_solved() throws Exception {
    // given
    int gridWidth = 4;
    List<Integer> tileList = IntStream.range(1, gridWidth * gridWidth).boxed().collect(Collectors.toList());
    tileList.add(Board.EMPTY_TILE_NUMBER);
    Board board = Board.of(tileList, gridWidth);

    // when
    final boolean actual = sut.isPuzzleSolved(board);

    // then
    Assert.assertTrue("Expected puzzle to be solved", actual);
  }

  @Test
  public void testIsPuzzleSolved_notSolvedEmptyTileIsNotInTheEnd() throws Exception {
    // given
    int gridWidth = 4;
    List<Integer> tileSet = IntStream.range(0, gridWidth * gridWidth).boxed().collect(Collectors.toList());
    Board board = Board.of(tileSet, gridWidth);

    // when
    final boolean actual = sut.isPuzzleSolved(board);

    // then
    Assert.assertFalse("Expected puzzle to be not solved", actual);
  }

  @Test
  public void testIsPuzzleSolved_notSolvedThereAreInversions() throws Exception {
    // given
    int gridWidth = 4;
    List<Integer> tileList = IntStream.range(1, gridWidth * gridWidth).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    tileList.add(Board.EMPTY_TILE_NUMBER);
    Board board = Board.of(tileList, gridWidth);

    // when
    final boolean actual = sut.isPuzzleSolved(board);

    // then
    Assert.assertFalse("Expected puzzle to be not solved", actual);
  }
}