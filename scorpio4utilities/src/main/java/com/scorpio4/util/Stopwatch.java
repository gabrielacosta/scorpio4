package com.scorpio4.util;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
public class Stopwatch {
	protected long start = 0, current = 0, resets = 0 ;

	public Stopwatch() {
		reset();
	}

	public void reset() {
		current = System.currentTimeMillis();
		start = current;
	}

	public long getStartTimestamp() {
		return start;
	}

	public long elapsed() {
		long elapsed = System.currentTimeMillis() - current;
		current = System.currentTimeMillis();
		return elapsed;
	}

	public long getTotalTime() {
		return System.currentTimeMillis() - start;
	}

	public long mark() {
		long time = getTotalTime();
		resets++;
		return (time/resets); // average
	}

	public String now() {
		return "" + (int)(getTotalTime()/1000) + "s";
	}

	public String toString() {
		return "" + (int)(elapsed()/1000) + "s";
	}
}
