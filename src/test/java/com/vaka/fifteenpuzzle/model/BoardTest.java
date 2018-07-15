package com.vaka.fifteenpuzzle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.vaka.fifteenpuzzle.util.exception.TileMoveException;
import com.vaka.fifteenpuzzle.util.exception.TileNotExistsException;

/**
 * @author i.vakatsiienko.
 */
public class BoardTest {

  @Test
  public void testOf_tileListIsTheSameAsInTheInputTileList() throws Exception {
    // given
    List<Integer> inputTileList = createListWithEmptyTileInTheEnd(16);
    List<Integer> expectedTileList = new ArrayList<>(inputTileList);
    Board board = Board.of(inputTileList, 4);

    // when
    List<Integer> actualTileList = board.toTileList();

    // then
    Assert.assertEquals(expectedTileList, actualTileList);
  }

  @Test
  public void testOf_changesOfInputParameterAfterBoardCreationDoesNotChangeTheBoard() throws Exception {
    // given
    List<Integer> tileList = createListWithEmptyTileInTheEnd(16);
    Board board = Board.of(tileList, 4);

    // when
    tileList.add(7);
    List<Integer> afterChange = board.toTileList();

    // then
    Assert.assertNotEquals(tileList, afterChange);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOf_tileListContainsDuplicate() throws Exception {
    // given
    List<Integer> tileList = createListWithEmptyTileInTheEnd(15);
    tileList.add(4);

    // when
    Board.of(tileList, 4);

    // then
    // expecting exception
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOf_sequenceOfTileNumbersHasGap() throws Exception {
    // given
    List<Integer> tileList = createListWithEmptyTileInTheEnd(17);
    tileList.remove(4);

    // when
    Board.of(tileList, 4);

    // then
    // expecting exception
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOf_widthDoesNotMatchWithTheSizeOfTileList() throws Exception {
    // given
    List<Integer> tileList = createListWithEmptyTileInTheEnd(16);

    // when
    Board.of(tileList, 5);

    // then
    // expecting exception
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOf_whenWidthIsNegativeButSquareIsCorrect() throws Exception {
    // given
    List<Integer> tileList = createListWithEmptyTileInTheEnd(16);

    // when
    Board.of(tileList, -4);

    // then
    // expecting exception
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testToTileList_returnsUnmodifiableList() throws Exception {
    // given
    List<Integer> tileList = IntStream.range(0, 16).boxed().collect(Collectors.toList());
    Board sut = Board.of(tileList, 4);

    // when
    List<Integer> actualWithFollowingChane = sut.toTileList();
    actualWithFollowingChane.set(1, 1);

    // then
    // expecting exception
  }

  @Test
  public void testMoveTile() throws Exception {
    // given
    List<Integer> tileList = IntStream.range(0, 16).boxed().collect(Collectors.toList());
    Board board = Board.of(tileList, 4);
    List<Integer> expected = IntStream.range(0, 16).boxed().collect(Collectors.toList());
    expected.set(1, 5);
    expected.set(4, 1);
    expected.set(5, 4);

    // when
    board.moveTile(1);
    board.moveTile(5);
    board.moveTile(4);
    board.moveTile(1);

    // then
    Assert.assertEquals(expected, board.toTileList());

  }

  @Test(expected = TileNotExistsException.class)
  public void testMoveTile_doesNotContainTileToMove() throws Exception {
    // given
    List<Integer> tileList = IntStream.range(0, 16).boxed().collect(Collectors.toList());
    Board board = Board.of(tileList, 4);

    // when
    board.moveTile(44);

    // then
    // expecting exception
  }

  @Test(expected = TileMoveException.class)
  public void testMoveTile_givenTileIsNotMovable() throws Exception {
    // given
    List<Integer> tileList = createListWithEmptyTileInTheEnd(16);
    Board board = Board.of(tileList, 4);

    // when
    board.moveTile(6);

    // then
    // expecting exception
  }

  private List<Integer> createListWithEmptyTileInTheEnd(int tileAmount) {
    List<Integer> tileList = IntStream.range(1, tileAmount).boxed().collect(Collectors.toList());
    tileList.add(Board.EMPTY_TILE_NUMBER);
    return tileList;
  }
}