import javafx.application.Application;
import javafx.scene.*;
import javafx.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.scene.transform.*;
import javafx.stage.*;
import java.util.*;
import java.io.*;


public class App extends Application {

    // control elements
    private Label mainLabel;
    private Label drinkLabel;
    private ComboBox<String> drinkComboBox;

    private Label leftLabel;
    private RadioButton aRadioButton;
    private RadioButton bRadioButton;
    private RadioButton cRadioButton;
    private ToggleGroup toggleGroup;

    private Label rightLabel;
    private CheckBox aCheckBox;
    private CheckBox bCheckBox;
    private CheckBox cCheckBox;

    private Button placeOrderButton;
    private TextField aTextField;
    private TextField bTextField;
    private TextField cTextField;

    private ImageView drinkImageView;
    private Label drinkDescriptionLabel;

    // layout elements
    private GridPane mainPane;

    // data
    private HashMap<String, Image> drinkImagesHashMap;
    private HashMap<String, String> drinkLabelsHashMap;

    // methods
    private void initializeElements() {
        mainLabel = new Label("My Coffee Shop");
        drinkLabel = new Label("Drink:");
        drinkComboBox = new ComboBox<String>();

        leftLabel = new Label("Milk Option:");
        aRadioButton = new RadioButton("Whole Milk");
        bRadioButton = new RadioButton("Almond Milk");
        cRadioButton = new RadioButton("No Milk");
        toggleGroup = new ToggleGroup();
        aRadioButton.setToggleGroup(toggleGroup);
        bRadioButton.setToggleGroup(toggleGroup);
        cRadioButton.setToggleGroup(toggleGroup);

        rightLabel = new Label("Add:");
        aCheckBox = new CheckBox("Sugar");
        bCheckBox = new CheckBox("Extra Hot");
        cCheckBox = new CheckBox("Extra Milk");

        placeOrderButton = new Button("PLACE ORDER");
        aTextField = new TextField("Latte");
        bTextField = new TextField();
        cTextField = new TextField();

        drinkImageView = new ImageView(drinkImagesHashMap.get("latte"));
        drinkImageView.setFitHeight(80);
        drinkImageView.setFitWidth(100);
        drinkDescriptionLabel = new Label(drinkLabelsHashMap.get("latte"));

        mainPane = new GridPane();
    }

    private void initializeLayout() {
        // 	add(Node child, int columnIndex, int rowIndex, int colspan, int rowspan)
        mainPane.add(mainLabel, 0, 0, 6, 1);
        
        mainPane.add(drinkLabel, 0, 1, 6, 1);
        
        mainPane.add(drinkComboBox, 0, 2, 6, 1);
        
        mainPane.add(leftLabel, 0, 3, 3, 1);
        mainPane.add(rightLabel, 3, 3, 3, 1);

        mainPane.add(aRadioButton, 0, 4, 3, 1);
        mainPane.add(aCheckBox, 3, 4, 3, 1);

        mainPane.add(bRadioButton, 0, 5, 3, 1);
        mainPane.add(bCheckBox, 3, 5, 3, 1);

        mainPane.add(cRadioButton, 0, 6, 3, 1);
        mainPane.add(cCheckBox, 3, 6, 3, 1);

        mainPane.add(placeOrderButton, 0, 7, 6, 1);
        
        mainPane.add(aTextField, 0, 8, 3, 1);
        mainPane.add(bTextField, 0, 9, 3, 1);
        mainPane.add(cTextField, 0, 10, 3, 1);

        mainPane.add(drinkImageView, 3, 8, 2, 3);
        mainPane.add(drinkDescriptionLabel, 5, 8, 1, 3);

        mainPane.setAlignment(Pos.CENTER);
        // mainPane.setGridLinesVisible(true);
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(10));
        mainPane.getColumnConstraints().add(new ColumnConstraints(50));
        mainPane.getColumnConstraints().add(new ColumnConstraints(50));
        mainPane.getColumnConstraints().add(new ColumnConstraints(50));
        mainPane.getColumnConstraints().add(new ColumnConstraints(50));
        mainPane.getColumnConstraints().add(new ColumnConstraints(50));
        mainPane.getColumnConstraints().add(new ColumnConstraints(50));
    }

    private void initializeEventHandlers() {
        drinkComboBox.getItems().addAll("Latte", "Americano", "Cappuccino", "Black Tea", "Green Tea");
        drinkComboBox.setValue("Latte");

        drinkComboBox.setOnAction(e -> {
            aTextField.setText(drinkComboBox.getValue());

            String drinkType = drinkComboBox.getValue().toLowerCase().replace(' ', '-');
            drinkImageView.setImage(drinkImagesHashMap.get(drinkType));
            drinkDescriptionLabel.setText(drinkLabelsHashMap.get(drinkType));
        });

        aRadioButton.setOnAction(e -> {
            updateRadioButtonTextField();
        });
        
        bRadioButton.setOnAction(e -> {
            updateRadioButtonTextField();
        });
        
        cRadioButton.setOnAction(e -> {
            updateRadioButtonTextField();
        });

        aCheckBox.setOnAction(e -> {
            updateCheckBoxTextField();
        });

        bCheckBox.setOnAction(e -> {
            updateCheckBoxTextField();
        });

        cCheckBox.setOnAction(e -> {
            updateCheckBoxTextField();
        });
    }

    private void updateRadioButtonTextField() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        bTextField.setText(selectedRadioButton.getText());
    }

    private void updateCheckBoxTextField() {
        String extras = "";
        if (aCheckBox.isSelected()) {
            extras += aCheckBox.getText();
            extras += ", ";
        }

        if (bCheckBox.isSelected()) {
            extras += bCheckBox.getText();
            extras += ", ";
        }

        if (cCheckBox.isSelected()) {
            extras += cCheckBox.getText();
            extras += ", ";
        }

        if (extras.equals("")) {
            cTextField.setText("");
            return;
        }

        extras = extras.substring(0, extras.length() - 2);
        cTextField.setText(extras);
    }

    private void loadData() {
        drinkImagesHashMap = new HashMap<String, Image>();
        drinkLabelsHashMap = new HashMap<String, String>();

        for (String drinkType: Arrays.asList("americano", "black-tea", "cappuccino", "green-tea", "latte")) {
            Image image = new Image("resources\\drink-types\\" + drinkType + ".jpg");
            String desc = "";
            try {
                Scanner fin = new Scanner(new File("resources\\drink-types\\" + drinkType + ".txt"));
                desc = fin.nextLine();
            } catch (Exception ex) {
                throw new RuntimeException("some input/output error happened.");
            }
            drinkImagesHashMap.put(drinkType, image);
            drinkLabelsHashMap.put(drinkType, desc);
        }
    }

    public void start(Stage primaryStage) {

        loadData();
        initializeElements();
        initializeLayout();
        initializeEventHandlers();

        Scene scene = new Scene(mainPane, 200, 200);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
