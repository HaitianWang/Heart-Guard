package com.example.wechel.heart.Data;

public class Future_Data {
    private int[] collectNum = new int[1008];
    private int[] heartNum = new int[]{68 ,72 ,74 ,78 ,82 ,72 ,68 ,63 ,87 ,80 ,70 ,77 ,63 ,73 ,68 ,69 ,70,80};
    private int index = 0;

    /*{86, 85, 93, 79, 99, 71, 82, 87, 83, 70, 91, 97, 85, 92,
            91, 75, 82, 90, 95, 96, 97, 80, 72, 75};*/
    public void calculate(int num) { //收集训练样本集；
        collectNum[index] = num;
    }

    public int[] sendData() {
        return heartNum;
    }
}
