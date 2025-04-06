import java.util.ArrayList;
import java.util.Scanner;
public class Calculator {
    ArrayList<Double> digits = new ArrayList<>();
    ArrayList<Character> arithmeticSymbols = new ArrayList<>();
    double result = 0;

    public void enterExpression() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите выражение");
        String expression = sc.next();
        sorting(expression);
        prioritizedCalculation();
        nonPrioritizedCalculation();
        System.out.println(digits.get(0));

    }

    //отделяем числа от арифметических знаков
    public void sorting(String expression) {
        StringBuilder currentNumber = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (Character.isDigit(symbol)) {
                currentNumber.append(symbol);  // Накапливаем цифры числа
            } else if (symbol == '+' || symbol == '-' || symbol == '/' || symbol == '*' || symbol == '^') {
                // Если встретили символ, сохраняем накопленное число
                if (currentNumber.length() > 0) {
                    digits.add(Double.parseDouble(currentNumber.toString()));
                    currentNumber.setLength(0);  // Сбрасываем для следующего числа
                }
                arithmeticSymbols.add(symbol);
            } else {
                throw new IllegalArgumentException("Можно использовать только цифры и символы +, -, /, *, ^");
            }
        }

        // Добавляем последнее число, если строка заканчивается цифрой
        if (currentNumber.length() > 0) {
            digits.add(Double.parseDouble(currentNumber.toString()));
        }
    }

    public void prioritizedCalculation() {
        if (digits.size() != arithmeticSymbols.size() + 1) {
            throw new IllegalArgumentException("Количество операторов должно быть на 1 меньше количества чисел");
        }
        for (int i = 0; i<arithmeticSymbols.size(); i++) {
            //делаем приоритетные вычисления
            if(arithmeticSymbols.get(i) == '/'){
                if (digits.get(i+1) == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result = digits.get(i)/digits.get(i+1);
                digits.remove(i);
                digits.set(i, result);
                arithmeticSymbols.remove(i);
                i--;
            }
            else if (arithmeticSymbols.get(i) == '*'){
                result = digits.get(i)*digits.get(i+1);
                digits.remove(i);
                digits.set(i, result);
                arithmeticSymbols.remove(i);
                i--;
            } else if (arithmeticSymbols.get(i) == '^'){
                result = Math.pow(digits.get(i), digits.get(i+1));
                digits.remove(i);
                digits.set(i, result);
                arithmeticSymbols.remove(i);
                i--;
            }
        }
    }

    public void nonPrioritizedCalculation (){
        for (int i = 0; i<arithmeticSymbols.size(); i++) {
            //делаем неприоритетные вычисления
            if(arithmeticSymbols.get(i) == '+'){
                result = digits.get(i) + digits.get(i+1);
                digits.remove(i);//удаляем отработанные числа
                digits.set(i, result);
                arithmeticSymbols.remove(i);//и знаки
                i--;
            }
            else if (arithmeticSymbols.get(i) == '-'){
                result = digits.get(i) - digits.get(i+1);
                digits.remove(i);//удаляеи отработанные числа
                digits.set(i, result);
                arithmeticSymbols.remove(i);//и знаки
                i--;
            }
        }
    }


        public static void main (String[]args){
            //1.Вводим выражение (2 enter + enter и т.д.)
            //2. Вводим равно
            Calculator calc = new Calculator();
            calc.enterExpression();
        }
    }


