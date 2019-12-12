package com.wtbw.gui.util;

/*
  @author: Naxanria
*/
public class Sprite
{
  public final SpriteMap map;
  public final int width;
  public final int height;
  public final int u;
  public final int v;
  
  Sprite(SpriteMap map, int width, int height, int u, int v)
  {
    this.map = map;
    this.width = width;
    this.height = height;
    this.u = u;
    this.v = v;
  }
  
  public void render(int x, int y)
  {
    render(x, y, 0xffffffff);
  }
  
  public void render(int x, int y, int color)
  {
    map.render(x, y, color, this);
  }
}
