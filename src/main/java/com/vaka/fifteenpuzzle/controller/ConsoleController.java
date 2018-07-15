package com.vaka.fifteenpuzzle.controller;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.vaka.fifteenpuzzle.model.Board;
import com.vaka.fifteenpuzzle.service.impl.DrawServiceImpl;
import com.vaka.fifteenpuzzle.service.GameService;
import com.vaka.fifteenpuzzle.service.impl.GameServiceImpl;
import com.vaka.fifteenpuzzle.util.exception.TileMoveException;
import com.vaka.fifteenpuzzle.util.exception.TileNotExistsException;

/**
 * @author i.vakatsiienko.
 */
public class ConsoleController {

  private static final int GRID_WIDTH = 4;
  private static GameService gameService = new GameServiceImpl();
  private static DrawServiceImpl drawService = new DrawServiceImpl();

  public static void main(String[] args) {
    try (Scanner consoleScanner = new Scanner(System.in);
         PrintStream consoleWriter = System.out) {

      Board board = gameService.createGame(GRID_WIDTH);
      printInitialMessages(consoleWriter, board);
      do {
        int tileNumber = catchIncorrectInput(consoleScanner, consoleWriter);
        catchImpossibleMove(board, tileNumber, consoleWriter);
        consoleWriter.println(drawService.draw(board));
      } while (!gameService.isPuzzleSolved(board));
      consoleWriter.println(drawService.getPuzzleIsSolvedMessage());
    }
  }

  private static void printInitialMessages(PrintStream printStream, Board board) {
    printStream.println(drawService.getWelcomeMessage());
    printStream.println();
    printStream.println(drawService.getGameDescription());
    printStream.println();
    printStream.println(drawService.draw(board));
    printStream.println(drawService.getAskToMoveMessage());
  }

  private static void catchImpossibleMove(Board board, int tileNumber, PrintStream consoleWriter) {
    try {
      board.moveTile(tileNumber);
    } catch (TileMoveException e) {
      consoleWriter.println(drawService.getTileIsNotMovableMessage(e.getTileNumber()));
    } catch (TileNotExistsException e) {
      consoleWriter.println(drawService.getTileNotExistMessage(e.getTileNumber()));
    }
  }

  private static int catchIncorrectInput(Scanner scanner, PrintStream writer) {
    try {
      return scanner.nextInt();
    } catch (InputMismatchException e) {
      writer.println(drawService.getIncorrectInputMessage(scanner.nextLine()));
      return catchIncorrectInput(scanner, writer);
    } catch (NoSuchElementException e) {
      return catchIncorrectInput(scanner, writer);
    }
  }

}
