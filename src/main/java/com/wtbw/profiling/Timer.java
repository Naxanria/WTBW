package com.wtbw.profiling;

/*
  @author: Naxanria
*/
public class Timer
{
  private long startTime = System.currentTimeMillis();
  
  public static Timer start()
  {
    return new Timer();
  }
  
  public long stop()
  {
    return System.currentTimeMillis() - startTime;
  }
  
  public void restart()
  {
    startTime = System.currentTimeMillis();
  }
}
