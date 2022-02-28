package pl.pue.air.czajsearch;

public class FavItem {

    private String key_id;
    private String itemTitle;
    private String itemLocation;
    private String itemPrice;
    private String itemInfo;
    private String itemAvailability;
    private int itemImage;

    public FavItem() {
    }

    public FavItem(String key_id, String itemTitle, String itemLocation, String itemPrice, String itemInfo, String itemAvailability, int itemImage) {
        this.key_id = key_id;
        this.itemTitle = itemTitle;
        this.itemLocation = itemLocation;
        this.itemPrice = itemPrice;
        this.itemInfo = itemInfo;
        this.itemAvailability = itemAvailability;
        this.itemImage = itemImage;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
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
