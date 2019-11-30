package com.wtbw.util;

import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class TextComponentBuilder
{
  private ITextComponent parent;
  private ITextComponent current;
  
  private boolean keepStyle = false;
  private boolean insertSpace = false;
  
  private TextComponentBuilder(String text)
  {
    parent = new StringTextComponent(text);
  }
  
  private TextComponentBuilder(ITextComponent component)
  {
    parent = component;
  }
  
  public TextComponentBuilder next(ITextComponent component)
  {
    Style newStyle = setup();
    
    current = component;
    current.setStyle(newStyle);
    
    return this;
  }
  
  public TextComponentBuilder next(String text)
  {
    return next(new StringTextComponent(insertSpace ? " " + text : text));
  }
  
  private Style setup()
  {
    if (current != null)
    {
      parent.appendSibling(current);
    }
    
    Style newStyle = new Style();
    
    if (keepStyle)
    {
      newStyle.setParentStyle(current == null ? parent.getStyle() : current.getStyle());
    }
    
    return newStyle;
  }
  
  public TextComponentBuilder next(int i)
  {
    return next(Integer.toString(i));
  }
  
  public TextComponentBuilder next(float f)
  {
    return next(Float.toString(f));
  }
  
  public TextComponentBuilder next(boolean bool)
  {
    next(Boolean.toString(bool));
    setColor(bool ? TextFormatting.GREEN : TextFormatting.RED);
    
    return this;
  }
  
  public TextComponentBuilder next(Object object)
  {
    if (object == null)
    {
      next("null");
    }
    else
    {
      next(object.toString());
    }
    
    return this;
  }
  
  public TextComponentBuilder nextTranslate(String key, Object... args)
  {
    return next(new TranslationTextComponent(key, args));
  }
  
  public Style getStyle()
  {
    return toEdit().getStyle();
  }
  
  public TextComponentBuilder setStyle(Style style)
  {
    toEdit().setStyle(style);
    
    return this;
  }
  
  public TextComponentBuilder bold()
  {
    return setBold(true);
  }
  
  public TextComponentBuilder setBold(boolean bold)
  {
    getStyle().setBold(bold);
    
    return this;
  }
  
  public TextComponentBuilder italic()
  {
    return setItalic(true);
  }
  
  public TextComponentBuilder setItalic(boolean italic)
  {
    getStyle().setItalic(italic);
    
    return this;
  }
  
  public TextComponentBuilder strikeThrough()
  {
    return setStrikeThrough(true);
  }
  
  public TextComponentBuilder setStrikeThrough(boolean strikeThrough)
  {
    getStyle().setStrikethrough(strikeThrough);
    
    return this;
  }
  
  public TextComponentBuilder underline()
  {
    return setUnderline(true);
  }
  
  public TextComponentBuilder setUnderline(boolean underline)
  {
    getStyle().setUnderlined(underline);
    
    return this;
  }
  
  public TextComponentBuilder setColor(TextFormatting formatting)
  {
    getStyle().setColor(formatting);
    
    return this;
  }
  
  public TextComponentBuilder setClickEvent(ClickEvent clickEvent)
  {
    getStyle().setClickEvent(clickEvent);
    
    return this;
  }
  
  public TextComponentBuilder setHoverEvent(HoverEvent hoverEvent)
  {
    getStyle().setHoverEvent(hoverEvent);
    
    return this;
  }
  
  public TextComponentBuilder black()
  {
    setColor(TextFormatting.BLACK);
    
    return this;
  }
  
  public TextComponentBuilder darkBlue()
  {
    setColor(TextFormatting.DARK_BLUE);
    
    return this;
  }
  public TextComponentBuilder darkGreen()
  {
    setColor(TextFormatting.DARK_GREEN);
    
    return this;
  }
  
  public TextComponentBuilder darkAqua()
  {
    setColor(TextFormatting.DARK_AQUA);
    
    return this;
  }
  
  public TextComponentBuilder darkRed()
  {
    setColor(TextFormatting.DARK_RED);
    
    return this;
  }
  
  public TextComponentBuilder darkPurple()
  {
    setColor(TextFormatting.DARK_PURPLE);
    
    return this;
  }
  
  public TextComponentBuilder gold()
  {
    setColor(TextFormatting.GOLD);
    
    return this;
  }
  
  public TextComponentBuilder gray()
  {
    setColor(TextFormatting.GRAY);
    
    return this;
  }
  
  public TextComponentBuilder darkGray()
  {
    setColor(TextFormatting.DARK_GRAY);
    
    return this;
  }
  
  public TextComponentBuilder blue()
  {
    setColor(TextFormatting.BLUE);
    
    return this;
  }
  
  public TextComponentBuilder green()
  {
    setColor(TextFormatting.GREEN);
    
    return this;
  }
  
  public TextComponentBuilder aqua()
  {
    setColor(TextFormatting.AQUA);
    
    return this;
  }
  
  public TextComponentBuilder red()
  {
    setColor(TextFormatting.RED);
    
    return this;
  }
  
  public TextComponentBuilder lightPurple()
  {
    setColor(TextFormatting.LIGHT_PURPLE);
    
    return this;
  }
  
  public TextComponentBuilder yellow()
  {
    setColor(TextFormatting.YELLOW);
    
    return this;
  }
  
  public TextComponentBuilder white()
  {
    setColor(TextFormatting.WHITE);
    
    return this;
  }
  
  public TextComponentBuilder keepStyle()
  {
    keepStyle = true;
    return this;
  }
  
  public TextComponentBuilder setKeepStyle(boolean keepStyle)
  {
    this.keepStyle = keepStyle;
    return this;
  }
  
  public TextComponentBuilder insertSpaces()
  {
    insertSpace = true;
    return this;
  }
  
  public TextComponentBuilder setInsertSpace(boolean insertSpace)
  {
    this.insertSpace = insertSpace;
    return this;
  }
  
  private ITextComponent toEdit()
  {
    return current != null ? current : parent;
  }
  
  public ITextComponent build()
  {
    if (current != null)
    {
      parent.appendSibling(current);
    }
    
    return parent;
  }
  
  public static TextComponentBuilder create(String text)
  {
    return create(text, true, false);
  }
  
  public static TextComponentBuilder create(String text, boolean insertSpace)
  {
    return create(text, true, insertSpace);
  }
  
  public static TextComponentBuilder create(String text, boolean keepStyle, boolean insertSpace)
  {
    TextComponentBuilder builder = new TextComponentBuilder(text);
    builder.keepStyle = keepStyle;
    builder.insertSpace = insertSpace;
    return builder;
  }
  
  public static TextComponentBuilder createTranslated(String text, Object... args)
  {
    TextComponentBuilder builder = new TextComponentBuilder(new TranslationTextComponent(text, args));
    return builder;
  }
  
}
