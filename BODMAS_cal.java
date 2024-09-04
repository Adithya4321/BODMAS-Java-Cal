import java.util.Stack;
import java.util.*;

public class BODMASCalculator {

    public static double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (currentChar == ' ') {
                continue;
            }
            if (Character.isDigit(currentChar)) {
                StringBuilder buffer = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    buffer.append(expression.charAt(i++));
                }
                numbers.push(Double.parseDouble(buffer.toString()));
                i--;
            } else if (currentChar == '(') {
                operations.push(currentChar);
            } else if (currentChar == ')') {
                while (operations.peek() != '(') {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.pop();
            } else if (isOperator(currentChar)) {
                while (!operations.isEmpty() && precedence(currentChar) <= precedence(operations.peek())) {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.push(currentChar);
            }
        }
        while (!operations.isEmpty()) {
            numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
        }
        return numbers.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return a / b;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a mathematical expression: ");
        String expression = scanner.nextLine();
        try {
            double result = evaluateExpression(expression);
            System.out.println("The result is: " + result);
        } catch (Exception e) {
            System.out.println("Error in expression: " + e.getMessage());
        }
        scanner.close();
    }
}
