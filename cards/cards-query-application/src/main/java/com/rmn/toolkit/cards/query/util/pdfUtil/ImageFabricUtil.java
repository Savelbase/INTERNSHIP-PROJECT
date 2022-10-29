package com.rmn.toolkit.cards.query.util.pdfUtil;


import com.itextpdf.text.Image;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class ImageFabricUtil {

    static final float widthA4 = 595F;
    static final float heightA4 = 842F;

    @SneakyThrows
    public Image createImageWithAbsolutePosition(Path path, int widthImage, int heightImage,
                                                 float absolutePositionWidthPercent,
                                                 float absolutePositionHeightPercent) {

            Image image = Image.getInstance(path.toAbsolutePath().toString());
            image.scaleAbsoluteWidth(widthImage);
            image.scaleAbsoluteHeight(heightImage);
            image.setAbsolutePosition(widthA4 * absolutePositionWidthPercent, heightA4 * absolutePositionHeightPercent);
            return image;
    }


}
