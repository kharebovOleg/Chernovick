package org.example.hexlet.algorithms.graphs.tsp.little;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

class Graph {
    private static final int M = -1;
    private static final String SYMBOL = "M";

    /*
    Алгоритм Литтла или исключения подциклов
    1) Операция редукции по строкам: в каждой строке матрицы находят минимальный элемент dmin и вычитают его из всех
        элементов соответствующей строки. Нижняя граница: H=∑dmin.

    2) Операция редукции по столбцам: в каждом столбце матрицы выбирают минимальный элемент dmin, и вычитают его из
        всех элементов соответствующего столбца. Нижняя граница: H=H+∑dmin.

    3) Константа приведения H является нижней границей множества всех допустимых гамильтоновых контуров.

    4) Поиск степеней нулей для приведенной по строкам и столбцам матрицы. Для этого временно нули в матице заменяют
        на знак «∞» и находят сумму минимальных элементов строки и столбца, соответствующих этому нулю.

    5) Выбирают дугу (i,j), для которой степень нулевого элемента достигает максимального значения.

    6) Разбивают множество всех гамильтоновых контуров на два подмножества: подмножество гамильтоновых контуров
        содержащих дугу (i,j) и не содержащих ее (i*,j*). Для получения матрицы контуров, включающих дугу (i,j),
        вычеркивают в матрице строку i и столбец j. Чтобы не допустить образования негамильтонова контура, заменяют
        симметричный элемент (j,i) на знак «∞». Исключение дуги достигается заменой элемента в матрице на ∞.

    7) Проводят приведение матрицы гамильтоновых контуров с поиском констант приведения H(i,j) и H(i*,j*).

    8) Сравнивают нижние границы подмножества гамильтоновых контуров H(i,j) и H(i*,j*). Если H(i,j)<H(i*,j*),
        то дальнейшему ветвлению в первую очередь подлежит множество (i,j), иначе - разбиению подлежит множество (i*,j*).

    9) Если в результате ветвлений получается матрица (2x2), то определяют полученный ветвлением гамильтонов контур и
        его длину.

    10) Сравнивают длину гамильтонова контура с нижними границами оборванных ветвей. Если длина контура не превышает
        их нижних границ, то задача решена. В противном случае развивают ветви подмножеств с нижней границей, меньшей
        полученного контура, до тех пор, пока не получится маршрут с меньшей длиной.
     */

    void start(String path) {
        long start = System.currentTimeMillis();
        Stack<Integer> stack = new Stack<>();
        System.out.println("Read graph to file:");
        int[][] matrix = readFile(path); // прочитали матрицу смежности из файла
        int[][] clone = clone(matrix); // сделали копию
        List<Integer> v = new ArrayList<>();
        for (int i = 1; i <= matrix.length; i++) { // заполнили лист значениями от 1 до длинны строки матрицы
            v.add(i);
        }
        printMatrix(matrix); // вывели полученную матрицу на экран
        int count = 1;
        while (matrix.length > 1) {
            System.out.println("\n##########################################");
            System.out.println("STAGE #" + count++ + ":");
            int[] di = getMinArray(matrix, false); // получили массив из наименьших значений в каждой строке
            matrix = diffMatrix(matrix, di, false); // в каждой строке из каждого числа отняли минимальное в данной строке
            int[] dj = getMinArray(matrix, true); // аналогично по столбцам
            matrix = diffMatrix(matrix, dj, true);
            System.out.println("\ndi: " + Arrays.toString(di) + ";"); // вывод на экран массива минимальных значений в каждой строке
            System.out.println("dj: " + Arrays.toString(dj) + ";"); // аналогично по столбцам
            System.out.println("\nMatrix after diff:");
            printMatrix(matrix); // выводим матрицу после вычитания минимальных значений
            matrix = getPath(matrix, stack, v);
            System.out.println("\nReduction matrix:");
            printMatrix(matrix);
            if (matrix.length == 1) {
                push(stack, v.remove(0));
            }
            System.out.print("Path now: ");
            printStack(stack);
        }
        if (!stack.empty()) {
            stack.push(stack.get(0));
        }
        System.out.println("\n##########################################");
        System.out.println("\nAnswer:");
        System.out.print("Path: ");
        printStack(stack);
        System.out.println("Sum:  " + getSum(stack, clone));
        start = System.currentTimeMillis() - start;
        System.out.printf("Spent time: %d.%s sec.;", start / 1000, new DecimalFormat("000").format(start % 1000));
    }

    private int[][] clone(int[][] martrix) {
        int[][] clone = new int[martrix.length][];
        int count = 0;
        for (int[] line : martrix) {
            clone[count++] = line.clone();
        }
        return clone;
    }

    private int getSum(Stack<Integer> stack, int[][] clone) {
        int sum = 0;
        if (!stack.empty()) {
            int v = stack.pop();
            while (!stack.empty()) {
                sum += clone[v - 1][stack.peek() - 1];
                v = stack.pop();
            }
        }
        return sum;
    }

    private boolean isNumber(String number) {
        return number != null && number.matches("\\d+");
    } // проверка является ли строковая переменная числом

    private void printStack(Stack<Integer> stack) {
        StringBuilder sb = new StringBuilder();
        if (!stack.empty()) {
            for (Integer num : stack) {
                sb.append(num).append(" -> ");
            }
            sb.delete(sb.length() - 4, sb.length());
        }
        System.out.println(sb.toString());
    }

    private int[][] getPath(int[][] matrix, Stack<Integer> stack, List<Integer> v) {
        int indexI = 0;
        int indexJ = 0;
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 0) {
                    int sum = getMin(matrix, i, false, j) + getMin(matrix, j, true, i);
                    if (sum > max) {
                        max = sum;
                        indexI = i;
                        indexJ = j;
                    }
                }
            }
        }
        matrix[indexJ][indexI] = M;
        matrix = removeLineAndRow(matrix, indexI, indexJ);
        push(stack, v.get(indexI));
        push(stack, v.get(indexJ));
        v.remove(indexI);
        return matrix;
    }

    private void push(Stack<Integer> stack, int v) {
        if (stack.search(v) == -1) {
            stack.push(v);
        }
    }

    private int[][] removeLineAndRow(int[][] matrix, int indexI, int indexJ) {
        int[][] result = new int[matrix.length - 1][matrix.length - 1];
        int countI = 0;
        int countJ;
        for (int i = 0; i < matrix.length; i++) {
            if (i != indexI) {
                countJ = 0;
                for (int j = 0; j < matrix.length; j++) {
                    if (j != indexJ) {
                        result[countI][countJ++] = matrix[i][j];
                    }
                }
                countI++;
            }
        }
        matrix = result;
        return matrix;
    }

    private int getMin(int[][] matrix, int index, boolean row, int j) { // ищем минимум в строке или столбце
        // index это номер строки или столбца в матрице
        // если raw = false то ищем в строке, иначе в столбце
        // j это то, что мы помечаем как М (путь из пункта А в пункт А, который нас не интересует)
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if (i != j) {
                int number = row ? matrix[i][index] : matrix[index][i]; // если raw == true, то по столбцам, иначе по строкам
                if (number != M && number < min) {
                    min = number;
                }
            }
        }
        return min;
    }

    private int[] getMinArray(int[][] matrix, boolean row) { // получаем массив минимальных значений в каждой строке
        // или в каждом столбце в зависимости от параметра raw
        int[] res = new int[matrix.length];
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            res[count++] = getMin(matrix, i, row, -1);
        }
        return res;
    }

    private int[][] diffMatrix(int[][] matrix, int[] d, boolean row) { // отнимает из чисел матрицы matrix минимальные
        for (int i = 0; i < matrix.length; i++) {                      // значения каждого столбца или строки в зависимости
            for (int j = 0; j < matrix.length; j++) {                  // от параметра raw
                if (matrix[i][j] != M) {
                    matrix[i][j] -= row ? d[j] : d[i]; // Если raw = true, то отнимем мин. значение в столбце, иначе в строке
                }
            }
        }
        return matrix;
    }

    private void printMatrix(int[][] matrix) { // печатаем матрицу на экран
        int length = matrix.length;
        for (int[] line : matrix) {
            System.out.print("["); // перед каждой строчкой ставим "]"
            for (int index = 0; index < length; index++) {
                System.out.print(line[index] == M ? SYMBOL : line[index]); // если значение равно "-1", то пишем M, иначе число
                if (index < length - 1) { // если это не конец строки, то добавляем запятую
                    System.out.print(", ");
                }
            }
            System.out.println("]"); // в конце строки ставим "]"
        }
    }

    private int[][] readFile(String path) { // метод считывает матрицу смежности
        // с файла и возвращает ее в виде 2-мерного массива чисел
        StringBuilder sb = new StringBuilder();
        try (Scanner read = new Scanner(new File(path))) {
            while (read.hasNextLine()) {
                String line = read.nextLine().trim(); // считываем строку в файле
                if (line.length() > 0) { // если она не пуста, добавляем в sb и абзац
                    sb.append(line).append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] lines = sb.toString().split("\n"); // делим sb на массив строк по абзацам
        int length = lines.length; // количество строк, оно же количество столбцов в матрице смежности
        int[][] matrix = new int[length][length];
        for (int i = 0; i < length; i++) { // проходимся по строкам (i - это индекс строки в массиве lines)
            String[] numbers = lines[i].split("\\s+"); // разделяем строку по пробелам
            for (int j = 0; j < length; j++) { // проходимся по полученному массиву - столбцу (j - это индекс числа в numbers)
                if (i == j) { // если номер столбца и строки равны, а это в матрице смежности будет значить расстояние из пункта А в пункт А
                    matrix[i][j] = M; // что в матрице будем помечать как М
                } else if (isNumber(numbers[j])) { // иначе, проверяем на то, что numbers[j] - является числом
                    matrix[i][j] = Integer.parseInt(numbers[j]); // добавляем его в матрицу
                }
            }
        }
        return matrix;
    }
}
