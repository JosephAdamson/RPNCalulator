import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.EmptyStackException;

/**
 * @author Joseph Adamson
 * @version June 2020
 * 
 * Interface functionality.
 */
public class CalculatorController {
    @FXML public Text output;

    /**
     * Handles button events for numbers and basic operations
     */
    @FXML
    public void processOperandOrOperator(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        output.setText(output.getText() + value);
    }

    /**
     * Handles button events for trig operations (Sin, Cos, Tan)
     */
    @FXML
    public void processFunction(ActionEvent event) {
        String trigOp = ((Button)event.getSource()).getText() + "(";
        output.setText(output.getText() + trigOp);
    }

    /**
     * Computes expression (=)
     */
    @FXML
    public void processExpression() {
        try {
            String[] tokens =
                    output.getText().split("(?<=[-+*^√/()])|(?=[-+*^√/()])");
            String RPN = ShuntingYard.infixToPostfix(tokens);
            String result = ShuntingYard.postfixCalculate(RPN);
            
            // If the answer is a whole number we convert it into an int
            if (Double.parseDouble(result) == (int) Double.parseDouble(result)) {
                result = Integer.toString((int) Double.parseDouble(result));
            }
            MiniMemEdit.store(result);
            output.setText(result);
        } catch (EmptyStackException | NumberFormatException | ArithmeticException | 
                IndexOutOfBoundsException e) {
            output.setText("Syntax ERROR [AC] : Cancel");
        }
    }

    /**
     * Delete the last typed value.
     */
    @FXML
    public void delete() {
        String s = output.getText();
        String value = 
                (s == null || s.length() == 0 ? null : s.substring(0, s.length() - 1));
        output.setText(value);
    }

    /**
     * Clear screen.
     */
    @FXML
    public void clearAll() {
        output.setText("");
    }

    /**
     * Retrieve answer from previous calculation
     * (action corresponds to ANS on a scientific calculator)
     */
    @FXML
    public void retrievePreviousAns() {
        output.setText(output.getText() + MiniMemEdit.retrieve());
    }
}
