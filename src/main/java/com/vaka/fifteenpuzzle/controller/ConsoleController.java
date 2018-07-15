package com.vaka.fifteenpuzzle.controller;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.vaka.fifteenpuzzle.model.Board;
import com.vaka.fifteenpuzzle.service.GameService;
import com.vaka.fifteenpuzzle.service.impl.DrawServiceImpl;
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
      printInitialMessages(consoleWriter);
      consoleWriter.println(drawService.draw(board));
      do {
        makeGameMove(board, consoleWriter, consoleScanner);
        consoleWriter.println(drawService.draw(board));
      } while (!gameService.isPuzzleSolved(board));
      consoleWriter.println(drawService.getPuzzleIsSolvedMessage());
    }
  }

  private static void makeGameMove(Board board, PrintStream consoleWriter, Scanner consoleScanner) {
    int newTileNumber = getCorrectInput(consoleScanner, consoleWriter);
    moveTile(board, newTileNumber, consoleWriter, consoleScanner);
  }

  private static int getCorrectInput(Scanner scanner, PrintStream writer) {
    try {
      return scanner.nextInt();
    } catch (InputMismatchException e) {
      writer.println(drawService.getIncorrectInputMessage(scanner.nextLine()));
      return getCorrectInput(scanner, writer);
    } catch (NoSuchElementException e) {
      return getCorrectInput(scanner, writer);
    }
  }

  private static void moveTile(Board board, int tileNumber, PrintStream consoleWriter, Scanner consoleScanner) {
    try {
      board.moveTile(tileNumber);
    } catch (TileMoveException e) {
      consoleWriter.println(drawService.getTileIsNotMovableMessage(e.getTileNumber()));
      makeGameMove(board, consoleWriter, consoleScanner);
    } catch (TileNotExistsException e) {
      consoleWriter.println(drawService.getTileNotExistMessage(e.getTileNumber()));
      makeGameMove(board, consoleWriter, consoleScanner);
    }
  }

  private static void printInitialMessages(PrintStream printStream) {
    printStream.println(drawService.getWelcomeMessage());
    printStream.println();
    printStream.println(drawService.getGameDescription());
    printStream.println();
    printStream.println(drawService.getAskToMoveMessage());
  }
}
