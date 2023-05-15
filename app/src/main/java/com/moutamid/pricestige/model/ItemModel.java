package com.moutamid.pricestige.model;

public class ItemModel {
    int position;
    String title, epid, link, image, condition;
    boolean is_auction, buy_it_now, free_returns, sponsored;
    String price;

    public ItemModel(int position, String title, String epid, String link, String image, String condition, boolean is_auction, boolean buy_it_now, boolean free_returns, boolean sponsored, String price) {
        this.position = position;
        this.title = title;
        this.epid = epid;
        this.link = link;
        this.image = image;
        this.condition = condition;
        this.is_auction = is_auction;
        this.buy_it_now = buy_it_now;
        this.free_returns = free_returns;
        this.sponsored = sponsored;
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpid() {
        return epid;
    }

    public void setEpid(String epid) {
        this.epid = epid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isIs_auction() {
        return is_auction;
    }

    public void setIs_auction(boolean is_auction) {
        this.is_auction = is_auction;
    }

    public boolean isBuy_it_now() {
        return buy_it_now;
    }

    public void setBuy_it_now(boolean buy_it_now) {
        this.buy_it_now = buy_it_now;
    }

    public boolean isFree_returns() {
        return free_returns;
    }

    public void setFree_returns(boolean free_returns) {
        this.free_returns = free_returns;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
