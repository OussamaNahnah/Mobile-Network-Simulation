package sample;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;


import javax.xml.stream.Location;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {
    //inisialisation
    int tailleZone = 100 * 7;
    int nbSB = 9;
    int nbNoeuds = 50;
    ArrayList<STATION_DE_BASE> listSB = null;
    ArrayList<NOEUD> listNoeuds = null;
    int nXn;
     int[][] tab;
    Path path;


    protected void reseau() {
        speed.setShowTickMarks(true);
        speed.setMin(1);
        speed.setMax(10);
        speed.setValue(5);
        speed.setShowTickLabels(true);
        speed.setShowTickMarks(true);
        listNoeuds = new ArrayList<NOEUD>();
        listSB = new ArrayList<STATION_DE_BASE>();
        for (int i = 0; i < nbNoeuds; i++) {
            int x = new Random().nextInt(tailleZone);
            int y = new Random().nextInt(tailleZone);
            listNoeuds.add(new NOEUD(i, x, y));

        }


         nXn = (int) Math.sqrt(nbSB);
        nbre_SBs.setText(""+nXn*nXn);
        tab = new int[nXn][nXn];
        int base = tailleZone / (nXn * 2);
        System.out.println("n " + nXn);
        System.out.println("nbSB" + nbSB);
        System.out.println("tailleZone" + tailleZone);
        System.out.println("base" + base);

        int id = 0;
        for (int i = base; i < tailleZone; i = i + 2 * base) {
            for (int j = base; j < tailleZone; j = j + 2 * base) {
                listSB.add(new STATION_DE_BASE(id, i, j));
                id++;
            }
        }
       ;
        int n = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = n;
                n++;
            }
        }


        for (int i = 0; i < listSB.size(); i++) {
            for (int j = 0; j < listNoeuds.size(); j++) {
                if (listSB.get(i).getX() + base >= listNoeuds.get(j).getX() && listSB.get(i).getX() - base <= listNoeuds.get(j).getX()) {
                    if (listSB.get(i).getY() + base >= listNoeuds.get(j).getY() && listSB.get(i).getY() - base <= listNoeuds.get(j).getY()) {
                        listSB.get(i).setNoeuds(listNoeuds.get(j));
                        listNoeuds.get(j).setSb(i);
                    }
                }

            }
        }

        for (int i = 0; i < nbSB; i++) {
           // add(new Circle(listSB.get(i).getX(), listSB.get(i).getY(), 20));
            Image station = new Image(getClass().getResourceAsStream("station.png"), 40, 40, true, true);
            ImageView imageView = new ImageView(station);
            imageView.setY(listSB.get(i).getY()-20);
            imageView.setX(listSB.get(i).getX()-20);
            add(imageView);
            affiche(" la station N=" + listSB.get(i).getId() + ", my x=" + listSB.get(i).getX() + " ,my y=" + listSB.get(i).getY() + " ,my" + listSB.get(i).getListeMesNoeuds().size() + " noeuds are :\n");
            int norme = 0;
            for (int j = 0; j < listSB.get(i).getListeMesNoeuds().size(); j++) {
                norme++;
                if (norme == 5) {
                    norme = 0;
                    affiche("\n");
                }

                affiche("noeude " + listSB.get(i).getListeMesNoeuds().get(j).getId() + " x: " +
                        listSB.get(i).getListeMesNoeuds().get(j).getX() + " y:" + listSB.get(i).getListeMesNoeuds().get(j).getY() + "\t ");
                Circle c = new Circle(listSB.get(i).getListeMesNoeuds().get(j).getX(), listSB.get(i).getListeMesNoeuds().get(j).getY(), 6);
                c.setFill(Color.GREEN);
                add(c);

            }
            affiche("\n\n");
        }
    }

    @FXML
    private Canvas lineCall;
    @FXML
    private Pane lines;
    @FXML
    private Pane space;
    @FXML
    private TextField nbre_SBs;
    @FXML
    private Slider speed;
    @FXML
    private TextField nbre_nodes;
    @FXML
    private Button apply;
    @FXML
    private CheckBox random;
    @FXML
    private TextArea log;
    @FXML
    private Button makeCall;

    protected void clear() {
        space.getChildren().clear();
    }

    protected void add(Node node) {
        space.getChildren().add(node);
    }

    protected void add(Node... nodes) {
        space.getChildren().addAll(nodes);
    }

    protected void affiche(String line) {
        log.appendText(line);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        nbre_nodes.setText("" + nbNoeuds);
        nbre_SBs.setText("" + nbSB);
        reseau();
        apply.setOnAction(this::apply);
        makeCall.setOnAction(this::makeCall);


    }

    private void apply(ActionEvent actionEvent) {
        try {
            nbSB = Integer.parseInt(nbre_SBs.getText());
            nbNoeuds = Integer.parseInt(nbre_nodes.getText());
        } catch (Exception e) {
            affiche("input errer");
        }

        lines.getChildren().clear();
        lineCall.getGraphicsContext2D().clearRect(0, 0, lineCall.getWidth(), lineCall.getHeight());
        clear();

        reseau();


    }

    private void makeCall(ActionEvent actionEvent) {
         makeCall .setDisable(true);
         apply.setDisable(true);
         speed.setDisable(true);


        lines.getChildren().clear();lineCall.getGraphicsContext2D().clearRect(0, 0, lineCall.getWidth(), lineCall.getHeight());
        Path path = createPath();
        lines.getChildren().add(path);
        Animation animation = createPathAnimation(path, Duration.seconds(speed.getValue()));
        animation.play();
        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                makeCall .setDisable(false);
                apply .setDisable(false);
                speed .setDisable(false);
            }
        });



    }

    private Path createPath() {
        int x1=0,y1=0,x2=0,y2=0;int a,b;
         a = new Random().nextInt(nbNoeuds);
        do {
            b = new Random().nextInt(nbNoeuds);
        }while (a==b);
        Image i1 = new Image(getClass().getResourceAsStream("loading.gif"), 30,30, true, true);
        ImageView A = new ImageView(i1);
        A.setX(listNoeuds.get(a).getX()-15);
        A.setY(listNoeuds.get(a).getY()-15);
        lines.getChildren().add(A);
        Label l1=new Label("A");
        l1.setLayoutX(listNoeuds.get(a).getX());
        l1.setLayoutY(listNoeuds.get(a).getY()+15);
        lines.getChildren().add(l1);
        Image i2 = new Image(getClass().getResourceAsStream("loading.gif"), 30,30, true, true);
        ImageView B = new ImageView(i2);
        B.setX(listNoeuds.get(b).getX()-15);
        B.setY(listNoeuds.get(b).getY()-15);
        lines.getChildren().add(B);
        Label l2=new Label("B");
        l2.setLayoutX(listNoeuds.get(b).getX());
        l2.setLayoutY(listNoeuds.get(b).getY()+15);
        lines.getChildren().add(l2);

         path = new Path();
        path.setStroke(Color.GRAY);
        path.setStrokeWidth(1);
        path.getElements().add(new MoveTo(listNoeuds.get(a).getX(), listNoeuds.get(a).getY()));
        path.getElements().add(new LineTo(listSB.get(listNoeuds.get(a).getSb()).getX(),listSB.get(listNoeuds.get(a).getSb()).getY()));
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {

                if(tab[i][j] ==listSB.get(listNoeuds.get(a).getSb()).getId()){
                    x1=i;
                    y1=j;
                }
                if(tab[i][j] ==listSB.get(listNoeuds.get(b).getSb()).getId()){
                    x2=i;
                    y2=j;
                }
            }
        }
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + "        ");

            }
            System.out.println();
        }
        System.out.println("A="+a);
        System.out.println("B="+b);
        System.out.println(x1 + "," + y1 + "  " + x2 + "," + y2);
        calcul_path(x1,y1,x2,y2);
       // path.getElements().add(new LineTo(listSB.get(listNoeuds.get(b).getSb()).getX(),listSB.get(listNoeuds.get(b).getSb()).getY()));
        path.getElements().add(new LineTo (listNoeuds.get(b).getX(), listNoeuds.get(b).getY()));



        return path;
    }

    private Animation createPathAnimation(Path path, Duration duration) {

        GraphicsContext gc = lineCall.getGraphicsContext2D();

        // move a node along a path. we want its position
        Circle pen = new Circle(0, 0, 3);

        // create path transition
        PathTransition pathTransition = new PathTransition( duration, path, pen);
        pathTransition.currentTimeProperty().addListener( new ChangeListener<Duration>() {

            Location oldLocation = null;

            /**
             * Draw a line from the old location to the new location
             */
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

                // skip starting at 0/0
                if( oldValue == Duration.ZERO){
                return;}

                // get current location
                double x = pen.getTranslateX();
                double y = pen.getTranslateY();

                // initialize the location
                if( oldLocation == null) {
                    oldLocation = new Location();
                    oldLocation.x = x;
                    oldLocation.y = y;
                    return;
                }

                // draw line
                gc.setStroke(Color.BLUE);
                gc.setFill(Color.YELLOW);
                gc.setLineWidth(4);
                gc.strokeLine(oldLocation.x, oldLocation.y, x, y);

                // update old location with current one
                oldLocation.x = x;
                oldLocation.y = y;
            }
        });
       // makeCall.setDisable(false);
        return pathTransition;
    }

    public static class Location {
        double x;
        double y;
    }
    public  void calcul_path(int x1,int y1,int x2,int y2) {
        System.out.println(x1 + "," + y1 + "  " + x2 + "," + y2);
        if ((x1 == x2)&& (y1 == y2)) {
                path.getElements().add(new LineTo(listSB.get(tab[x1][y1]).getX(),listSB.get(tab[x1][y1]).getY()));
                return;
            } else {

            if (x1 > x2) {
                x1--;
            } else if(x1 < x2){
                x1++;
            }
                if (y1 > y2) {
                    y1--;
                } else if(y1 <y2){
                    y1++;
                }



        }
        path.getElements().add(new LineTo(listSB.get(tab[x1][y1]).getX(),listSB.get(tab[x1][y1]).getY()));
        calcul_path(x1,y1,x2,y2);
    }

}




