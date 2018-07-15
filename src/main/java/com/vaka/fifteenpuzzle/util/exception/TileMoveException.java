package com.vaka.fifteenpuzzle.util.exception;

/**
 * @author i.vakatsiienko.
 */
public class TileMoveException extends TileOperationException {

  public TileMoveException(Integer tileNumber) {
    super(tileNumber);
  }
}
