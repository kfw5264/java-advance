package com.kangfawei.builder;

public class Computer {

    private String hardDisk; // 硬盘
    private String memoryBank; // 内存条
    private String graphicsCard; // 显卡
    private String mainboard; // 主板

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public void setMemoryBank(String memoryBank) {
        this.memoryBank = memoryBank;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public void setMainboard(String mainboard) {
        this.mainboard = mainboard;
    }

    public void show(){
        StringBuilder sb = new StringBuilder();
        sb.append("硬盘-->")
                .append(hardDisk)
                .append("\r\n")
                .append("内存条-->")
                .append(memoryBank)
                .append("\r\n")
                .append("显卡-->")
                .append(graphicsCard)
                .append("\r\n")
                .append("主板-->")
                .append(mainboard);

        System.out.println(sb.toString());
    }
}
