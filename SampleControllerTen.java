package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SampleControllerTen {
	@FXML
	private Pane despane1, despane2, despane3, despane4, despane5, despane6, despane7, despane8, despane9, despane10;

	@FXML
	private ImageView drawimg1, drawimg2, drawimg3, drawimg4, drawimg5, drawimg6, drawimg7, drawimg8, drawimg9,
			drawimg10;

	ImageView drawimgs[] = { drawimg1, drawimg2, drawimg3, drawimg4, drawimg5, drawimg6, drawimg7, drawimg8, drawimg9,
			drawimg10 };

	public void setDestImageview(ImageView[] imageviews) {
		Pane despanes[] = { despane1, despane2, despane3, despane4, despane5, despane6, despane7, despane8, despane9,
				despane10 };

		for (int i = 0; i < 10; i++) {
			imageviews[i].setLayoutX((despanes[i].prefWidth(0) - imageviews[i].getImage().getWidth()) / 2);
			imageviews[i].setLayoutY((despanes[i].prefHeight(0) - imageviews[i].getImage().getHeight()) / 2);
			despanes[i].getChildren().add(imageviews[i]);
			drawimgs[i] = new ImageView(imageviews[i].getImage());
			drawimgs[i].setFitWidth((imageviews[i].getImage().getWidth()) * 4);
			drawimgs[i].setFitHeight((imageviews[i].getImage().getHeight()) * 4);
		}

	}
}
