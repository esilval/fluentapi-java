package com.fluentapi.impl.simple;

import com.fluentapi.FluentIterable;
import com.fluentapi.FluentIterableTest;

public class SimpleFluentIterableTest extends FluentIterableTest {

  @Override
  protected FluentIterable<Integer>
      createFluentIterable(Iterable<Integer> integers)
  {
    return SimpleFluentIterable.fromCopyOf(integers);
  }

}
