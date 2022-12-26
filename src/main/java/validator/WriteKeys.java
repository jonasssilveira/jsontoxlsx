package validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import validator.domain.Batter;
import validator.domain.ExcellWriter;
import validator.domain.Topping;

import java.util.List;

public class WriteKeys implements Runnable {

    private XSSFSheet spreadsheet;
    private List<ExcellWriter> collect;

    public WriteKeys(XSSFSheet spreadsheet, List<ExcellWriter> collect) {
        this.spreadsheet = spreadsheet;
        this.collect = collect;
    }

    @Override
    public void run() {
        XSSFRow row;
        for (ExcellWriter excellWriter : this.collect) {
            row = this.spreadsheet.createRow(0);
            int cellId = 0;
            Cell cell = row.createCell(0);
            cell.setCellValue("ID");
            cell = row.createCell(cellId++);
            cell.setCellValue("Name");
            cell = row.createCell(cellId++);
            cell.setCellValue("Type");
            cell = row.createCell(cellId++);
            cell.setCellValue("PPU");
            cell = row.createCell(cellId++);
            for (Batter batter : excellWriter.batters().batter()) {
                cell.setCellValue("Batter id");
                cell = row.createCell(cellId++);
                cell.setCellValue("Batter type");
                cell = row.createCell(cellId++);
            }
            for (Topping topping : excellWriter.topping()) {
                cell.setCellValue("Topping id");
                cell = row.createCell(cellId++);
                cell.setCellValue("Topping type");
                cell = row.createCell(cellId++);
                System.out.println(cellId);
            }
        }
    }
}
