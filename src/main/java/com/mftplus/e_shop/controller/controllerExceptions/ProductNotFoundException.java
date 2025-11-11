package com.mftplus.e_shop.controller.controllerExceptions;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(){
        super("Product Not Found");
    }
}
