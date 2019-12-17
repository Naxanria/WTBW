package com.wtbw.util;

/*
  @author: Naxanria
*/
public class ColorUtil
{
  public static final int WHITE = 0xffffffff;
  public static final int BLACK = 0xff000000;
  
  public static int[] getRGBAi(int argb)
  {
    return new int[]
    {
      (argb >> 16) & 0xff,
      (argb >> 8) & 0xff,
      (argb) & 0xff,
      (argb >> 24) & 0xff
    };
  }
  
  public static float[] getRGBAf(int argb)
  {
    int[] rgba = getRGBAi(argb);
    return new float[]
    {
      rgba[0] / 256f,
      rgba[1] / 256f,
      rgba[2] / 256f,
      rgba[3] / 256f
    };
  }
  
  public static int color(int r, int g, int b, int a)
  {
    return (a << 24) | (r << 16) | (g << 8) | b;
  }
  
  public static int color(float r, float g, float b, float a)
  {
    return color((int) (r * 256),(int) (g * 256), (int) (b * 256), (int) (a * 256));
  }
}
