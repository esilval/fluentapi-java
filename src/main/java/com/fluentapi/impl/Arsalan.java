package com.fluentapi.impl;

import com.fluentapi.contract.IMenu;
import com.fluentapi.contract.IRestaurant;

public class Arsalan implements IRestaurant {

  protected String name;

  protected String menu;

  public IRestaurant name(String name)
  {
    this.name = name;
    System.out.println(String.format("Enter to hotel :: {1}", name));
    return this;
  }

  public IMenu show()
  {
    final ArsalanMenuHandler handler = new ArsalanMenuHandler();
    // handler.showMenu();
    return handler;
  }

}
