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

public class paneLastCrop {
	//10 parmak izinin en son geldiði ekranda kýrpma yapmak için kullanýlan fonksiyon
    Rectangle rectBound = new Rectangle(0, 0);
    ImageView imageView;

    public ImageView cropAndGetImageView() {
        PixelReader reader =imageView.getImage().getPixelReader();
        int x = (int) rectBound.getLayoutX()/2;
        int y = (int) rectBound.getLayoutY()/2;
        int w = (int) rectBound.getWidth()/2;
        int h =  (int) rectBound.getHeight()/2;
        WritableImage newImage = new WritableImage(reader,x,
                y, w, h);
        ImageView res = new ImageView(newImage);
        res.setLayoutX(0.0);
        res.setLayoutY(0.0);
        res.setFitWidth ((newImage.getWidth()) * 2);
        res.setFitHeight((newImage.getHeight()) * 2);

        return res;
    }

    public paneLastCrop(Pane pane, Image image) {
        imageView = new ImageView(image);
        rectBound.setFill(Color.TRANSPARENT);
        rectBound.setStroke(Color.RED);
        rectBound.setWidth(250.0);
        rectBound.setHeight(250.0);
        rectBound.setLayoutX(15);
        rectBound.setLayoutY(15); // setX or setY
        pane.getChildren().add(rectBound);
        pane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    rectBound.setWidth(event.getX() - rectBound.getLayoutX());
                    rectBound.setHeight(event.getY() - rectBound.getLayoutY());
                } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED
                        && event.getButton() == MouseButton.SECONDARY) {
                    if (rectBound.getParent() != null) {
                        rectBound.setLayoutX(event.getX());
                        rectBound.setLayoutY(event.getY());
                    }
                } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() > 1) {
                }
            }
        });
    }
}