/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2015 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej;

import java.io.File;
import java.io.IOException;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFX preloader for ImageJ.
 *
 * @author Curtis Rueden
 */
@SuppressWarnings("restriction")
public class ImageJPreloader extends Preloader {

	ProgressBar bar;
	Stage stage;

	// -- Preloader methods --

	@Override
	public void handleProgressNotification(final ProgressNotification pn) {
		t("handleProgressNotification");
		bar.setProgress(pn.getProgress());
	}

	@Override
	public void handleStateChangeNotification(final StateChangeNotification evt) {
		t("handleStateChangeNotification");
		if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
			stage.hide();
		}
	}

	// -- Application methods --

	@Override
	public void start(final Stage stage) throws Exception {
		t("start");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				t("jvm-shutdown");
			}
		});

		this.stage = stage;
		stage.setScene(createPreloaderScene());
		stage.show();
	}

	// -- Helper methods --

	private Scene createPreloaderScene() {
		bar = new ProgressBar();
		final BorderPane p = new BorderPane();
		p.setCenter(bar);
		return new Scene(p, 300, 150);
	}

	private void t(final String suffix) {
		try {
			new File("/Users/curtis/Desktop/jfx-" + suffix).createNewFile();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
