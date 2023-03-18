import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.util.*;
import javafx.scene.*;
import javafx.animation.*;
import javafx.scene.input.*;
import javafx.scene.transform.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.stage.*;
import java.util.*;
import java.io.*;

public class MyProgram extends Application {

    // control elements
    private Label mainLabel;

    private Label drinkLabel;
    private ComboBox<String> drinkComboBox;
    
    private Label milkOptionLabel;
    private RadioButton wholeMilkRadioButton;
    private RadioButton almondMilkRadioButton;
    private RadioButton noMilkRadioButton;
    private ToggleGroup milkOptionToggleGroup;

    private Label addExtraLabel;
    private CheckBox sugarCheckBox;
    private CheckBox extraHotCheckBox;
    private CheckBox extraMilkCheckBox;

    private Button placeOrderButton;
    private TextField drinkTextField;
    private TextField milkTextField;
    private TextField addExtraTextField;

    // layout elements
    private VBox topVBox;
    
    private VBox radioButtonVBox;
    private VBox checkBoxVBox;
    private HBox optionsHBox;

    private HBox statusHBox;

    private VBox mainVBox;

    private VBox drinkImageVBox;

    // data
    private HashMap<String, ImageView> drinkImagesHashMap;
    private HashMap<String, Label> drinkLabelsHashMap;

    // methods
    private void initialize() {
        mainLabel = new Label("My Coffee Shop");

        drinkLabel = new Label("Drink:");
        drinkComboBox = new ComboBox<String>();
        
        milkOptionLabel = new Label("Milk option:");
        wholeMilkRadioButton = new RadioButton("Whole Milk");
        almondMilkRadioButton = new RadioButton("Almond Milk");
        noMilkRadioButton = new RadioButton("No Milk");
        milkOptionToggleGroup = new ToggleGroup();
    
        addExtraLabel = new Label("Add extra:");
        sugarCheckBox = new CheckBox("Sugar");
        extraHotCheckBox = new CheckBox("Extra Hot");
        extraMilkCheckBox = new CheckBox("Extra Milk");
    
        placeOrderButton = new Button("PLACE ORDER");
        drinkTextField = new TextField();
        milkTextField = new TextField();
        addExtraTextField = new TextField();

        // layouts
        topVBox = new VBox(10); 
        radioButtonVBox = new VBox(10); 
        checkBoxVBox = new VBox(10); 
        optionsHBox = new HBox(10); 
        statusHBox = new HBox(10); 
        mainVBox = new VBox(10);
        drinkImageVBox = new VBox(10);

        // data
        drinkImagesHashMap = new HashMap<String, ImageView>();
        drinkLabelsHashMap = new HashMap<String, Label>();
        loadDrinkInfoData();
    }

    private void loadDrinkInfoData() {
        ArrayList<String> drinkTypesArrayList = new ArrayList<String>();
        drinkTypesArrayList.add("americano");
        drinkTypesArrayList.add("black-tea");
        drinkTypesArrayList.add("cappuccino");
        drinkTypesArrayList.add("green-tea");
        drinkTypesArrayList.add("latte");

        for (String drinkType : drinkTypesArrayList) {
            ImageView drinkImage = new ImageView(new Image("resources\\drink-types\\" + drinkType + ".jpg"));
            String drinkInfo = "";
            try {
                Scanner fin = new Scanner(new File("resources\\drink-types\\" + drinkType + ".txt"));
                drinkInfo = fin.nextLine();
            } catch (Exception ex) {
                throw new RuntimeException("something went wrong during IO");
            }
            Label drinkLabel = new Label(drinkInfo);

            drinkImagesHashMap.put(drinkType, drinkImage);
            drinkLabelsHashMap.put(drinkType, drinkLabel);
        }
    }

    private void makeLayout() {
        topVBox.getChildren().addAll(mainLabel, drinkLabel, drinkComboBox);
        radioButtonVBox.getChildren().addAll(milkOptionLabel, wholeMilkRadioButton, almondMilkRadioButton, noMilkRadioButton);
        checkBoxVBox.getChildren().addAll(addExtraLabel, sugarCheckBox, extraHotCheckBox, extraMilkCheckBox);
        optionsHBox.getChildren().addAll(radioButtonVBox, checkBoxVBox);
        statusHBox.getChildren().addAll(drinkTextField, milkTextField, addExtraTextField);
        mainVBox.getChildren().addAll(topVBox, drinkImageVBox, optionsHBox, placeOrderButton, statusHBox);
    }

    private void refineScene() {
        // add radio buttons to toggle group
        wholeMilkRadioButton.setToggleGroup(milkOptionToggleGroup);
        almondMilkRadioButton.setToggleGroup(milkOptionToggleGroup);
        noMilkRadioButton.setToggleGroup(milkOptionToggleGroup);

        // text fields
        milkTextField.setEditable(false);
        drinkTextField.setEditable(false);
        addExtraTextField.setEditable(false);

        // latte, americano, cappuccino, black tea, green tea
        drinkComboBox.getItems().addAll("Latte", "Americano", "Cappuccino", "Black Tea", "Green Tea");

        // layouts
        drinkImageVBox.setAlignment(Pos.CENTER);
        optionsHBox.setAlignment(Pos.CENTER);
        statusHBox.setAlignment(Pos.CENTER);
        topVBox.setAlignment(Pos.CENTER);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));
    }

    private void initializeEventHandlers() {
        // combo box to text field
        drinkComboBox.setOnAction(e -> {
            String drinkType = drinkComboBox.getValue();
            drinkTextField.setText(drinkType);

            String drinkID = drinkType.toLowerCase().replace(' ', '-');
            drinkImageVBox.getChildren().clear();
            drinkImageVBox.getChildren().add(drinkImagesHashMap.get(drinkID));
            drinkImageVBox.getChildren().add(drinkLabelsHashMap.get(drinkID));
        });

        // radio buttons
        wholeMilkRadioButton.setOnAction(e -> {
            updateMilkTextField();
        });
        almondMilkRadioButton.setOnAction(e -> {
            updateMilkTextField();
        });
        noMilkRadioButton.setOnAction(e -> {
            updateMilkTextField();
        });

        // check boxes
        sugarCheckBox.setOnAction(e -> {
            updateAddExtraTextField();
        });
        extraHotCheckBox.setOnAction(e -> {
            updateAddExtraTextField();
        });
        extraMilkCheckBox.setOnAction(e -> {
            updateAddExtraTextField();
        });
    }

    private void updateMilkTextField() {
        milkTextField.setText(((RadioButton) milkOptionToggleGroup.getSelectedToggle()).getText());
    }

    private void updateAddExtraTextField() {
        String extras = "";
        if (sugarCheckBox.isSelected()) {
            extras += sugarCheckBox.getText();
            extras += ", ";
        }
        if (extraHotCheckBox.isSelected()) {
            extras += extraHotCheckBox.getText();
            extras += ", ";
        }
        if (extraMilkCheckBox.isSelected()) {
            extras += extraMilkCheckBox.getText();
            extras += ", ";
        }
        extras = extras.substring(0, extras.length() - 2);
        addExtraTextField.setText(extras);
    }

    public void start(Stage primaryStage) {

        initialize();
        makeLayout();
        refineScene();
        initializeEventHandlers();

        Scene scene = new Scene(mainVBox, 200, 200);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.setTitle("Problem A");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
