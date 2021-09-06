package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DrawController implements Initializable {
	//editleme ekraný
	ImageView imageDraw;
	public void setImageDraw(ImageView drawimg) {
		drawpane1.getChildren().add(drawimg);
	}
	@FXML
	private Pane drawpane1;
	@FXML
	private Pane anchorid3;
	@FXML
	private Canvas canvas1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	//Attach a scroll listener
	
}