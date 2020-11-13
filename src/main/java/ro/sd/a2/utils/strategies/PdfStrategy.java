package ro.sd.a2.utils.strategies;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import ro.sd.a2.dto.TransactionTableDto;
import ro.sd.a2.entity.Transaction;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Strategy class for generating a pdf with the transactions.
 */
public class PdfStrategy implements Strategy{
    /**
     * Build a pdf file using the given transaction list.
     * @param transactionList The transactions we want to put in the csv.
     * @return The list of transactions.
     */
    @Override
    public String generate(List<TransactionTableDto> transactionList) throws FileNotFoundException, Exception {
        Document document = new Document();
        String filename = "pdf\\"+UUID.randomUUID().toString()+".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
        } catch (DocumentException e) {
            throw new Exception();
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        for(TransactionTableDto transactionTableDto: transactionList)
        {
            document.add(new Paragraph(transactionTableDto.getPaymentDate().toString()+":"));
            document.add(Chunk.NEWLINE);
            document.add( new Paragraph( "IBAN: "+transactionTableDto.getIban()) );
            document.add(new Paragraph("Provider: "+transactionTableDto.getProvider()));
            document.add(new Paragraph("Sum: "+transactionTableDto.getSum()));
            document.add(new Paragraph("Valute: "+transactionTableDto.getValute()));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        }
        document.close();
        return filename;

    }
}
