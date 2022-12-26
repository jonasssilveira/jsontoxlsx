package validator;

import com.google.gson.Gson;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import validator.domain.Batters;
import validator.domain.ExcellWriter;
import validator.domain.Topping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Validator {

    public static void main(String[] args) throws IOException, InterruptedException {
        JSONParser parser = new JSONParser();
        List<ExcellWriter> writers = new ArrayList<>();


        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("/home/jonas/Documentos/test.json"));

            for (Object obj : jsonArray) {

                JSONObject jsonObject = (JSONObject) obj;
                Gson gson = new Gson();
                JSONArray toppings = (JSONArray) jsonObject.get("topping");
                List<Topping> toppingArrayList = new ArrayList<>();
                toppings.stream().forEach(v -> toppingArrayList.add(gson.fromJson(v.toString(), Topping.class)));//.collect(Collectors.toList());
                ExcellWriter writer = new ExcellWriter((String) jsonObject.get("id"),
                        (String) jsonObject.get("type"), (String) jsonObject.get("name"), (Double) jsonObject.get("ppu"),
                        gson.fromJson(jsonObject.get("batters").toString(), Batters.class), toppingArrayList);
                writer.batters().batter().stream().forEach(System.out::println);
                writer.topping().stream().forEach(System.out::println);
                writers.add(writer);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Student Data ");

        int rowid = 1;
        List<ExcellWriter> collect = writers.stream().sorted((o1, o2) -> Long.compareUnsigned(o1.batters().batter().size(), o1.topping().size()))
                .sorted((o1, o2) -> Long.compareUnsigned(o2.batters().batter().size(), o2.topping().size()))
                .collect(Collectors.toList());
        WriteKeys wk = new WriteKeys(spreadsheet, collect);
        WriteValues wv = new WriteValues(writers, spreadsheet, rowid);
        Thread wkThread = new Thread(wk);
        wkThread.start();
        wkThread.join();
        Thread wvThread = new Thread(wv);
        wvThread.start();
        wvThread.join();
        FileOutputStream out = new FileOutputStream("/home/jonas/Documentos/GFGsheet.xlsx");

        workbook.write(out);
        out.close();
    }


}
