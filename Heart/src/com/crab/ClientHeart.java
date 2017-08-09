package com.crab;

public class ClientHeart extends Thread {

    @Override
    public void run() {

        try {
            while (true) {
                ClientSender.getInstance().send();
                synchronized (ClientHeart.class) {
                    Thread.sleep(2000);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序的入口main方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        ClientHeart client = new ClientHeart();
        client.start();
    }

}