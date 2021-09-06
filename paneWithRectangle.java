package application;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class paneWithRectangle {
	//ilk kýrpma yapýlan fonksiyon
	ImageView imageview, desimageview, desimageview2, desimageview3, desimageview4, desimageview5, desimageview6,
			desimageview7, desimageview8, desimageview9, desimageview10, desimageview11, desimageview12, desimageview13,
			desimageview14;
	Image result;
	ImageView imageviews[] = { desimageview, desimageview2, desimageview3, desimageview4, desimageview5, desimageview6,
			desimageview7, desimageview8, desimageview9, desimageview10, desimageview11, desimageview12, desimageview13,
			desimageview14 };

	public Image getResult() {
		this.result = desimageview.getImage();
		return result;
	}

	Rectangle rectBound1 = new Rectangle(0, 0);
	Rectangle rectBound2 = new Rectangle(0, 0);
	Rectangle rectBound3 = new Rectangle(0, 0);
	Rectangle rectBound4 = new Rectangle(0, 0);
	Rectangle rectBound5 = new Rectangle(0, 0);
	Rectangle rectBound6 = new Rectangle(0, 0);
	Rectangle rectBound7 = new Rectangle(0, 0);
	Rectangle rectBound8 = new Rectangle(0, 0);
	Rectangle rectBound9 = new Rectangle(0, 0);
	Rectangle rectBound10 = new Rectangle(0, 0);
	Rectangle rectBound11 = new Rectangle(0, 0);
	Rectangle rectBound12 = new Rectangle(0, 0);
	Rectangle rectBound13 = new Rectangle(0, 0);
	Rectangle rectBound14 = new Rectangle(0, 0);
	Rectangle rectBounds[] = { rectBound1, rectBound2, rectBound3, rectBound4, rectBound5, rectBound6, rectBound7,
			rectBound8, rectBound9, rectBound10, rectBound11, rectBound12, rectBound13, rectBound14 };

	public ImageView[] cropAndGetImageView() {
		PixelReader reader = imageview.getImage().getPixelReader();
		for (int i = 0; i < 14; i++) {
			WritableImage newImage = new WritableImage(reader, (int) rectBounds[i].getLayoutX(),
					(int) rectBounds[i].getLayoutY(), (int) rectBounds[i].getWidth(), (int) rectBounds[i].getHeight());
			imageviews[i] = new ImageView(newImage);
			imageviews[i].setLayoutX(0.0);
			imageviews[i].setLayoutY(0.0);

		}
		return imageviews;
	}

	public ImageView[] cropAndGetTenFinger() {
		PixelReader reader = imageview.getImage().getPixelReader();
		for (int i = 0; i < 10; i++) {
			WritableImage newImage = new WritableImage(reader, (int) rectBounds[i].getLayoutX(),
					(int) rectBounds[i].getLayoutY(), (int) rectBounds[i].getWidth(), (int) rectBounds[i].getHeight());
			imageviews[i] = new ImageView(newImage);
			imageviews[i].setLayoutX(0.0);
			imageviews[i].setLayoutY(0.0);
			

		}
		return imageviews;
	}

	int i = 0;

	public paneWithRectangle(Pane pane, String fileLocation, int imgwidth, int imgheight) {
		Image image = new Image(fileLocation, imgwidth, imgheight, false, false);
		imageview = new ImageView(image);
		for (int i = 0; i < 14; i++) {
			rectBounds[i].setFill(Color.TRANSPARENT);
			rectBounds[i].setStroke(Color.RED);
		}
		pane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getX() < 200 && event.getY() < 150)
					i = 0;
				if (200 < event.getX() && event.getX() < 400 && event.getY() < 150)
					i = 1;
				if (400 < event.getX() && event.getX() < 600 && event.getY() < 150)
					i = 2;
				if (600 < event.getX() && event.getX() < 800 && event.getY() < 150)
					i = 3;
				if (800 < event.getX() && event.getX() < 1000 && event.getY() < 150)
					i = 4;
				if (event.getX() < 200 && event.getY() < 300 && event.getY() > 150)
					i = 5;
				if (200 < event.getX() && event.getX() < 400 && event.getY() < 300 && event.getY() > 150)
					i = 6;
				if (400 < event.getX() && event.getX() < 600 && event.getY() < 300 && event.getY() > 150)
					i = 7;
				if (600 < event.getX() && event.getX() < 800 && event.getY() < 300 && event.getY() > 150)
					i = 8;
				if (800 < event.getX() && event.getX() < 1000 && event.getY() < 300 && event.getY() > 150)
					i = 9;
				if (event.getX() < 390 && event.getY() > 300)
					i = 10;
				if (event.getX() > 400 && event.getX() < 499 && event.getY() > 300)
					i = 11;
				if (event.getX() > 500 && event.getX() < 600 && event.getY() > 300)
					i = 12;
				if (event.getX() > 605 && event.getX() < 1000 && event.getY() > 300)
					i = 13;
				if (rectBound1.getParent() == null) {
					rectBound1.setWidth(170.0);
					rectBound1.setHeight(125.0);
					rectBound1.setLayoutX(15);
					rectBound1.setLayoutY(15); // setX or setY
					pane.getChildren().add(rectBound1);
					rectBound2.setWidth(170.0);
					rectBound2.setHeight(125.0);
					rectBound2.setLayoutX(200);
					rectBound2.setLayoutY(15); // setX or setY
					pane.getChildren().add(rectBound2);
					rectBound3.setWidth(170.0);
					rectBound3.setHeight(125.0);
					rectBound3.setLayoutX(400);
					rectBound3.setLayoutY(15); // setX or setY
					pane.getChildren().add(rectBound3);
					rectBound4.setWidth(170.0);
					rectBound4.setHeight(125.0);
					rectBound4.setLayoutX(600);
					rectBound4.setLayoutY(15); // setX or setY
					pane.getChildren().add(rectBound4);
					rectBound5.setWidth(170.0);
					rectBound5.setHeight(125.0);
					rectBound5.setLayoutX(800);
					rectBound5.setLayoutY(15); // setX or setY
					pane.getChildren().add(rectBound5);
					rectBound6.setWidth(170.0);
					rectBound6.setHeight(125.0);
					rectBound6.setLayoutX(15);
					rectBound6.setLayoutY(150); // setX or setY
					pane.getChildren().add(rectBound6);
					rectBound7.setWidth(170.0);
					rectBound7.setHeight(125.0);
					rectBound7.setLayoutX(200);
					rectBound7.setLayoutY(150); // setX or setY
					pane.getChildren().add(rectBound7);
					rectBound8.setWidth(170.0);
					rectBound8.setHeight(125.0);
					rectBound8.setLayoutX(400);
					rectBound8.setLayoutY(150); // setX or setY
					pane.getChildren().add(rectBound8);
					rectBound9.setWidth(170.0);
					rectBound9.setHeight(125.0);
					rectBound9.setLayoutX(600);
					rectBound9.setLayoutY(150); // setX or setY
					pane.getChildren().add(rectBound9);
					rectBound10.setWidth(170.0);
					rectBound10.setHeight(125.0);
					rectBound10.setLayoutX(800);
					rectBound10.setLayoutY(150); // setX or setY
					pane.getChildren().add(rectBound10);
					rectBound11.setWidth(350.0);
					rectBound11.setHeight(200.0);
					rectBound11.setLayoutX(15);
					rectBound11.setLayoutY(300); // setX or setY
					pane.getChildren().add(rectBound11);
					rectBound12.setWidth(90.0);
					rectBound12.setHeight(200.0);
					rectBound12.setLayoutX(400);
					rectBound12.setLayoutY(300); // setX or setY
					pane.getChildren().add(rectBound12);
					rectBound13.setWidth(90.0);
					rectBound13.setHeight(200.0);
					rectBound13.setLayoutX(500);
					rectBound13.setLayoutY(300); // setX or setY
					pane.getChildren().add(rectBound13);
					rectBound14.setWidth(350.0);
					rectBound14.setHeight(200.0);
					rectBound14.setLayoutX(600);
					rectBound14.setLayoutY(300); // setX or setY
					pane.getChildren().add(rectBound14);

				} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
				} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					rectBounds[i].setWidth(event.getX() - rectBounds[i].getLayoutX());
					rectBounds[i].setHeight(event.getY() - rectBounds[i].getLayoutY());
				} else if (event.getEventType() == MouseEvent.MOUSE_CLICKED
						&& event.getButton() == MouseButton.SECONDARY) {
					if (rectBounds[i].getParent() != null) {
						rectBounds[i].setLayoutX(event.getX());
						rectBounds[i].setLayoutY(event.getY());
					}
				} else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() > 1) {
					cropAndGetImageView();
				}
			}
		});
		pane.getChildren().add(imageview);
	}

}
