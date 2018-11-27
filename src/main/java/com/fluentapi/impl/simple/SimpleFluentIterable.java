package com.fluentapi.impl.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.fluentapi.FluentIterable;

/**
 * Simple implementation for Fluent Iterable API
 * 
 * @author developer
 * @param <E>
 *          The element to iterate
 */
public class SimpleFluentIterable<E> implements FluentIterable<E> {

  /** The iterable */
  private final Iterable<E> iterable;

  /**
   * Default constructor
   * 
   * @param iterable
   *          The iterable element
   */
  protected SimpleFluentIterable(Iterable<E> iterable) {
    this.iterable = iterable;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<E> iterator()
  {
    return iterable.iterator();
  }

  /*
   * (non-Javadoc)
   * @see com.fluentapi.FluentIterable#filter(java.util.function.Predicate)
   */
  @Override
  public final FluentIterable<E> filter(Predicate<? super E> predicate)
  {
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      E nextElement = (E) iterator.next();
      if (!predicate.test(nextElement)) {
        iterator.remove();
      }
    }
    return this;
  }

  @Override
  public Optional<E> first()
  {
    Iterator<E> resultIterator = first(1).iterator();
    return resultIterator.hasNext() ? Optional.of(resultIterator.next())
        : Optional.empty();
  }

  /*
   * (non-Javadoc)
   * @see com.fluentapi.FluentIterable#first(int)
   */
  @Override
  public FluentIterable<E> first(int count)
  {
    Iterator<E> iterator = iterator();
    int current = 0;
    while (iterator.hasNext()) {
      iterator.next();
      if (current >= count) {
        iterator.remove();
      }
      current++;
    }
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.fluentapi.FluentIterable#last()
   */
  @Override
  public Optional<E> last()
  {
    List<E> list = last(1).asList();
    if (list.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(list.get(0));
  }

  /*
   * (non-Javadoc)
   * @see com.fluentapi.FluentIterable#last(int)
   */
  @Override
  public FluentIterable<E> last(int count)
  {
    int remainingElementsCount = getRemainingElementsCount();
    Iterator<E> iterator = iterator();
    int current = 0;
    while (iterator.hasNext()) {
      iterator.next();
      if (current < (remainingElementsCount - count)) {
        iterator.remove();
      }
      current++;
    }
    return this;
  }

  /*
   * (non-Javadoc)
   * @see com.fluentapi.FluentIterable#map(java.util.function.Function)
   */
  @Override
  public <T> FluentIterable<T> map(Function<? super E, T> function)
  {
    List<T> temporaryList = new ArrayList<>();
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      temporaryList.add(function.apply(iterator.next()));
    }
    return from(temporaryList);
  }

  /*
   * (non-Javadoc)
   * @see com.fluentapi.FluentIterable#asList()
   */
  @Override
  public List<E> asList()
  {
    return toList(iterable.iterator());
  }

  /**
   * Obtain a FluentIterable from a given iterable.
   * 
   * @param iterable
   * @return
   */
  public static <E> FluentIterable<E> from(Iterable<E> iterable)
  {
    return new SimpleFluentIterable<>(iterable);
  }

  /**
   * Makes a copy of a given iterable. Uses the FluentIterable.copyToList
   * utility
   * 
   * @param iterable
   * @return
   */
  public static <E> FluentIterable<E> fromCopyOf(Iterable<E> iterable)
  {
    List<E> copy = FluentIterable.copyToList(iterable);
    return new SimpleFluentIterable<>(copy);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Iterable#forEach(java.util.function.Consumer)
   */
  @Override
  public void forEach(Consumer<? super E> action)
  {
    iterable.forEach(action);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Iterable#spliterator()
   */
  @Override
  public Spliterator<E> spliterator()
  {
    return iterable.spliterator();
  }

  /**
   * Gets the count of remaining objects of the current iterable
   * 
   * @return
   */
  private int getRemainingElementsCount()
  {
    int counter = 0;
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      iterator.next();
      counter++;
    }
    return counter;
  }

  /**
   * Collects the remaining objects of the given iterator into a List.
   * 
   * @param iterator
   * @return
   */
  public static <E> List<E> toList(Iterator<E> iterator)
  {
    List<E> copy = new ArrayList<>();
    while (iterator.hasNext()) {
      copy.add(iterator.next());
    }
    return copy;
  }

}
