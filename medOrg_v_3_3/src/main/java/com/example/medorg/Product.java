package com.example.medorg;


public class Product {
  
  String name;
  int price;
  int image;
  boolean box;
  int number;
  String question;

  Product(String _describe, int _price, int _image, boolean _box, String question_, String title_) {
    name = title_;
    price = _price;
    image = _image;
    box = _box;
    number = 1;
    question = question_;
  }
}
