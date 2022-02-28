package pl.pue.air.czajsearch;

public class Item {

    private String itemTitle;
    private String itemLocation;
    private float itemPrice;
    private String itemInfo;
    private String itemAvailability;
    private int itemImage;

    public Item(String itemTitle, String itemLocation, float itemPrice, String itemInfo, String itemAvailability, int itemImage) {
        this.itemTitle = itemTitle;
        this.itemLocation = itemLocation;
        this.itemPrice = itemPrice;
        this.itemInfo = itemInfo;
        this.itemAvailability = itemAvailability;
        this.itemImage = itemImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getItemAvailability() {
        return itemAvailability;
    }

    public void setItemAvailability(String itemAvailability) {
        this.itemAvailability = itemAvailability;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }
}
