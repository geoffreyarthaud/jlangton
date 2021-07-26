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
	public static final long INIT_POS = W_MAP * H_MAP / 2 + W_MAP / 2;
	boolean hasLogged = false;


	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {

		final Canvas canvas = new Canvas(W_SCREEN, H_SCREEN);
		final RectLangtonMapImpl langtonMap = new RectLangtonMapImpl(W_MAP, H_MAP);
		final BitSetLangtonPathImpl path = new BitSetLangtonPathImpl();
		//LangtonPathToMap pathToMap = new LangtonPathToMap(langtonMap);
		final LangtonAnt langtonAnt = new LangtonAnt(langtonMap, path);
		final AnimationTimer timer = new AnimationTimer() {
			long langtonPos = INIT_POS;
			long iteration = 0;
			int delta = 8;

			@Override
			public void handle(long now) {
				final GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setFill(Color.WHITE);
				gc.fillRect(0, 0, W_SCREEN, H_SCREEN);
				gc.setFill(Color.FORESTGREEN);
				for (int coord = 0; coord < W_MAP * H_MAP; coord++) {
					if (langtonMap.getGrid().get(coord)) {
						gc.fillRect(coord % W_MAP * RATIO, W_SCREEN - RATIO - coord / W_MAP * RATIO, RATIO, RATIO);
					}
				}
				gc.fillText("n = " + iteration, 30, 30);
				gc.fillText("Cycles = " + path.getNbBackCycles(), 30, 50);
				gc.fillText("Cycle started at = " + path.getBackCyclingStart(), 30, 60);
				if (path.getNbBackCycles() > 0) {
					gc.fillText("Cycle code = " + path.getPath(path.getBackCyclingStart() - 103, 104), 30, 70);
					if (!hasLogged) {
						System.out.println(path.getPath(path.getBackCyclingStart() - 103, 104));
						hasLogged = true;
					}
					
				}
				gc.setFill(Color.BLACK);
				gc.fillRect(langtonPos % W_MAP * RATIO, W_SCREEN - RATIO - langtonPos / W_MAP * RATIO, RATIO, RATIO);
				langtonPos = langtonAnt.back(langtonPos, delta);
				iteration += delta;
			}
		};

		stage.setScene(new Scene(new Group(canvas)));
		stage.show();
		timer.start();
	}
}
