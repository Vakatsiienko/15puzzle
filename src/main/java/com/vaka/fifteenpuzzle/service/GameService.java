package com.vaka.fifteenpuzzle.service;

import com.vaka.fifteenpuzzle.model.Board;

/**
 * @author i.vakatsiienko.
 */
public interface GameService {

  Board createGame(int gridWidth);

  boolean isPuzzleSolved(Board board);
}
