package validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import validator.domain.Batter;
import validator.domain.ExcellWriter;
import validator.domain.Topping;

import java.util.List;

public class WriteValues implements Runnable {
    private List<ExcellWriter> writers;
    private XSSFSheet spreadsheet;
    private int rowid;

    public WriteValues(List< ExcellWriter > writers, XSSFSheet spreadsheet, int rowid) {
        this.rowid =rowid;
        this.spreadsheet = spreadsheet;
        this.writers = writers;
    }

    @Override
    public void run() {
        XSSFRow row;
        for (ExcellWriter excellWriter : this.writers) {
            row = this.spreadsheet.createRow(rowid++);
            int cellId = 0;
            Cell cell = row.createCell(0);
            cell.setCellValue((String) excellWriter.id());
            cell = row.createCell(cellId++);
            cell.setCellValue((String) excellWriter.name());
            cell = row.createCell(cellId++);
            cell.setCellValue((String) excellWriter.type());
            cell = row.createCell(cellId++);
            cell.setCellValue((Double) excellWriter.ppu());
            cell = row.createCell(cellId++);
            for (Batter batter : excellWriter.batters().batter()) {
                cell.setCellValue((String) batter.id());
                cell = row.createCell(cellId++);
                cell.setCellValue((String) batter.type());
                cell = row.createCell(cellId++);
            }
            for (Topping topping : excellWriter.topping()) {
                cell.setCellValue((String) topping.id());
                cell = row.createCell(cellId++);
                cell.setCellValue((String) topping.type());
                cell = row.createCell(cellId++);

            }

        }
    }
}
