package com.dongx.blog.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;

    private Integer cartegorycode;

    private String cartegorytype;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartegorycode() {
        return cartegorycode;
    }

    public void setCartegorycode(Integer cartegorycode) {
        this.cartegorycode = cartegorycode;
    }

    public String getCartegorytype() {
        return cartegorytype;
    }

    public void setCartegorytype(String cartegorytype) {
        this.cartegorytype = cartegorytype == null ? null : cartegorytype.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}