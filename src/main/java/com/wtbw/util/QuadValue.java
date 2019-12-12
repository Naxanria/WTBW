package com.wtbw.util;

/*
  @author: Naxanria
*/
public class QuadValue<A, B, C, D>
{
  public final A a;
  public final B b;
  public final C c;
  public final D d;
  
  public QuadValue(A a, B b, C c, D d)
  {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
  
  @Override
  public int hashCode()
  {
    return a.hashCode() ^ b.hashCode() ^ c.hashCode() ^ d.hashCode();
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof QuadValue))
    {
      return false;
    }
    
    if (obj == this)
    {
      return true;
    }
  
    QuadValue quadValue = (QuadValue) obj;
    
    return a.equals(quadValue.a) && b.equals(quadValue.b) && c.equals(quadValue.c) && d.equals(quadValue.d);
  }
}
