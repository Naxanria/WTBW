package com.wtbw.util;

import java.util.HashMap;
import java.util.Map;

/*
  @author: Naxanria
*/
public class EmptyCollections
{
  public static <K, V> Map<K, V> map()
  {
    return new HashMap<>();
  }
}
