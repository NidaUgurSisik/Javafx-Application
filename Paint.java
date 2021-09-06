package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import static application.Main.writableImage;
public class Paint implements Initializable {

	@FXML
	private Canvas canvas;
	
	@FXML
	private VBox btns;
	GraphicsContext gc;
	int heightOfCanvas;
	int widthOfCanvas;
	private Pair<Double, Double> initialTouch;

	public void screenshot() {
		SnapshotParameters params = new SnapshotParameters();
		heightOfCanvas = (int) canvas.getHeight(); // get Height for screenshot
		widthOfCanvas = (int) canvas.getWidth(); // get width for screenshot

		writableImage = new WritableImage(widthOfCanvas, heightOfCanvas); // new writableImage
		params.setFill(Color.TRANSPARENT); // trasparent fill
		canvas.snapshot(params, writableImage);

	}

	public void loadScreenshot() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear the canvas
		gc.drawImage(writableImage, 0, 0, widthOfCanvas, heightOfCanvas); // load image in
		gc = canvas.getGraphicsContext2D();
	}

	public void setStart(Image imgq) {
		Stage primaryStage = new Stage();
		Stack<Shape> undoHistory = new Stack();
		Stack<Shape> redoHistory = new Stack();
		Stack<Shape> lineStack = new Stack();

		/* ----------btns---------- */
		ToggleButton drowbtn = new ToggleButton("Draw");
		ToggleButton rubberbtn = new ToggleButton("Rubber");
		ToggleButton linebtn = new ToggleButton("Line");
		ToggleButton rectbtn = new ToggleButton("Rectange");
		ToggleButton circlebtn = new ToggleButton("Circle");
		ToggleButton elpslebtn = new ToggleButton("Ellipse");
		ToggleButton textbtn = new ToggleButton("Text");

		ToggleButton[] toolsArr = { drowbtn, rubberbtn, linebtn, rectbtn, circlebtn, elpslebtn, textbtn };

		ToggleGroup tools = new ToggleGroup();

		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		ColorPicker cpLine = new ColorPicker(Color.BLACK);
		ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);

		TextArea text = new TextArea();
		text.setPrefRowCount(1);

		Slider slider = new Slider(1, 50, 3);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);

		Label line_color = new Label("Line Color");
		Label fill_color = new Label("Fill Color");
		Label line_width = new Label("3.0");

		Button undo = new Button("Undo");
		Button redo = new Button("Redo");
		Button save = new Button("Save");
		Button open = new Button("Open");

		Button[] basicArr = { undo, redo, save, open };

		for (Button btn : basicArr) {
			btn.setMinWidth(90);
			btn.setCursor(Cursor.HAND);
			btn.setTextFill(Color.WHITE);
			btn.setStyle("-fx-background-color: #666;");
		}
		save.setStyle("-fx-background-color: #80334d;");
		open.setStyle("-fx-background-color: #80334d;");
		
		//VBox btns = new VBox(10);
		btns.getChildren().addAll(drowbtn, rubberbtn, linebtn, rectbtn, circlebtn, elpslebtn, textbtn, text, line_color,
				cpLine, fill_color, cpFill, line_width, slider, undo, redo, open, save);
		btns.setPadding(new Insets(5));
		btns.setStyle("-fx-background-color: #999");
		btns.setPrefWidth(100);

		/* ----------Drow Canvas---------- */
		// Canvas canvas = new Canvas(1080, 790);
		canvas.setWidth(imgq.getWidth() * 2.5);
		canvas.setHeight(imgq.getHeight() * 2.5);
		gc = canvas.getGraphicsContext2D();
		gc.drawImage(imgq, 0, 0, imgq.getWidth() * 2.5, imgq.getHeight() * 2.5);
		gc.setLineWidth(2);
		
		Rectangle rect = new Rectangle();
		Circle circ = new Circle();
		Ellipse elps = new Ellipse();
		Line line = new Line();
		canvas.setOnMousePressed(e -> {
			if (linebtn.isSelected()) {

				gc.setFill(Color.TRANSPARENT);
				screenshot();
				line.setStartX(e.getX());
				line.setStartY(e.getY());
				System.out.println("Start Drawing Line");
				initialTouch = new Pair<>(e.getX(), e.getY());

			} else if (drowbtn.isSelected()) {
				gc.setGlobalAlpha(0.005);
				gc.setStroke(cpLine.getValue());
				gc.beginPath();
				gc.lineTo(e.getX(), e.getY());
				gc.setGlobalAlpha(1);
			} else if (rubberbtn.isSelected()) {
				double lineWidth = gc.getLineWidth();
				gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
			} else if (rectbtn.isSelected()) {
				gc.setStroke(cpLine.getValue());
				gc.setFill(cpFill.getValue());
				rect.setX(e.getX());
				rect.setY(e.getY());
			} else if (circlebtn.isSelected()) {
				gc.setStroke(cpLine.getValue());
				gc.setFill(cpFill.getValue());
				circ.setCenterX(e.getX());
				circ.setCenterY(e.getY());
			} else if (elpslebtn.isSelected()) {
				gc.setStroke(cpLine.getValue());
				gc.setFill(cpFill.getValue());
				elps.setCenterX(e.getX());
				elps.setCenterY(e.getY());
			} else if (textbtn.isSelected()) {
				gc.setLineWidth(1);
				gc.setFont(Font.font(slider.getValue()));
				gc.setStroke(cpLine.getValue());
				gc.setFill(cpFill.getValue());
				gc.fillText(text.getText(), e.getX(), e.getY());
				gc.strokeText(text.getText(), e.getX(), e.getY());
			}
		});
		canvas.setOnMouseDragged(e -> {

			if(drowbtn.isSelected()) {
				gc.setGlobalAlpha(0.005);
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.setGlobalAlpha(1);
            }
			else if (linebtn.isSelected()) {
				loadScreenshot();
				line.setEndX(e.getX());
				line.setEndY(e.getY());
				gc.setGlobalAlpha(0.25);	//çizgi için transparanlýk ayarlama
				gc.strokeLine(initialTouch.getKey(), initialTouch.getValue(), e.getX(), e.getY()); // gets coordinate of
																									// second point
				lineStack.push(new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
				gc.setGlobalAlpha(1);
			} else if (rubberbtn.isSelected()) {
				double lineWidth = gc.getLineWidth();
				gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});

		canvas.setOnMouseReleased(e -> {
			if (linebtn.isSelected()) {
				screenshot();
				loadScreenshot();
				undoHistory.push(new Line(initialTouch.getKey(), initialTouch.getValue(), e.getX(), e.getY()));
			} else if (drowbtn.isSelected()) {
				gc.lineTo(e.getX(), e.getY());
				gc.setGlobalAlpha(0.005);
				gc.stroke();
				gc.closePath();
				gc.setGlobalAlpha(1);
			} else if (rubberbtn.isSelected()) {
				double lineWidth = gc.getLineWidth();
				gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
			} else if (rectbtn.isSelected()) {
				rect.setWidth(Math.abs((e.getX() - rect.getX())));
				rect.setHeight(Math.abs((e.getY() - rect.getY())));
				if (rect.getX() > e.getX()) {
					rect.setX(e.getX());
				}
				if (rect.getY() > e.getY()) {
					rect.setY(e.getY());
				}

				gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
				gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

				undoHistory.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));

			} else if (circlebtn.isSelected()) {
				circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);

				if (circ.getCenterX() > e.getX()) {
					circ.setCenterX(e.getX());
				}
				if (circ.getCenterY() > e.getY()) {
					circ.setCenterY(e.getY());
				}

				gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
				gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());

				undoHistory.push(new Circle(circ.getCenterX(), circ.getCenterY(), circ.getRadius()));
			} else if (elpslebtn.isSelected()) {
				elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
				elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));

				if (elps.getCenterX() > e.getX()) {
					elps.setCenterX(e.getX());
				}
				if (elps.getCenterY() > e.getY()) {
					elps.setCenterY(e.getY());
				}

				gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
				gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

				undoHistory
						.push(new Ellipse(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY()));
			}
			redoHistory.clear();
			Shape lastUndo = undoHistory.lastElement();
			lastUndo.setFill(gc.getFill());
			lastUndo.setStroke(gc.getStroke());
			lastUndo.setStrokeWidth(gc.getLineWidth());
		});
		cpLine.setOnAction(e -> {
			gc.setStroke(cpLine.getValue());
		});
		cpFill.setOnAction(e -> {
			gc.setFill(cpFill.getValue());
		});

		// slider
		slider.valueProperty().addListener(e -> {
			double width = slider.getValue();
			if (textbtn.isSelected()) {
				gc.setLineWidth(1);
				gc.setFont(Font.font(slider.getValue()));
				line_width.setText(String.format("%.1f", width));
				return;
			}
			line_width.setText(String.format("%.1f", width));
			gc.setLineWidth(width);
		});

		/*------- Undo & Redo ------*/
		// Undo
		undo.setOnAction(e -> {
			if (!undoHistory.empty()) {
				gc.clearRect(0, 0, 1080, 790);
				gc.drawImage(imgq, 0, 0, imgq.getWidth() * 2.5, imgq.getHeight() * 2.5);
				Shape removedShape = undoHistory.lastElement();
				if (removedShape.getClass() == Line.class) {
					Line tempLine = (Line) removedShape;
					tempLine.setFill(gc.getFill());
					tempLine.setStroke(gc.getStroke());
					tempLine.setStrokeWidth(gc.getLineWidth());
					redoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(),
							tempLine.getEndY()));

				} else if (removedShape.getClass() == Rectangle.class) {
					Rectangle tempRect = (Rectangle) removedShape;
					tempRect.setFill(gc.getFill());
					tempRect.setStroke(gc.getStroke());
					tempRect.setStrokeWidth(gc.getLineWidth());
					redoHistory.push(
							new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
				} else if (removedShape.getClass() == Circle.class) {
					Circle tempCirc = (Circle) removedShape;
					tempCirc.setStrokeWidth(gc.getLineWidth());
					tempCirc.setFill(gc.getFill());
					tempCirc.setStroke(gc.getStroke());
					redoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
				} else if (removedShape.getClass() == Ellipse.class) {
					Ellipse tempElps = (Ellipse) removedShape;
					tempElps.setFill(gc.getFill());
					tempElps.setStroke(gc.getStroke());
					tempElps.setStrokeWidth(gc.getLineWidth());
					redoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(),
							tempElps.getRadiusY()));
				}
				Shape lastRedo = redoHistory.lastElement();
				lastRedo.setFill(removedShape.getFill());
				lastRedo.setStroke(removedShape.getStroke());
				lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
				undoHistory.pop();

				for (int i = 0; i < undoHistory.size(); i++) {
					Shape shape = undoHistory.elementAt(i);
					if (shape.getClass() == Line.class) {
						Line temp = (Line) shape;
						gc.setLineWidth(temp.getStrokeWidth());
						gc.setStroke(temp.getStroke());
						gc.setFill(temp.getFill());
						gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
					} else if (shape.getClass() == Rectangle.class) {
						Rectangle temp = (Rectangle) shape;
						gc.setLineWidth(temp.getStrokeWidth());
						gc.setStroke(temp.getStroke());
						gc.setFill(temp.getFill());
						gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
						gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
					} else if (shape.getClass() == Circle.class) {
						Circle temp = (Circle) shape;
						gc.setLineWidth(temp.getStrokeWidth());
						gc.setStroke(temp.getStroke());
						gc.setFill(temp.getFill());
						gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
						gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
					} else if (shape.getClass() == Ellipse.class) {
						Ellipse temp = (Ellipse) shape;
						gc.setLineWidth(temp.getStrokeWidth());
						gc.setStroke(temp.getStroke());
						gc.setFill(temp.getFill());
						gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
						gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
					}
				}
			} else {
				System.out.println("there is no action to undo");
			}
		});

		// Redo
		redo.setOnAction(e -> {
			if (!redoHistory.empty()) {
				Shape shape = redoHistory.lastElement();
				gc.setLineWidth(shape.getStrokeWidth());
				gc.setStroke(shape.getStroke());
				gc.setFill(shape.getFill());

				redoHistory.pop();
				if (shape.getClass() == Line.class) {
					Line tempLine = (Line) shape;
					gc.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
					undoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(),
							tempLine.getEndY()));
				} else if (shape.getClass() == Rectangle.class) {
					Rectangle tempRect = (Rectangle) shape;
					gc.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
					gc.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());

					undoHistory.push(
							new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
				} else if (shape.getClass() == Circle.class) {
					Circle tempCirc = (Circle) shape;
					gc.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(),
							tempCirc.getRadius());
					gc.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(),
							tempCirc.getRadius());

					undoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
				} else if (shape.getClass() == Ellipse.class) {
					Ellipse tempElps = (Ellipse) shape;
					gc.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(),
							tempElps.getRadiusY());
					gc.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(),
							tempElps.getRadiusY());

					undoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(),
							tempElps.getRadiusY()));
				}
				Shape lastUndo = undoHistory.lastElement();
				lastUndo.setFill(gc.getFill());
				lastUndo.setStroke(gc.getStroke());
				lastUndo.setStrokeWidth(gc.getLineWidth());
			} else {
				System.out.println("there is no action to redo");
			}
		});

		/*------- Save & Open ------*/
		// Open
		open.setOnAction((e) -> {
			FileChooser openFile = new FileChooser();
			openFile.setTitle("Open File");
			File file = openFile.showOpenDialog(primaryStage);
			if (file != null) {
				try {
					InputStream io = new FileInputStream(file);
					Image img = new Image(io);
					gc.drawImage(img, 0, 0);
				} catch (IOException ex) {
					System.out.println("Error!");
				}
			}
		});

		// Save
		save.setOnAction((e) -> {
			screenshot();
			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		});
		//pane.setLeft(btns);
		//pane.setCenter(canvas);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}