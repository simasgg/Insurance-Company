package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public GridPane fstGrid, sndGrid, rightBox, leftBox;
    //public VBox rightBox;
    public ChoiceBox type, fuel, month, day, year;
    public Spinner kw, seats, tank;
    public TextField name_input, surname_input, house_nr_input, postal_input, street_input, make_input, model_input, registration_input, end_input;
    public DatePicker date_input;
    public ColorPicker color;
    public ToggleGroup radioGroup, kaskoGroup, basicGroup;
    public CheckBox glass, parking, passenger, third, hail, theft;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set borders of regions
        fstGrid.setStyle("-fx-border-color: black");
        sndGrid.setStyle("-fx-border-color: black");
        leftBox.setStyle("-fx-border-color: black");
        rightBox.setStyle("-fx-border-color: black");

        // choice box (type)
        List<String> list = new ArrayList<String>(Arrays.asList("Car", "Van", "Bus", "Camper", "Motorcycle", "Camper"));
        ObservableList obList = FXCollections.observableList(list);
        type.getItems().clear();
        type.setItems(obList);
        type.setValue(list.get(0));

        // choice box (fuel)
        list = new ArrayList<String>(Arrays.asList("Diesel", "Petrol", "Electricity", "Bioethanol", "Gas", "Hybrid"));
        obList = FXCollections.observableList(list);
        fuel.getItems().clear();
        fuel.setItems(obList);
        fuel.setValue(list.get(0));

        // kw spinner
        kw.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300));
        kw.getValueFactory().setValue(100);

        // tank spinner
        tank.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400, 0, 5));
        tank.getValueFactory().setValue(50);

        // seats spinner
        seats.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 70));
        seats.getValueFactory().setValue(5);

        // for date choice boxes
        // month
        list = new ArrayList<String>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        obList = FXCollections.observableList(list);
        month.getItems().clear();
        month.setItems(obList);
        month.setValue(list.get(0));

        // day
        list = new ArrayList<String>();
        for(int i=31; i>=1; i--)
            list.add(Integer.toString(i));
        obList = FXCollections.observableList(list);
        day.getItems().clear();
        day.setItems(obList);
        day.setValue(list.get(0));

        // day
        list = new ArrayList<String>();
        for(int i=2021; i>=1900; i--)
            list.add(Integer.toString(i));
        obList = FXCollections.observableList(list);
        year.getItems().clear();
        year.setItems(obList);
        year.setValue(list.get(0));
    }

    public void btn_save(ActionEvent actionEvent) {
        Boolean isValid = Validation();
        if(isValid){
            try (PrintWriter out = new PrintWriter("data.txt")) {
                out.println("Information about insured person");
                out.println("Name: "+name_input.getText());
                out.println("Surname: "+surname_input.getText());
                out.println("Street: "+street_input.getText());
                out.println("House number: "+house_nr_input.getText());
                out.println("Postal: "+postal_input.getText());
                out.println("Date of birth: "+date_input.getValue());
                out.println("Experience: "+((RadioButton)radioGroup.getSelectedToggle()).getText());
                out.println();
                out.println("Vehicle data");
                out.println("Make: "+make_input.getText());
                out.println("Model: "+model_input.getText());
                out.println("Type: "+type.getSelectionModel().getSelectedItem());
                out.println("Fuel: "+fuel.getSelectionModel().getSelectedItem());
                out.println("Kw: "+kw.getValue());
                out.println("Seats: "+seats.getValue());
                out.println("Tank: "+tank.getValue());
                out.println("Color: "+color.getValue());
                out.println();
                out.println("Info about Insurer");
                out.println("Basic insurance: "+((RadioButton)basicGroup.getSelectedToggle()).getText());
                out.println("Kasko insurance: "+((RadioButton)radioGroup.getSelectedToggle()).getText());
                out.println("Other insurances:");
                if(glass.isSelected()) out.println(glass.getText());
                if(parking.isSelected()) out.println(parking.getText());
                if(passenger.isSelected()) out.println(passenger.getText());
                if(third.isSelected()) out.println(third.getText());
                if(hail.isSelected()) out.println(hail.getText());
                if(theft.isSelected()) out.println(theft.getText());
                out.println();
                out.println("Registration data");
                out.println("Month: "+month.getSelectionModel().getSelectedItem());
                out.println("Day: "+day.getSelectionModel().getSelectedItem());
                out.println("Year: "+year.getSelectionModel().getSelectedItem());
                out.println("Designation of registration: "+registration_input.getText());
                out.println("End of first registration: "+end_input.getText());

            }catch ( IOException e) {
                Dialog<String> dialog = new Dialog<String>();
                dialog.setTitle("Dialog");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.setContentText("Unable to write to file");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                return;
            }
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Dialog");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Data was successfully saved");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }

    public void btn_reset(ActionEvent actionEvent) {
        // reset choice boxes
        type.setValue("Car");
        fuel.setValue("Diesel");
        month.setValue("January");
        day.setValue("31");
        year.setValue("2021");

        // reset spinners
        kw.getValueFactory().setValue(100);
        seats.getValueFactory().setValue(5);
        tank.getValueFactory().setValue(50);

        // reset text fields
        name_input.clear();
        name_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        surname_input.clear();
        surname_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        house_nr_input.clear();
        house_nr_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        postal_input.clear();
        postal_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        street_input.clear();
        street_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        make_input.clear();
        make_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        model_input.clear();
        model_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        registration_input.clear();
        registration_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");
        end_input.clear();
        end_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);");

        // reset date picker
        date_input.setValue(null);
        date_input.setStyle("-fx-background-color: none;");

        // reset checkboxes
        glass.setSelected(false);
        parking.setSelected(false);
        passenger.setSelected(false);
        third.setSelected(false);
        hail.setSelected(false);
        theft.setSelected(false);
    }

    public boolean isLetters(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public boolean Validation(){
        boolean errorDidNotOccur = true;

        // name
        if(name_input.getText().isEmpty() || !isLetters(name_input.getText())){
            //name_input.setStyle("-fx-background-color: rgb(" + 255 + "," + 0 + ", " + 0 + ", " + 0.1 + ");");
            name_input.setStyle("-fx-text-box-border: red;");

            errorDidNotOccur = false;
        } else { name_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // surname
        if(surname_input.getText().isEmpty() || !isLetters(surname_input.getText())){
            surname_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { surname_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // house nr (only number allowed)
        if(house_nr_input.getText().isEmpty() || !house_nr_input.getText().matches("^\\d+$")){
            house_nr_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { house_nr_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // postal (4 numbers, 1st number between 1 and 9)
        if(postal_input.getText().isEmpty() || !postal_input.getText().matches("^[0-9]{4}$") || postal_input.getText().charAt(0) == '0'){
            postal_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { postal_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // street (no more than 89 chars)
        if(street_input.getText().isEmpty() || street_input.getText().length() >89){
            street_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { street_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // make (at least not empty)
        if(make_input.getText().isEmpty()){
            make_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { make_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // model
        if(model_input.getText().isEmpty()){
            model_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { model_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // Date Picker (check if not null)
        if(date_input.getValue() == null){
            date_input.setStyle("-fx-background-color: red;");
            errorDidNotOccur = false;
        } else { date_input.setStyle("-fx-background-color: none;"); }

        // End
        if(end_input.getText().isEmpty()){
            end_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { end_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // Registration
        if(registration_input.getText().isEmpty()){
            registration_input.setStyle("-fx-text-box-border: red;");
            errorDidNotOccur = false;
        } else { registration_input.setStyle("-fx-focus-color:rgba(255,0,0,0.2);"); }

        // returns true if error didn't occur, otherwise returns false if at least 1 error was found
        return errorDidNotOccur;
    }

    public void toOpen(ActionEvent actionEvent) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("C:\\Windows\\system32\\notepad.exe data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void toAbout(ActionEvent actionEvent) {
        //Creating a dialog
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("About");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText("For easier use of the program: \nCTRL+O (to open data file),\nCTRL+E (to exit program), \nCTRL+A (for help)\n" +
                "To save data press Save button, to reset entered values - Reset\n\n" +
                "This is a 3rd UI Assignment done by Simas");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
}
