package com.fluentapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

public abstract class FluentIterableTest {

  protected abstract FluentIterable<Integer>
      createFluentIterable(final Iterable<Integer> integers);
  
  private List<Integer> integers;
  
  @Before
  public void setUp() {
    integers = Arrays.asList(1, 2, 3, 10, 9, 8);
  }

  @Test
  public void testFirst()
  {
    final Optional<Integer> first = createFluentIterable(integers).first();
    assertNotNull(first);
    assertTrue(first.isPresent());
    assertEquals(integers.get(0), first.get());
  }
  
  @Test
  public void testFirstEmptyCollection() {
    integers = Collections.emptyList();
    final Optional<Integer> first = createFluentIterable(integers).first();
    assertNotNull(first);
    assertFalse(first.isPresent());
  }
  
  @Test
  public void testFirstCount() {
    final List<Integer> sublist = createFluentIterable(integers)
      .first(4)
      .asList();
    
    assertNotNull(sublist);
    assertEquals(sublist.size(), 4);
    
    assertEquals(integers.get(0), sublist.get(0));
    assertEquals(integers.get(1), sublist.get(1));
    assertEquals(integers.get(2), sublist.get(2));
    assertEquals(integers.get(3), sublist.get(3));
  }
  
  @Test
  public void testFirstCountLessItems() {
    integers = Arrays.asList(1, 2, 3);
    final List<Integer> sublist = createFluentIterable(integers)
      .first(4)
      .asList();
        
    assertNotNull(sublist);
    assertEquals(sublist.size(), 3);
    
    assertEquals(integers.get(0), sublist.get(0));
    assertEquals(integers.get(1), sublist.get(1));
    assertEquals(integers.get(2), sublist.get(2));
  }
  
  @Test
  public void testLast() {
    final Optional<Integer> last = createFluentIterable(integers).last();
    assertNotNull(last);
    assertTrue(last.isPresent());
    assertEquals(integers.get(integers.size() - 1), last.get());
  }
  
  @Test
  public void testLastEmptyCollection() throws Exception {
    final List<Integer> integers = Collections.<Integer>emptyList();
    final Optional<Integer> last = createFluentIterable(integers).last();
    assertNotNull(last);
    assertFalse(last.isPresent());
  }

  @Test
  public void testLastCount() throws Exception {
    final List<Integer> last4 = createFluentIterable(integers)
      .last(4)
      .asList();

    assertNotNull(last4);
    assertEquals(4, last4.size());
    assertEquals(Integer.valueOf(3), last4.get(0));
    assertEquals(Integer.valueOf(10), last4.get(1));
    assertEquals(Integer.valueOf(9), last4.get(2));
    assertEquals(Integer.valueOf(8), last4.get(3));
  }

  @Test
  public void testLastCountLessItems() throws Exception {
    integers = Arrays.asList(1, 2, 3);
    final List<Integer> last4 = createFluentIterable(integers)
          .last(4)
          .asList();

    assertNotNull(last4);
    assertEquals(3, last4.size());

    assertEquals(Integer.valueOf(1), last4.get(0));
    assertEquals(Integer.valueOf(2), last4.get(1));
    assertEquals(Integer.valueOf(3), last4.get(2));
  }
  
  @Test
  public void testFilter() {
    final List<Integer> evenItems = createFluentIterable(integers)
      .filter(i -> i % 2 == 0)
      .asList();
    
    assertNotNull(evenItems);
    assertEquals(3, evenItems.size());
    assertEquals(Integer.valueOf(2), evenItems.get(0));
    assertEquals(Integer.valueOf(10), evenItems.get(1));
    assertEquals(Integer.valueOf(8), evenItems.get(2));
  }
  
  @Test
  public void testMap() {
    final List<Long> longs = createFluentIterable(integers)
      .map(Integer::longValue)
      .asList();
    
    assertNotNull(longs);
    assertEquals(Long.valueOf(1), longs.get(0));
    assertEquals(Long.valueOf(2), longs.get(1));
    assertEquals(Long.valueOf(3), longs.get(2));
  }
  
  @Test
  public void testForEach() {
    final List<Integer> integers = Arrays.asList(1, 2, 3);

    final Consumer<Integer> consumer = mock(Consumer.class);
    createFluentIterable(integers).forEach(consumer);

    verify(consumer, times(1)).accept(1);
    verify(consumer, times(1)).accept(2);
    verify(consumer, times(1)).accept(3);
    verifyNoMoreInteractions(consumer);

  }

  @Test
  public void testSpliterator() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3);
    final Spliterator<Integer> split = createFluentIterable(integers).spliterator();
    assertNotNull(split);
  }
  
}
