package org.example.hexlet.algorithms.graphs.tsp.little;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GraphDetailed {
    private static final char V = 'V';
    private static final String ELEMENT = "M";
    private static final int ELEMENT_DIAGONAL = -1;
    private static final String DI = "di";
    private static final String DJ = "dj";

    private List<List<Integer>> matrix;
    private final List<Integer> vi;
    private final List<Integer> vj;

    GraphDetailed(String path) {
        this.matrix = init(path);
        this.vi = fillV(this.matrix.size());
        this.vj = fillV(this.matrix.size());
    }

    private List<List<Integer>> clone(List<List<Integer>> matrix) {
        List<List<Integer>> clone = new ArrayList<>();
        for (List<Integer> sub : matrix) {
            List<Integer> temp = new ArrayList<>();
            Collections.addAll(temp, sub.toArray(new Integer[0]));
            clone.add(temp);
        }
        return clone;
    }

    void bfs() {
        List<String> result = new ArrayList<>();
        List<List<Integer>> clone = clone(this.matrix);
        int count = 1;

        while (this.matrix.size() > 0 && result.size() != clone.size()) {
            String separator = String.format(String.format("%s%d%s", "%", 100, "s"), "").replaceAll(" ", "#");
            System.out.println("\n" + separator);

            System.out.printf("%50s\n", "ЭТАП #" + count++);

            System.out.println("ГРАФ:");
            List<String> list = matrixFormatToPrint(matrixToStringList(this.matrix), null, null);
            list.forEach(System.out::println);
            System.out.println("\nИщем и считаем " + DI + ":");
            int[] di = getMinByMatrix(this.matrix, false);
            System.out.printf("%s: %s;\n\n", DI, Arrays.toString(di));

            List<String> matrix = matrixFormatToPrint(matrixToStringList(this.matrix), di, null);
            List<String> diff = matrixFormatToPrint(getDiffList(this.matrix, di, false), di, null);
            this.matrix = diffMatrix(this.matrix, di, false);
            List<String> matrixRes = matrixFormatToPrint(matrixToStringList(this.matrix), null, null);
            list = matrixFormatToPrintCascade(matrix, diff, matrixRes, false);
            list.forEach(System.out::println);

            System.out.println("\nНа данном этапе ГРАФ:");
            list = matrixFormatToPrint(matrixToStringList(this.matrix), null, null);
            list.forEach(System.out::println);

            System.out.println("\nИщем и считаем " + DJ + " :");
            int[] dj = getMinByMatrix(this.matrix, true);
            System.out.printf("%s: %s;\n\n", DJ, Arrays.toString(dj));

            matrix = matrixFormatToPrint(matrixToStringList(this.matrix), null, dj);
            diff = matrixFormatToPrint(getDiffList(this.matrix, dj, true), null, dj);
            this.matrix = diffMatrix(this.matrix, dj, true);
            matrixRes = matrixFormatToPrint(matrixToStringList(this.matrix), null, null);
            list = matrixFormatToPrintCascade(matrix, diff, matrixRes, true);
            list.forEach(System.out::println);

            System.out.println("\nНа данном этапе ГРАФ:");
            list = matrixFormatToPrint(matrixToStringList(this.matrix), null, null);
            list.forEach(System.out::println);

            System.out.println("\nИщем <оценки> нулей:");
            List<List<String>> matrixMark = new ArrayList<>();
            Pare maxZero = getMaxPare(this.matrix, matrixMark);
            list = matrixFormatToPrint(matrixMark, null, null);
            list.forEach(System.out::println);

            System.out.println("\nВыбираем самую большую <оценку>:");
            System.out.println(matrixMark.get(maxZero.i).get(maxZero.j));

            System.out.println("\nНайденный путь:");
            String vi = String.valueOf(this.vi.get(maxZero.i));
            String vj = String.valueOf(this.vj.get(maxZero.j));
            if (!result.contains(vi)) {
                result.add(vi);
            }
            if (!result.contains(vj)) {
                result.add(vj);
            }
            System.out.println(vi + " -> " + vj);

            System.out.println("\nУдаляем строку и столбец с наибольшей <оценкой> у нуля.");
            this.matrix.get(maxZero.j).set(maxZero.i, ELEMENT_DIAGONAL);

            this.matrix.remove(maxZero.i);
            for (List<Integer> sub : this.matrix) {
                sub.remove(maxZero.j);
            }
            this.vi.remove(maxZero.i);
            this.vj.remove(maxZero.j);
            list = matrixFormatToPrint(matrixToStringList(this.matrix), null, null);
            list.forEach(System.out::println);

            System.out.println("\nПуть на данный момент:");
            System.out.println(StringUtils.join(result, " -> "));
        }

        if (!result.isEmpty()) {
            result.add(result.get(0));
        }

        System.out.println("\nИТОГОВЫЙ ПУТЬ: " + StringUtils.join(result, " -> ") + ";");
        System.out.println("СУММА ПУТИ: " + getSum(result, clone));
    }

    private int getSum(List<String> path, List<List<Integer>> matrix) {
        int sum = 0;
        if (path.size() > 1) {
            int i = Integer.parseInt(path.get(0)) - 1;
            for (int index = 1; index < path.size(); index++) {
                int j = Integer.parseInt(path.get(index)) - 1;
                sum += matrix.get(i).get(j);
                i = j;
            }
        }
        return sum;
    }

    private Pare getMaxPare(List<List<Integer>> matrix, List<List<String>> res) {
        Pare result = new Pare(0, 0);
        int max = 0;
        for (int i = 0; i < matrix.size(); i++) {
            List<String> temp = new ArrayList<>();
            for (int j = 0; j < matrix.size(); j++) {
                int value = matrix.get(i).get(j);
                if (value == 0) {
                    Pare pare = getMin(matrix, i, j);
                    if (pare.i + pare.j > max) {
                        max = pare.i + pare.j;
                        result = new Pare(i, j);
                    }
                    temp.add("0(" + (pare.i + pare.j) + ")");
                } else if (value == ELEMENT_DIAGONAL) {
                    temp.add(ELEMENT);
                } else {
                    temp.add(String.valueOf(value));
                }
            }
            res.add(temp);
        }
        return result;
    }

    private Pare getMin(List<List<Integer>> matrix, int i, int j) {
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        int count = 0;
        for (int value : matrix.get(i)) {
            if (count++ != j && value != ELEMENT_DIAGONAL && value < x) {
                x = value;
            }
        }
        for (count = 0; count < matrix.size(); count++) {
            if (count != i) {
                int value = matrix.get(count).get(j);
                if (value != ELEMENT_DIAGONAL && value < y) {
                    y = value;
                }
            }
        }
        x = x == Integer.MAX_VALUE ? 0 : x;
        y = y == Integer.MAX_VALUE ? 0 : y;
        return new Pare(x, y);
    }

    private List<List<String>> getDiffList(List<List<Integer>> matrix, int[] d, boolean dj) {
        List<List<String>> res = new ArrayList<>();
        int count = 0;
        for (List<Integer> sub : matrix) {
            List<String> temp = new ArrayList<>();
            for (int value : sub) {
                temp.add(value != ELEMENT_DIAGONAL ? value + "-" + d[count] : ELEMENT);
                if (dj) {
                    count++;
                }
            }
            res.add(temp);
            count = dj ? 0 : count + 1;
        }
        return res;
    }

    private int[] getMinByMatrix(List<List<Integer>> matrix, boolean dj) {
        int[] d = new int[matrix.size()];
        Arrays.fill(d, Integer.MAX_VALUE);
        int count = 0;
        for (List<Integer> list : matrix) {
            for (int value : list) {
                if (value != ELEMENT_DIAGONAL && d[count] > value) {
                    d[count] = value;
                }
                if (dj) {
                    count++;
                }
            }
            count = dj ? 0 : count + 1;
        }
        return d;
    }

    private List<String> matrixFormatToPrintCascade(List<String> matrix, List<String> diff, List<String> res, boolean dj) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int size = res.size();
        int half = matrix.size() / 2;
        for (int index = 0; index < size; index++) {
            sb.append(matrix.get(index)).append("   ");
            sb.append(index == half ? "=>" : "  ");
            sb.append("   ");
            sb.append(diff.get(index)).append("   ");
            sb.append(index == half ? "=>" : "  ");
            sb.append("   ");
            sb.append(res.get(index));
            result.add(sb.toString());
            sb.delete(0, sb.length());
        }
        if (dj) {
            sb.append(matrix.get(matrix.size() - 2)).append("        ").append(diff.get(diff.size() - 2));
            result.add(sb.toString());
            sb.delete(0, sb.length());
            sb.append(matrix.get(matrix.size() - 1)).append("        ").append(diff.get(diff.size() - 1));
            result.add(sb.toString());
        }
        return result;
    }

    private int getMaxLengthG(List<List<String>> matrixString, int min) {
        int max = String.valueOf(matrixString.size()).length();
        for (List<String> sub : matrixString) {
            for (String value : sub) {
                if (value.length() > max) {
                    max = value.length();
                }
            }
        }
        return Math.max(min, max);
    }

    private int getMaxLengthV(List<Integer> v, int min) {
        int max = 0;
        for (int value : v) {
            if (value > max) {
                max = value;
            }
        }
        return Math.max(min, String.valueOf(max).length());
    }

    private List<List<String>> matrixToStringList(List<List<Integer>> matrix) {
        List<List<String>> res = new ArrayList<>();
        for (List<Integer> list : matrix) {
            List<String> strList = new ArrayList<>();
            for (int value : list) {
                strList.add(value == ELEMENT_DIAGONAL ? ELEMENT : String.valueOf(value));
            }
            res.add(strList);
        }
        return res;
    }

    private List<List<Integer>> diffMatrix(List<List<Integer>> matrix, int[] d, boolean dj) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                int value = matrix.get(i).get(j);
                if (value != ELEMENT_DIAGONAL) {
                    value = dj ? value - d[j] : value - d[i];
                    matrix.get(i).set(j, value);
                }
            }
        }
        return matrix;
    }

    private List<String> matrixFormatToPrint(List<List<String>> list, int[] di, int[] dj) {
        List<String> res = new ArrayList<>();
        int sizeV = getMaxLengthV(this.vi, Math.max(DI.length(), DJ.length()));
        sizeV = Math.max(getMaxLengthV(this.vj, Math.max(DI.length(), DJ.length())), sizeV);
        int sizeG = getMaxLengthG(list, Math.max(DI.length(), DJ.length()));
        String tabV = String.format("%s%d%s", "%", sizeV + 1, "s");
        String tabG = String.format("%s%d%s", "%", sizeG + 1, "s");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(tabV, V)).append(" |");
        for (int v : this.vj) {
            sb.append(String.format(tabG, v)).append(" ");
        }
        if (di != null && di.length > 0) {
            sb.append("|").append(String.format(tabV, DI));
        }
        String lineSeparator = String.format(String.format("%s%d%s", "%", sb.length(), "s"), "").
                replaceAll(" ", "-");
        res.add(sb.toString());
        sb.delete(0, sb.length());
        res.add(lineSeparator);
        int count = 0;
        for (List<String> sub : list) {
            sb.append(String.format(tabV, this.vi.get(count++))).append(" |");
            for (String value : sub) {
                sb.append(String.format(tabG, value));
                sb.append(" ");
            }
            if (di != null && di.length > 0) {
                sb.append("|").append(String.format(tabV, di[count - 1]));
            }
            res.add(sb.toString());
            sb.delete(0, sb.length());
        }
        if (dj != null && dj.length > 0) {
            sb.append(String.format(tabV, DJ)).append(" |");
            for (int value : dj) {
                sb.append(String.format(tabG, value)).append(" ");
            }
            if (di != null && di.length > 0) {
                sb.append("| ").append(String.format(tabG, ""));
            }
            res.add(lineSeparator);
            res.add(sb.toString());
        }
        return res;
    }

    private List<Integer> fillV(int size) {
        List<Integer> list = new ArrayList<>();
        for (int v = 1; v <= size; v++) {
            list.add(v);
        }
        return list;
    }

    private List<List<Integer>> init(String path) {
        List<List<Integer>> matrix = getMatrix(readFile(path));
        checkMatrix(matrix);
        return matrix;
    }

    private List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try (Scanner in = new Scanner(new File(path))) {
            while (in.hasNextLine()) {
                lines.add(in.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            throw new IncorrectGraphException(String.format("Файл [путь = %s] не найден.", path));
        }
        return lines;
    }

    private List<List<Integer>> getMatrix(List<String> list) {
        List<List<Integer>> res = new ArrayList<>();
        if (list != null) {
            int i = 0;
            for (String line : list) {
                if (!line.isEmpty()) {
                    List<Integer> temp = new ArrayList<>();
                    int j = 0;
                    for (String element : line.split("\\s+")) {
                        if (i != j++) {
                            if (isInteger(element)) {
                                temp.add(Integer.parseInt(element));
                            } else {
                                throw new IncorrectGraphException("Не корректные данные в файле.");
                            }
                        } else {
                            temp.add(ELEMENT_DIAGONAL);
                        }
                    }
                    res.add(temp);
                    i++;
                }
            }
        }
        return res;
    }

    private void checkMatrix(List<List<Integer>> matrix) {
        if (!matrix.isEmpty()) {
            for (List<Integer> list : matrix) {
                if (list.size() != matrix.size()) {
                    throw new IncorrectGraphException("Матрица должна иметь одинаковую выосту и ширину.");
                }
            }
        } else {
            throw new IncorrectGraphException("Файл пуст.");
        }
    }

    private boolean isInteger(String str) {
        return str != null && str.matches("\\d+");
    }

    class IncorrectGraphException extends RuntimeException {
        IncorrectGraphException(String message) {
            super(message);
        }
    }

    class Pare {
        private final int i;
        private final int j;

        Pare(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
