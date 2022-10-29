package com.rmn.toolkit.cards.query.util.pdfUtil;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FontFabricUtil {

    public Font createFontWithBasedColor(Font.FontFamily fontFamily , int fontStyle, int fontSize, Color color) throws DocumentException, IOException {
        Font font = new Font(fontFamily);
        font.setStyle(fontStyle);
        font.setSize(fontSize);
        font.setColor(color.getRed(), color.getGreen(), color.getBlue());
        return font;
    }

    public Font createFontWithRGB(Font.FontFamily fontFamily , int fontStyle, int fontSize, BaseColor color) throws DocumentException, IOException {
        Font font = new Font(fontFamily);
        font.setStyle(fontStyle);
        font.setSize(fontSize);
        font.setColor(color);
        return font;
    }

    public Font createBaseFontWithBasedColor(BaseFont baseFont, int fontStyle, int fontSize, BaseColor color) throws DocumentException, IOException {
        Font font = new Font(baseFont);
        font.setStyle(fontStyle);
        font.setSize(fontSize);
        font.setColor(color);
        return font;
    }

    public Font createBaseFontWithRGB(BaseFont baseFont, int fontStyle, int fontSize, Color color) throws DocumentException, IOException {
        Font font = new Font(baseFont);
        font.setStyle(fontStyle);
        font.setSize(fontSize);
        font.setColor(color.getRed(), color.getGreen(), color.getBlue());
        return font;
    }
}
