package com.example.demospringbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBatchApplication.class, args);
    }

    //  method to sort the input value

    public void sortInputParams(int[] inputParams) {
        int n = inputParams.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (inputParams[j] > inputParams[j + 1]) {
                    // swap inputParams[j+1] and inputParams[i]
                    int temp = inputParams[j];
                    inputParams[j] = inputParams[j + 1];
                    inputParams[j + 1] = temp;
                }
            }
        }
    }
}
