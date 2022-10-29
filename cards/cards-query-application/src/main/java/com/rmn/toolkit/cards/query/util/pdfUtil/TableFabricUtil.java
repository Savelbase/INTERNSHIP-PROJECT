package com.rmn.toolkit.cards.query.util.pdfUtil;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import static com.itextpdf.text.BaseColor.WHITE;

@Component
@RequiredArgsConstructor
public class TableFabricUtil {

    @SneakyThrows
    public PdfPTable createPdfTableFrame(int columnsNumber, int tableWidthPercentage, float spacingBeforeTable,
                                                     float spacingAfterTable) {

        PdfPTable table = new PdfPTable(columnsNumber);
        table.setWidthPercentage(tableWidthPercentage);
        table.setSpacingBefore(spacingBeforeTable);
        table.setSpacingAfter(spacingAfterTable);

        return table;
    }

    @SneakyThrows
    public PdfPTable setPdfColumnWidthsRelation(PdfPTable table, float[] columnsWidth) {

        table.setWidths(columnsWidth);

        return table;
    }

    public PdfPCell createPdfCell(Paragraph paragraph, BaseColor frameColor, int horizontalAlign, int verticalAlign ){
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorderColor(frameColor);
        cell.setPaddingLeft(10);
        cell.setHorizontalAlignment(horizontalAlign);
        cell.setVerticalAlignment(verticalAlign);
        return cell;
    }
}
