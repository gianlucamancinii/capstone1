package EpicodeFS0124.core.product;

import jakarta.persistence.*;
import EpicodeFS0124.core.security.CustomUser;


@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    private String type;
    private String title;
    private String shop;
    private double price;
    private int rating;
    private int ratingCount;
    private boolean isBestSeller;
    private boolean hasDiscount;
    private double discountedPrice;
    private double discountPercent;
    private boolean isFreeShipping;
    @Column(columnDefinition = "text")
    private String image;
    @Column(columnDefinition = "text")
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name="seller_id", referencedColumnName = "id", nullable = true)
    private CustomUser seller;

    public static Product of(String type, String title, String shop, CustomUser seller, double price, int rating,
                             int ratingCount, boolean isBestSeller, boolean hasDiscount, double discountedPrice,
                             double discountPercent, boolean isFreeShipping, String image, String thumbnail) {
        final Product product=new Product();
        product.type = type;
        product.title = title;
        product.shop = shop;
        product.seller = seller;
        product.price = price;
        product.rating = rating;
        product.ratingCount = ratingCount;
        product.isBestSeller = isBestSeller;
        product.hasDiscount = hasDiscount;
        product.discountedPrice = discountedPrice;
        product.discountPercent = discountPercent;
        product.isFreeShipping = isFreeShipping;
        product.image = image;
        product.thumbnail = thumbnail;
        product.setSeller(seller);
        return product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public CustomUser getSeller() {
        return seller;
    }

    public void setSeller(CustomUser seller) {
        this.seller = seller;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public boolean isHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isFreeShipping() {
        return isFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        isFreeShipping = freeShipping;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
