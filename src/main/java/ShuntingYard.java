import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Joseph Adamson
 * @version June 2020
 * 
 * Calculator input tokens sorted according to Djikstra's Shunting
 * Yard algorithm. Resulting expressions are calculated using a stack.
 */
public class ShuntingYard {

    /**
     * Initializer block fills our map, assigning operators to
     * their corresponding precedence according to BODMAS.
     * I've added a custom operator to handle the unary minus (-u).
     */
    private static final HashMap<String, Integer> OPERATORS;
    static {
       OPERATORS = new HashMap<>();
       OPERATORS.put("-", 1);
       OPERATORS.put("+", 2);
       OPERATORS.put("*", 3);
       OPERATORS.put("/", 4);
       OPERATORS.put("-u", 5);
       OPERATORS.put("√", 6);
       OPERATORS.put("log", 7);
       OPERATORS.put("sin", 7);
       OPERATORS.put("cos", 7);
       OPERATORS.put("tan", 7);
       OPERATORS.put("^", 8);
    }

    /**
     * Based on Djikstra's Shunting-yard algorithm.
     * 
     * @param tokens: operands and operands from a given infix 
     * expression.
     * @return our infix expression rendered in postfix (reverse
     * polish) notation.
     */
    public static String infixToPostfix(String[] tokens) {
        Stack<String> opStack = new Stack<>();
        StringBuilder output = new StringBuilder();
        
        for (int i = 0; i < tokens.length; i++) {
            
            // parse operands with the unary minus and convert π to Math.PI
            unaryMinusEval(tokens, tokens[i], i);
            parsePi(tokens, tokens[i], i);
            parseSqrtString(tokens, tokens[i], i);
            
            if (OPERATORS.containsKey(tokens[i])) {
               while(!opStack.isEmpty() && isHigherPrecedence(tokens[i], opStack.peek())) {
                   output.append(opStack.pop()).append(" ");
               }
               opStack.push(tokens[i]);
               
            } else if (tokens[i].equals("(")) {
                opStack.push(tokens[i]);
                
            } else if (tokens[i].equals(")")) {
                while (!opStack.peek().equals("(")){
                    output.append(opStack.pop()).append(" ");
                }
                opStack.pop();
                
            } else {
                output.append(tokens[i]).append(" ");
            }
        }
        while(!opStack.isEmpty()) {
            output.append(opStack.pop()).append(" ");
        }
        
        return output.toString();
    }

    /**
     * Helper method for infixToPostfix compares two operators
     * based on the order of precedence established in the OPERATORS
     * map.
     * 
     * @param operator1: current operator token being evaluated
     * @param operator2: operator currently on top of the opStack. 
     * @return true of the operator currently on top of the opStack has 
     * a greater precedence than the operator token being evaluated.
     */
    public static boolean isHigherPrecedence(String operator1, String operator2) {
        return OPERATORS.containsKey(operator2) &&
                OPERATORS.get(operator2) >= OPERATORS.get(operator1);
    }

    /**
     * Helper method identifies the unary minus operator.
     * 
     * @param tokens: an String array containing each token in a given
     * infix expression.
     * @param token: a token representing a number or operator.
     * @param tokenIndex: the position of the token within the array (tokens).
     */
    public static void unaryMinusEval(String[] tokens, String token, int tokenIndex) {
        if (token.equals("-") && tokenIndex == 0 ||
                token.equals("-") && OPERATORS.containsKey(tokens[tokenIndex-1])) {
            tokens[tokenIndex] = "-u";
        }
    }

    /**
     * Simple method to parse the pi symbol as it appears in
     * a given list of tokens. 
     * 
     * @param tokens: an String array containing each token in a given
     * infix expression.
     * @param token: a token representing a number or operator.
     * @param tokenIndex: the position of the token within the array (tokens).
     */
    public static void parsePi(String[] tokens, String token, int tokenIndex) {
        if (token.equals("π")) {
            
            // pi symbol converted into a constant.
            tokens[tokenIndex] = Double.toString(Math.PI);
        }
    }

    /**
     * Helper method to parse √
     * 
     * @param tokens: an String array containing each token in a given
     * infix expression.
     * @param token: a token representing a number or operator.
     * @param tokenIndex: the position of the token within the array (tokens).
     * @throws IndexOutOfBoundsException: makes sure correct (right-associative)
     * syntax is used for the √ (√3 good, 3√ bad)
     */
    public static void parseSqrtString
            (String[] tokens, String token, int tokenIndex) throws IndexOutOfBoundsException {
        if (token.equals("√")) {
            if (tokenIndex == tokens.length - 1) {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    /**
     * Method computes postfix expressions formatted by infixToPostfix().
     * 
     * @param postfix: mathematical expression rendered in postfix notation.
     * @return the result of the expression.
     * @throws EmptyStackException indicating a fatal syntax error in 
     * the original infix expression.
     */
    public static String postfixCalculate(String postfix) throws EmptyStackException {
        Stack<String> calcStack = new Stack<>();
        String[] tokens = postfix.split("\\s");

        for (String token : tokens) {
            if (OPERATORS.containsKey(token)) {
                int precedence = OPERATORS.get(token);
                if (precedence < 5 || precedence == 8) {
                    double operand2 = Double.parseDouble(calcStack.pop());
                    double operand1 = Double.parseDouble(calcStack.pop());
                    
                    double result;
                    switch (token) {
                        case "-" -> {
                            result = operand1 - operand2;
                            calcStack.push(Double.toString(result));
                        }
                        case "+" -> {
                            result = operand1 + operand2;
                            calcStack.push(Double.toString(result));
                        }
                        case "*" -> {
                            result = operand1 * operand2;
                            calcStack.push(Double.toString(result));
                        }
                        case "/" -> {
                            if (operand2 == 0) {
                                throw new ArithmeticException();
                            }
                            result = operand1 / operand2;
                            calcStack.push(Double.toString(result));
                        }
                        case "^" -> {
                            result = Math.pow(operand1, operand2);
                            calcStack.push(Double.toString(result));
                        }
                    }
                } else {
                    double operand = Double.parseDouble(calcStack.pop());
                    
                    switch (token) {
                        case "-u" ->  calcStack.push(Double.toString(-operand));
                        
                        case "√" -> calcStack.push(Double.toString((Math.sqrt(operand))));
                        
                        // logarithms calculated in default base 10.
                        case "log" -> calcStack.push((Double.toString((Math.log10(operand)))));
                        
                        case "sin" -> 
                                calcStack.push(Double.toString(Math.sin(Math.toRadians(operand))));
                        
                        case "cos" -> 
                            calcStack.push(Double.toString(Math.cos(Math.toRadians(operand))));
                        
                        case "tan" -> 
                            calcStack.push(Double.toString(Math.tan(Math.toRadians(operand))));
                    }
                }
            } else {
                calcStack.push(token);
            }
        }
        return (calcStack.peek());
    }
}
