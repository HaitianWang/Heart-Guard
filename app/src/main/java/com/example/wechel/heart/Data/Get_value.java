package com.example.wechel.heart.Data;

public class Get_value {
    public static int[] value = new int[24];
    static int flag = 9;

    public Get_value() {
        value[0] = 62;
        value[1] = 60;
        value[1] = 55;
        value[2] = 55;
        value[3] = 61;
        value[4] = 52;
        value[5] = 50;
        value[6] = 71;
        value[7] = 56;
        value[8] = 45;
        value[9] = 70;
        value[10] = 72;
        value[11] = 71;
        value[12] = 74;
        value[13] = 75;
        value[14] = 76;
        value[15] = 90;
        value[16] = 86;
        value[17] = 80;
        value[18] = 79;
        value[19] = 71;
        value[20] = 80;
        value[21] = 76;
        value[22] = 72;
        value[23] = 69;
    }

    public void sum(int heart_rate) {
        if (flag < 24) {
            value[flag++] = heart_rate;
        } else {
            for (int i = 0; i < 23; i++) {
                value[i] = value[i + 1];
            }
            value[23] = heart_rate;
        }
    }

}
