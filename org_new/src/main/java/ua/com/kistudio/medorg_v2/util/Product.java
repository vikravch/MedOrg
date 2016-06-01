package ua.com.kistudio.medorg_v2.util;


public class Product {
  
  public String name;
  int price;
  int image;
  boolean box;
  public int number;
  public String question;

  public Product(String _describe, int _price, int _image, boolean _box, String question_, String title_) {
    name = title_;
    price = _price;
    image = _image;
    box = _box;
    number = 1;
    question = question_;
  }
}
