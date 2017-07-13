package com.adaming.myapp.tools;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.adaming.myapp.entities.Questions;

public class ComparatorQuestion implements Comparator<Questions> {

	@Override
	public int compare(Questions q1, Questions q2) {
		// TODO Auto-generated method stub
		return q1.getNumeroQuestion() - q2.getNumeroQuestion();
	}

	@Override
	public Comparator<Questions> reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Questions> thenComparing(
			Comparator<? super Questions> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> Comparator<Questions> thenComparing(
			Function<? super Questions, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<Questions> thenComparing(
			Function<? super Questions, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Questions> thenComparingInt(
			ToIntFunction<? super Questions> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Questions> thenComparingLong(
			ToLongFunction<? super Questions> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Questions> thenComparingDouble(
			ToDoubleFunction<? super Questions> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T, U> Comparator<T> comparing(
			Function<? super T, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
			Function<? super T, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> comparingInt(
			ToIntFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> comparingLong(
			ToLongFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> comparingDouble(
			ToDoubleFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	
	

	

	

	

}
