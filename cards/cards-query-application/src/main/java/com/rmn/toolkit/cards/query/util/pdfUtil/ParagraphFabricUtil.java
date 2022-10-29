package com.rmn.toolkit.cards.query.util.pdfUtil;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.itextpdf.text.Chunk.NEWLINE;
@Component
@RequiredArgsConstructor
public class ParagraphFabricUtil {

    private final DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();

    public Paragraph createDotedLineCellParagraphWithChunk(String paragraphText, Font font, Chunk chunk) {
        Paragraph paragraph = new Paragraph(paragraphText, font);
        paragraph.add(NEWLINE);
        paragraph.add(chunk);
        paragraph.add(NEWLINE);
        paragraph.add(dottedLineSeparator);

        return paragraph;
    }
}
