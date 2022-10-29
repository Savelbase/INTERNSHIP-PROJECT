package com.rmn.toolkit.cards.query.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.rmn.toolkit.cards.query.dto.response.success.ReceiptDto;
import com.rmn.toolkit.cards.query.util.pdfUtil.FontFabricUtil;
import com.rmn.toolkit.cards.query.util.pdfUtil.ImageFabricUtil;
import com.rmn.toolkit.cards.query.util.pdfUtil.ParagraphFabricUtil;
import com.rmn.toolkit.cards.query.util.pdfUtil.TableFabricUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.itextpdf.text.BaseColor.*;
import static com.itextpdf.text.BaseColor.WHITE;
import static com.itextpdf.text.Chunk.NEWLINE;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Font.ITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.Rectangle.BOX;
import static java.awt.Font.BOLD;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatePDFService {

    private final ReceiptsService cardStatementService;
    private final FontFabricUtil fontFabricUtil;
    private final ParagraphFabricUtil paragraphFabricUtil;
    private final TableFabricUtil tableFabricUtil;

    private final ImageFabricUtil imageFabricUtil;

    private static final int LARGE_FONT_SIZE = 20;
    private static final int MIDDLE_FONT_SIZE = 14;
    private static final int LITTLE_FONT_SIZE = 12;
    private static final int MINY_FONT_SIZE = 8;
    private static final String STANDARD_FONT = "arial.ttf";
    private static final String HEADLINE = "Выписка по карте сформирована автоматически";
    private static final  String DOC_HEAD = "Выписка по счёту карты";

    private static final String DATE_TIME_DESCRIPTION = "Время операции/";
    private static final String TRANSACTION_TYPE_DESCRIPTION = "Тип операции/";
    private static final String TRANSACTION_LOCATION = "Место операции/";
    private static final String SUMMARY_DESCRIPTION = "Сумма операции/";

    private static final  String IMAGE_FILE_NAME = "logo.png";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss z");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();

    public InputStream getCardStatementInPDF(String cardId, ZonedDateTime startPeriod, ZonedDateTime endPeriod) {
        try {

            Document pdfDoc = new Document(PageSize.A4, 20, 30, 40, 40);
            File file = new File("statement_" + cardId + ".pdf");
            FileOutputStream fos = new FileOutputStream(file);

            PdfWriter.getInstance(pdfDoc, fos)
                    .setPdfVersion(PdfWriter.PDF_VERSION_1_7);

            pdfDoc.open();

            BaseFont baseFont = BaseFont.createFont(STANDARD_FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font largeBoldFont = fontFabricUtil.createBaseFontWithBasedColor(baseFont, BOLD, LARGE_FONT_SIZE, BLACK);
            Font middleBoldFont = fontFabricUtil.createBaseFontWithBasedColor(baseFont, BOLD, MIDDLE_FONT_SIZE, BLACK);
            Font littleNormalFont = fontFabricUtil.createBaseFontWithBasedColor(baseFont, NORMAL, LITTLE_FONT_SIZE, BLACK);
            Font minyItalicGrayFont = fontFabricUtil.createBaseFontWithBasedColor(baseFont, ITALIC, MINY_FONT_SIZE, DARK_GRAY);


            Paragraph headlineParagraph = new Paragraph(HEADLINE, minyItalicGrayFont);
            pdfDoc.add(headlineParagraph);

            Path logoPath = Paths.get(ClassLoader.getSystemResource(IMAGE_FILE_NAME).toURI());
            Image stamp = imageFabricUtil.createImageWithAbsolutePosition(logoPath, 100, 80, 0.75F, 0.85F);
            stamp.setBorder(BOX);
            stamp.setBorderWidth(10);
            stamp.setBorderColor(BLACK);

            pdfDoc.add(stamp);

            Paragraph docHeadParagraph = new Paragraph(DOC_HEAD, largeBoldFont);
            Chunk cardIdChunk = new Chunk("\n" + "№" + cardId);
            cardIdChunk.setFont(middleBoldFont);
            docHeadParagraph.add(cardIdChunk);
            docHeadParagraph.add(NEWLINE);
            docHeadParagraph.add(NEWLINE);
            docHeadParagraph.add(NEWLINE);
            docHeadParagraph.setAlignment(ALIGN_CENTER);
            pdfDoc.add(docHeadParagraph);

            Paragraph cardStatementsPeriodParagraph = new Paragraph("Расшифровка операций за период с " + dateFormatter.format(startPeriod)
                    + " по " + dateFormatter.format(endPeriod), minyItalicGrayFont);
            cardStatementsPeriodParagraph.add(NEWLINE);
            cardStatementsPeriodParagraph.add(NEWLINE);
            cardStatementsPeriodParagraph.setAlignment(Element.ALIGN_RIGHT);
            pdfDoc.add(cardStatementsPeriodParagraph);

            pdfDoc.add(dottedLineSeparator);

            PdfPTable tableFrame = tableFabricUtil.createPdfTableFrame(4, 100, 10f, 20f);
            float[] columnWidths = {0.9f, 1.25f, 1.25f, 1f};
            PdfPTable table = tableFabricUtil.setPdfColumnWidthsRelation(tableFrame, columnWidths);

            Chunk dateTimeChunk = new Chunk("Transaction date");
            dateTimeChunk.setFont(minyItalicGrayFont);
            Paragraph dateTimeDescriptionParagraph = paragraphFabricUtil
                    .createDotedLineCellParagraphWithChunk(DATE_TIME_DESCRIPTION, littleNormalFont, dateTimeChunk);
            PdfPCell cell1 = tableFabricUtil.createPdfCell(dateTimeDescriptionParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);

            Chunk typeChunk = new Chunk("Transaction type");
            typeChunk.setFont(minyItalicGrayFont);
            Paragraph transactionTypeDescriptionParagraph = paragraphFabricUtil
                    .createDotedLineCellParagraphWithChunk(TRANSACTION_TYPE_DESCRIPTION, littleNormalFont, typeChunk);
            PdfPCell cell2 = tableFabricUtil.createPdfCell(transactionTypeDescriptionParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);

            Chunk locationChunk = new Chunk("Transaction location");
            locationChunk.setFont(minyItalicGrayFont);
            Paragraph locationDescriptionParagraph = paragraphFabricUtil
                    .createDotedLineCellParagraphWithChunk(TRANSACTION_LOCATION, littleNormalFont, locationChunk);
            PdfPCell cell3 = tableFabricUtil.createPdfCell(locationDescriptionParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);

            Chunk summaryChunk = new Chunk("Transaction location");
            summaryChunk.setFont(minyItalicGrayFont);
            Paragraph summaryDescriptionParagraph = paragraphFabricUtil
                    .createDotedLineCellParagraphWithChunk(SUMMARY_DESCRIPTION, littleNormalFont, summaryChunk);
            PdfPCell cell4 = tableFabricUtil.createPdfCell(summaryDescriptionParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);


            for (ReceiptDto cardStatementDto : cardStatementService.getCardStatementsWithoutPagination(cardId, startPeriod, endPeriod)) {

                Paragraph dateParagraph = new Paragraph(dateTimeFormatter.format(cardStatementDto.getDateTime()), littleNormalFont);
                PdfPCell cellDate = tableFabricUtil.createPdfCell(dateParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);
                table.addCell(cellDate);

                Paragraph typeParagraph = new Paragraph(cardStatementDto.getTransactionType().toString(), littleNormalFont);
                PdfPCell cellType = tableFabricUtil.createPdfCell(typeParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);
                table.addCell(cellType);

                Paragraph locationParagraph = new Paragraph(cardStatementDto.getTransactionLocation(), littleNormalFont);
                PdfPCell cellLocation = tableFabricUtil.createPdfCell(locationParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);
                table.addCell(cellLocation);

                Paragraph summaryParagraph = new Paragraph(cardStatementDto.getSumWithCurrency().toString(), littleNormalFont);
                PdfPCell cellSummary = tableFabricUtil.createPdfCell(summaryParagraph, WHITE, ALIGN_CENTER, ALIGN_MIDDLE);
                table.addCell(cellSummary);

            }

            pdfDoc.add(table);

            String dateTime = "\n" + "Date of formation " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " " +
                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Paragraph dateTimeParagraph = new Paragraph(dateTime, minyItalicGrayFont);
            dateTimeParagraph.setAlignment(Element.ALIGN_RIGHT);
            pdfDoc.add(dateTimeParagraph);

            pdfDoc.close();

            fos.close();

            return new FileInputStream(file);

        } catch (DocumentException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
