package CT1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private TextField textField;

    @FXML
    private Text savedNumbers;

    private String firstNumber = "";

    private String currentNumber = "";

    private String calculationType;

    @FXML
    void add(ActionEvent event) {
        calculationSetup("+");
    }

    @FXML
    void minus(ActionEvent event) {
        calculationSetup("-");
    }

    @FXML
    void div(ActionEvent event) {
        calculationSetup("/");
    }

    @FXML
    void multi(ActionEvent event) {
        calculationSetup("*");
    }

    public void calculationSetup(String calculationType){
        this.calculationType = calculationType;
        firstNumber = currentNumber;
        currentNumber = "";
        savedNumbers.setText(calculationType + " " +firstNumber );
    }

    @FXML
    void calculate(ActionEvent event) {
        int firstNumberInt = Integer.parseInt(firstNumber);
        int secondNumberInt = Integer.parseInt(currentNumber);

        switch (calculationType) {
            case "+" -> {
                int calculatedNumber = firstNumberInt + secondNumberInt;
              textField.setText(firstNumber+" + "+currentNumber+" = "+calculatedNumber);
            }
            case "-" -> {
                int calculatedNumber = firstNumberInt - secondNumberInt;
                textField.setText(firstNumber+" - "+currentNumber+" = "+calculatedNumber);
            }
            case "/" -> {
                double calculatedNumber = firstNumberInt / (double)secondNumberInt;
                textField.setText(firstNumber+" / "+currentNumber+" = "+calculatedNumber);
            }
            case "*" -> {
                int calculatedNumber = firstNumberInt * secondNumberInt;
                textField.setText(firstNumber+" * "+currentNumber+" = "+calculatedNumber);
            }
        }
    }
    public void updateTextField(){
        textField.setText(currentNumber);
    }

    public void addNumber(String number){
        currentNumber += number;
        updateTextField();
    }

    @FXML
    void clearTextField(ActionEvent event) {
        textField.setText("");
        currentNumber="";

    }
    @FXML
    void b0(ActionEvent event) {
        addNumber("0");
    }

    @FXML
    void b1(ActionEvent event) {
        addNumber("1");
    }

    @FXML
    void b2(ActionEvent event) {
        addNumber("2");
    }

    @FXML
    void b3(ActionEvent event) {
        addNumber("3");
    }

    @FXML
    void b4(ActionEvent event) {
        addNumber("4");
    }

    @FXML
    void b5(ActionEvent event) {
        addNumber("5");
    }

    @FXML
    void b6(ActionEvent event) {
        addNumber("6");
    }

    @FXML
    void b7(ActionEvent event) {
        addNumber("7");
    }

    @FXML
    void b8(ActionEvent event) {
        addNumber("8");
    }

    @FXML
    void b9(ActionEvent event) {
        addNumber("9");
    }


}
