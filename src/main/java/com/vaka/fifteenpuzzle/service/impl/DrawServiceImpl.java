package com.vaka.fifteenpuzzle.service.impl;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaka.fifteenpuzzle.model.Board;
import com.vaka.fifteenpuzzle.service.DrawService;
import com.vaka.fifteenpuzzle.util.Pair;

/**
 * @author i.vakatsiienko.
 */
public class DrawServiceImpl implements DrawService {
  private static final String EMPTY_TILE_REPRESENTATION = "~";
  private static final String GAME_DESCRIPTION = String.format("The 15-puzzle " +
      "is a sliding puzzle that consists of a frame of numbered tiles in random order with one tile missing.\r\n" +
      "The goal is to use your force make all numbers ordered and put empty tile(%s) to the end.", EMPTY_TILE_REPRESENTATION);
  private static final String WELCOME_MESSAGE = "Welcome Jedi!";
  private static final String ASK_TO_MOVE_MESSAGE = "Please, write the number of a tile which you want to move.";
  private static final String INCORRECT_INPUT_MESSAGE = "Entered value '%s' isn't a number. %s";
  private static final String PUZZLE_IS_SOLVED_MESSAGE = "Well done, Master! The Puzzle is solved!";
  private static final String TILE_IS_NOT_MOVABLE = "Tile %s can't be moved.";
  private static final String TILE_NOT_EXISTS = "Tile %s not found on the board.";

  @Override
  public String draw(Board board) {
    List<Integer> tileList = board.toTileList();
    int maxTileLength = Collections.max(tileList).toString().length();
    int gridWidth = board.getGridWidth();

    return IntStream.range(0, tileList.size())
        .mapToObj(i -> Pair.of(i, convertToGameRepresentation(i, tileList, maxTileLength)))
        .map(pair -> addDelimiter(pair, gridWidth))
        .collect(Collectors.joining());
  }

  private String fixedLengthString(String string, int length) {
    return String.format("%1$" + length + "s", string);
  }

  private String addDelimiter(Pair<Integer, String> indexWithStringOfTile, int gridWidth) {
    return indexWithStringOfTile.getLeft() % gridWidth == gridWidth - 1
        ? indexWithStringOfTile.getRight() + "\r\n" : indexWithStringOfTile.getRight() + " ";
  }

  private String convertToGameRepresentation(int indexToConvert, List<Integer> tileList, int maxTileLength) {
    String gameRepresentation = tileList.get(indexToConvert).equals(Board.EMPTY_TILE_NUMBER) ?
        EMPTY_TILE_REPRESENTATION : tileList.get(indexToConvert).toString();
    return fixedLengthString(gameRepresentation, maxTileLength);
  }

  @Override
  public String getWelcomeMessage() {
    return WELCOME_MESSAGE;
  }

  @Override
  public String getGameDescription() {
    return GAME_DESCRIPTION;
  }

  @Override
  public String getAskToMoveMessage() {
    return ASK_TO_MOVE_MESSAGE;
  }

  @Override
  public String getIncorrectInputMessage(String inputMessage) {
    return String.format(INCORRECT_INPUT_MESSAGE, inputMessage, ASK_TO_MOVE_MESSAGE);
  }

  @Override
  public String getPuzzleIsSolvedMessage() {
    return PUZZLE_IS_SOLVED_MESSAGE;
  }

  @Override
  public String getTileNotExistMessage(Integer tileNumber) {
    return String.format(TILE_NOT_EXISTS, tileNumber);
  }

  @Override
  public String getTileIsNotMovableMessage(Integer tileNumber) {
    return String.format(TILE_IS_NOT_MOVABLE, tileNumber);
  }
}
