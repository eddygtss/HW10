import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoanController implements Initializable {


    @FXML
    private VBox rootPane;
    public Button startButton;
    public TextField interestRateTF;
    public Label errorLabel;
    public Slider loops;
    public TextField loanAmountTF;
    public TextField loanNumberTF;
    public Button previousButton;
    public Button nextButton;
    public Button calculateButton;
    public Button displayButton;
    public MenuItem newMenuButton;
    public RadioButton businessLoanRB;
    public RadioButton personalLoanRB;
    public ToggleGroup loanType;
    public TextField lastNameTF;
    public TextField termLengthTF;
    public ListView<Loan> resultList = new ListView<>();
    static Loan[] loans;
    static double interest;
    static int loopCount;
    static int iteration;



    private void changeWindow(String fxmlName) throws IOException {
        VBox mainWindow = FXMLLoader.load(LoanController.class.getResource(fxmlName));
        rootPane.getChildren().setAll(mainWindow);
    }

    @FXML
    private void startCalc() throws Exception {
        // Storing how many loops we are doing using loopCount, initializing array for loans
        loopCount = (int)loops.getValue();
        loans = new Loan[loopCount];
        iteration = 0;

        // We want to get the interest rate here, if any error we will display error label
        try {
            interest = Double.parseDouble(interestRateTF.getText());
        } catch (Exception ex) {
            errorLabel.setVisible(true);
        }

        // Interest cannot be negative or 0, or else we display the error label
        if (interest <= 0.0) {
            errorLabel.setVisible(true);
        } else {
            String loanInfo = "LoanCalculator.fxml";
            changeWindow(loanInfo);
        }

    }

    @FXML
    private void nextButton() throws Exception {
        // Obtaining all of the text from the text fields
        int loanNum = Integer.parseInt(loanNumberTF.getText());
        double loanAmt = Double.parseDouble(loanAmountTF.getText());
        int term = Integer.parseInt(termLengthTF.getText());
        String lastName = lastNameTF.getText();

        // Depending on the radio button we will make a personal loan or business loan
        if (personalLoanRB.isSelected()) {
            PersonalLoan personalLoan = new PersonalLoan(loanNum, loanAmt, term, lastName, interest);
            loans[iteration] = personalLoan;
        } else {
            BusinessLoan businessLoan = new BusinessLoan(loanNum, loanAmt, term, lastName, interest);
            loans[iteration] = businessLoan;
        }
        // We want to clear all of the text fields for the next data entry
        loanNumberTF.clear();
        loanAmountTF.clear();
        termLengthTF.clear();
        lastNameTF.clear();

        // Increase the iteration counter
        iteration += 1;

        // If we used previous button we want to make sure that moving back forward
        // we don't delete any previously entered data
        try {
            if (loans[iteration] != null && iteration != loopCount) {
                retrieveLoan();
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        // We are setting the previous button to be visible once we are out of index 0
        if (iteration > 0) {
            previousButton.setVisible(true);
        }
        // Once we are at the last iteration and we hit next, we want to switch to results page
        if (iteration == loopCount) {
            String resultsPage = "ResultsPage.fxml";
            changeWindow(resultsPage);
        }

        if (iteration == loopCount - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }

    }

    // This method helps retrieve previous loan information and sets the text field to the information
    // found on the array at the iteration number
    private void retrieveLoan() {
        if (loans[iteration].getClass().equals(PersonalLoan.class)) {
            // This just makes sure that once we go back or forward the correct Loan type is selected.
            personalLoanRB.fire();
        } else {
            businessLoanRB.fire();
        }
        loanNumberTF.setText(String.valueOf(loans[iteration].getLoanNumber()));
        loanAmountTF.setText(String.valueOf(loans[iteration].getLoanAmount()));
        termLengthTF.setText(String.valueOf(loans[iteration].getTerm()));
        lastNameTF.setText(loans[iteration].getLastName());
    }

    @FXML
    private void previousButton() {
        // Clearing all of the text fields of previous data
        loanNumberTF.clear();
        loanAmountTF.clear();
        termLengthTF.clear();
        lastNameTF.clear();

        iteration -= 1;

        // When going back I want to disable previous button at index 0
        if (iteration == 0) {
            previousButton.setVisible(false);
        }

        if (iteration != loopCount - 1) {
            nextButton.setText("Next");
        }

        retrieveLoan();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void calculateDisplay() {
        ObservableList<Loan> loanList = FXCollections.observableArrayList(loans);
        resultList.setItems(loanList);
    }

    public void startNew() throws Exception{
        String startPage = "StartPage.fxml";
        changeWindow(startPage);
    }

    public void quitApp() {
        Platform.exit();
    }

    public void aboutApp() {
        // This is the about button alert which displays info about the application.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Loan Calculator");
        alert.setHeaderText("Loan Calculator v1.1 JavaFX");
        alert.setContentText("© Eddy Herrera 2021");

        alert.showAndWait();
    }
}
