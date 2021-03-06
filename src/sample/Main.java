package sample;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.iio.ImageFrame;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.io.File;
import java.util.Random;

public class Main extends Application {
    public static HBox queue = new HBox(15);

    public class TitleFrame extends ImageView{
        private ImageView thumbnail;
        private int width;
        private int height;

        public TitleFrame(String path) {
            Image thumbnail = new Image(path);
            this.height = 300;
            this.width = 200;
            this.thumbnail = new ImageView(thumbnail);
            this.thumbnail.setFitHeight(this.height);
            this.thumbnail.setFitWidth(this.width);
            this.thumbnail.getStyleClass().add("title");

            this.thumbnail.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                ImageView image = new ImageView(thumbnail);
                @Override
                public void handle(MouseEvent event) {
                    image.setFitWidth(200);
                    image.setFitHeight(300);
                    image.getStyleClass().add("title");
                    queue.getChildren().add(image);
                }
//
            });
        }
    }

    public String[] getImages(){
        File f = new File("C:\\Users\\Matt\\IdeaProjects\\CrunchyRoll-Prototype\\src\\sample\\img");
        return(f.list());
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label siteTitle = new Label("crunchyroll");
        TextFlow siteHeader = new TextFlow(siteTitle);
        siteHeader.getStyleClass().add("header");
        siteHeader.setTextAlignment(TextAlignment.CENTER);
        siteHeader.setLineSpacing(2);
        HBox contentBox = new HBox();

        Label popularLabel = new Label("popular now");
        popularLabel.getStyleClass().add("label-main");
        VBox popularContent = new VBox(popularLabel, contentBox);
        popularContent.getStyleClass().add("popular");
        String[] images = getImages();
        Random index = new Random();
        //Add 10 images to the contentBox
        for(int i = 0; i < 10; i++){
            contentBox.getChildren().add(new TitleFrame("sample/img/"+images[index.nextInt(images.length)]).thumbnail);
        }
        contentBox.getStyleClass().add("popular");

        VBox yourSection = new VBox(15);
        yourSection.getStyleClass().add("your-section");
        Label yourSectionHeader = new Label("your crunchyroll");

        yourSectionHeader.getStyleClass().add("label-big-blue");
        yourSectionHeader.setAlignment(Pos.CENTER);
        yourSection.getChildren().addAll(yourSectionHeader);
        String[] titles = {"continue watching", "your queue", "recommended for you", "new episodes"};
        for(int i = 0; i < 3; i++){
            Label rowLabel = new Label(titles[i]);
            rowLabel.getStyleClass().add("label-sub");
            HBox content = new HBox(15);
            if(i != 1){
                for(int j = 0; j < 10; j++){
                    content.getChildren().add(new TitleFrame("sample/img/"+images[index.nextInt(images.length)]).thumbnail);
                }
                yourSection.getChildren().addAll(rowLabel,content);
            }
            else {
                yourSection.getChildren().addAll(rowLabel, queue);
            }
        }

        VBox parentVBox = new VBox(siteHeader, popularContent, yourSection);
        ScrollPane containerPane = new ScrollPane(parentVBox);
        primaryStage.setTitle("Crunchyroll");
        primaryStage.setScene(new Scene(containerPane, 1920, 1080));
        primaryStage.getScene().getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
