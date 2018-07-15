package com.vaka.fifteenpuzzle.service;

import com.vaka.fifteenpuzzle.model.Board;

/**
 * @author i.vakatsiienko.
 */
public interface DrawService {
  String draw(Board board);

  String getWelcomeMessage();

  String getGameDescription();

  String getAskToMoveMessage();

  String getIncorrectInputMessage(String inputMessage);

  String getPuzzleIsSolvedMessage();

  String getTileNotExistMessage(Integer tileNumber);

  String getTileIsNotMovableMessage(Integer tileNumber);
}