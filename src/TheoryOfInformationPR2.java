public class TheoryOfInformationPR2 {

    public static void main(String[] args) {
        //ПР 2 Исходные данные для варианта 9
        double[] p_a = {0.08, 0.17, 0.42, 0.33}; // Вероятности символов источника
        double[][] p_b_given_a = { // Условные вероятности (канальная матрица)
                {0.9933, 0.0067, 0, 0},
                {0.0067, 0.9733, 0.02, 0},
                {0.0133, 0, 0.9867, 0},
                {0, 0.0067, 0.0133, 0.98}
        };

        // 1. Расчет совместных вероятностей p(a_i, b_j)
        double[][] p_ab = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                p_ab[i][j] = p_a[i] * p_b_given_a[i][j];
            }
        }

        // 2. Расчет безусловных вероятностей p(b_j)
        double[] p_b = new double[4];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                p_b[j] += p_ab[i][j];
            }
        }

        // 3. Расчет условных вероятностей p(a_i|b_j)
        double[][] p_a_given_b = new double[4][4];
        for (int j = 0; j < 4; j++) {
            if (p_b[j] > 0) {
                for (int i = 0; i < 4; i++) {
                    p_a_given_b[i][j] = p_ab[i][j] / p_b[j];
                }
            }
        }

        // 4. Расчет энтропий
        double H_A = calculateEntropy(p_a);
        double H_B = calculateEntropy(p_b);

        // Условная энтропия H(B|A)
        double H_B_given_A = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (p_b_given_a[i][j] > 0) {
                    H_B_given_A += p_ab[i][j] * log2(p_b_given_a[i][j]);
                }
            }
        }
        H_B_given_A = -H_B_given_A;

        // Условная энтропия H(A|B)
        double H_A_given_B = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (p_a_given_b[i][j] > 0) {
                    H_A_given_B += p_ab[i][j] * log2(p_a_given_b[i][j]);
                }
            }
        }
        H_A_given_B = -H_A_given_B;

        // Энтропия объединения
        double H_AB = H_A + H_B_given_A;

        // 5. Расчет информационных потерь для n=500
        int n = 500;
        double delta_I = n * H_A_given_B;
        double I_received = n * (H_B - H_A_given_B);

        // Вывод результатов
        System.out.println("Практическая работа №2 (вариант 9)");
        System.out.printf("H(A) = %.4f бит/символ%n", H_A);
        System.out.printf("H(B) = %.4f бит/символ%n", H_B);
        System.out.printf("H(B|A) = %.4f бит/символ%n", H_B_given_A);
        System.out.printf("H(A|B) = %.4f бит/символ%n", H_A_given_B);
        System.out.printf("H(A,B) = %.4f бит/символ%n", H_AB);
        System.out.printf("Потери при n=500: ΔI = %.2f бит%n", delta_I);
        System.out.printf("Принятая информация: I = %.2f бит%n", I_received);
    }

    private static double calculateEntropy(double[] probabilities) {
        double entropy = 0;
        for (double p : probabilities) {
            if (p > 0) {
                entropy += p * log2(p);
            }
        }
        return -entropy;
    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }
}