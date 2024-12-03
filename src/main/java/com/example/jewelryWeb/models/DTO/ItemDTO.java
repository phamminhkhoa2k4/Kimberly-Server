package com.example.jewelryWeb.models.DTO;

import java.math.BigDecimal;

public class ItemDTO {
    private String name;
    private BigDecimal price;
    private String imgId;

    public String getImgId() {
        return this.imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public ItemDTO(String name, BigDecimal price,String imgId) {
        this.name = name;
        this.price = price;
        this.imgId=imgId;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
