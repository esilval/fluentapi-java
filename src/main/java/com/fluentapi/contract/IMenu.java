package com.fluentapi.contract;

public interface IMenu {

  IMenu order(int index);

  IMenu eat();

  IMenu pay();

  IItem get(int index);
}
