package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class RunnableTask implements Runnable{
    private String message;

    public RunnableTask(String message){
        this.message = message;
    }

    @Override
    public void run() {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < 20000; i++) {
            list.add(Integer.toString(i));
        }
        System.out.println("Lista Criada. Disparando chamadas");

        HttpPostSender sender = new HttpPostSender();
        while (!list.isEmpty()) {
            List<String> batchList = new ArrayList<String>();

            for (int i = 0; i < 200; i++) {
                if (!list.isEmpty()) {
                    String s = list.get(0);
                    batchList.add(s);
                    list.remove(0);
                }
            }
            System.out.println("Lista completa com " + list.size() + " Elementos");
            if (list.size() > 0) {
                System.out.println("Disparando chamadas.");
                sender.sendList(batchList);
                batchList = new ArrayList<String>();
            }
        }
    }
}