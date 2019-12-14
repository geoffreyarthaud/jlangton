package fr.cookiedev.jlangton.app;

import fr.cookiedev.jlangton.core.BitSetLangtonPathImpl;
import fr.cookiedev.jlangton.core.LangtonAnt;
import fr.cookiedev.jlangton.core.RectLangtonMapImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JLangtonApp extends Application {

	public static final int W_SCREEN = 1024;
	public static final int H_SCREEN = 1024;
	public static final int RATIO = 4;
	public static final int W_MAP = W_SCREEN / RATIO;
	public static final int H_MAP = H_SCREEN / RATIO;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {

		final Canvas canvas = new Canvas(W_SCREEN, H_SCREEN);
		final RectLangtonMapImpl langtonMap = new RectLangtonMapImpl(W_MAP, H_MAP);
		final LangtonAnt langtonAnt = new LangtonAnt(langtonMap, new BitSetLangtonPathImpl());
		final AnimationTimer timer = new AnimationTimer() {
			long langtonPos = W_MAP * H_MAP / 2 + W_MAP / 2;
			long iteration = 0;
			int delta = 4;

			@Override
			public void handle(long now) {
				final GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setFill(Color.WHITE);
				gc.fillRect(0, 0, W_SCREEN, H_SCREEN);
				gc.setFill(Color.FORESTGREEN);
				for (int coord = 0; coord < W_MAP * H_MAP; coord++) {
					if (langtonMap.getGrid().get(coord)) {
						gc.fillRect(coord % W_MAP * RATIO, coord / W_MAP * RATIO, RATIO, RATIO);
					}
				}
				gc.fillText("n = " + iteration, 30, 30);
				langtonPos = langtonAnt.move(langtonPos, delta);
				iteration += delta;
			}
		};

		stage.setScene(new Scene(new Group(canvas)));
		stage.show();
		timer.start();
	}
}
