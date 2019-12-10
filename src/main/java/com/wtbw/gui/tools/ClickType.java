package com.wtbw.gui.tools;

/*
  @author: Naxanria
*/
public enum ClickType
{
  LEFT,
  RIGHT;
  
  public byte toByte()
  {
    return (byte) ordinal();
  }
  
  public static ClickType fromByte(byte b)
  {
    return values()[b % values().length];
  }
}
