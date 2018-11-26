package com.fluentapi.impl;

import java.util.ArrayList;
import java.util.List;

import com.fluentapi.contract.IItem;
import com.fluentapi.contract.IMenu;

public class ArsalanMenuHandler implements IMenu {

  protected List<IItem> menuList = new ArrayList<>();

  protected List<IItem> selectedList = new ArrayList<>();

  public ArsalanMenuHandler() {

  }

  public IMenu order(int index)
  {
    return null;
  }

  public IMenu eat()
  {
    return null;
  }

  public IMenu pay()
  {
    return null;
  }

  public IItem get(int index)
  {
    return null;
  }

}
