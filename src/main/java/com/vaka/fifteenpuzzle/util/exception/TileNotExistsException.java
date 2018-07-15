package com.vaka.fifteenpuzzle.util.exception;

/**
 * @author i.vakatsiienko.
 */
public class TileNotExistsException extends TileOperationException {

  public TileNotExistsException(Integer tileNumber) {
    super(tileNumber);
  }
}
