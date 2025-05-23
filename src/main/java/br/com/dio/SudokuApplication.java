package br.com.dio;

import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

import br.com.dio.ui.custom.screen.MainScreen;

public class SudokuApplication {

	public static void main(String[] args) {
        final var gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.buildMainScreen();
    }

}
