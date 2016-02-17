package br.ufg.inf.muralufg.model;

public class DrawerItem {
    private final String itemName;
    private final int imgResID;

    public DrawerItem(String itemName, int imgResID) {
        super();
        this.itemName = itemName;
        this.imgResID = imgResID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

}