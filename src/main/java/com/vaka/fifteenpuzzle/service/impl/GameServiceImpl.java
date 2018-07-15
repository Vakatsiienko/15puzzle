package com.vaka.fifteenpuzzle.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaka.fifteenpuzzle.model.Board;
import com.vaka.fifteenpuzzle.service.GameService;

/**
 * @author i.vakatsiienko.
 */
public class GameServiceImpl implements GameService {

  @Override
  public Board createGame(int gridWidth) {
    List<Integer> tileList = createTileList(gridWidth);
    do {
      Collections.shuffle(tileList);
    }
    while (!isSolvable(tileList, gridWidth));
    return Board.of(tileList, gridWidth);
  }

  private List<Integer> createTileList(int gridWidth) {
    int countOfCells = gridWidth * gridWidth;
    List<Integer> tileList = IntStream.range(1, countOfCells).boxed().collect(Collectors.toList());
    tileList.add(Board.EMPTY_TILE_NUMBER);
    return tileList;
  }


  @Override
  public boolean isPuzzleSolved(Board board) {
    List<Integer> tileList = board.toTileList();
    boolean emptyTileLast = tileList.get(tileList.size() - 1) == Board.EMPTY_TILE_NUMBER;
    return emptyTileLast && countInversions(tileList) == 0;
  }


  private boolean isSolvable(List<Integer> tileList, int gridWidth) {
    int inversionCount = countInversions(tileList);
    boolean isSolvable;
    if (isEven(gridWidth)) {
      isSolvable = (!isEven(getEmptyTileRowNumberFromTheBottom(tileList, gridWidth))) == (isEven(inversionCount));
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

  private int getEmptyTileRowNumberFromTheBottom(List<Integer> tileList, int gridWidth) {
    return gridWidth - tileList.indexOf(Board.EMPTY_TILE_NUMBER) / gridWidth;
  }

  private boolean isEven(int x) {
    return x % 2 == 0;
  }

}
