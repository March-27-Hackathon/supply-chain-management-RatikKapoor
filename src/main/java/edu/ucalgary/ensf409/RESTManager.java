package edu.ucalgary.ensf409;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import edu.ucalgary.ensf409.FurniturePart.Types;

/**
 * Class RESTManager creates an API to allow data to be accessed by other
 * applications
 * 
 * @author Risat Haque, Ratik Kapoor, Robert Brown, Anand Patel
 * @since 1.2
 */
@RestController
public class RESTManager {
    public Database database = null;

    public RESTManager() {
        try {
            Dotenv enviroment = Dotenv.load();
            database = new Database("jdbc:mysql://" + enviroment.get("DB_URL"), enviroment.get("DB_USER"),
                    enviroment.get("DB_PASS"));
            database.connect();
            database.storeManufacturers();
        } catch (DotenvException e) {
            System.err.println("Could not load .env file in root folder!");
            System.err.println("Create or move .env file with DB_URL, DB_USER, DB_PASS");
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("Error connecting to database!");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO more exception handeling
        }
    }

    @CrossOrigin
    @GetMapping("/chairs")
    @RequestMapping(value = "/chairs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Chair> chairs(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.CHAIR) : database.getListByType(Types.CHAIR, type);
    }

    @CrossOrigin
    @GetMapping("/desks")
    @RequestMapping(value = "/desks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Desk> desks(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.DESK) : database.getListByType(Types.DESK, type);
    }

    @CrossOrigin
    @GetMapping("/filings")
    @RequestMapping(value = "/filings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Filing> filings(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.FILING) : database.getListByType(Types.FILING, type);
    }

    @CrossOrigin
    @GetMapping("/lamps")
    @RequestMapping(value = "/lamps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Lamp> lamps(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.LAMP) : database.getListByType(Types.LAMP, type);
    }

    // Manufacturers
    @CrossOrigin
    @GetMapping("/manufacturers")
    @RequestMapping(value = "/manufacturers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Manufacturer> manufacturers(@RequestParam(value = "type", defaultValue = "") String type) {
        if (type == "") {
            return database.getManufacturers();
        } else {
            try {
                return database.getManufacturersByType(FurniturePart.Types.fromString(type));
            } catch (Exception e) {
                return null;
            }
        }

    }

    // Builders
    @CrossOrigin
    @GetMapping("/builder/chair")
    @RequestMapping(value = "/builder/chair", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Chair> chairBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {

        Chair l = new Chair(type);
        ArrayList<Chair> allLamps = database.getListByType(Types.CHAIR, type);
        Builder<Chair> b = new Builder<Chair>(l);
        b.setParts(allLamps);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
        if (b.getCost() == -1) {
            return new ArrayList<Chair>();
        }
        ArrayList<Chair> temp = new ArrayList<>();
        for (String id : b.getidCombination()) {
            for (Chair la : allLamps) {
                if (la.getId().equals(id)) {
                    temp.add(la);
                }
            }
        }
        return temp;
    }

    @CrossOrigin
    @GetMapping("/builder/desk")
    @RequestMapping(value = "/builder/desk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Desk> deskBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {
        Desk l = new Desk(type);
        ArrayList<Desk> allDesks = database.getListByType(Types.DESK, type);
        Builder<Desk> b = new Builder<Desk>(l);
        b.setParts(allDesks);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
        if (b.getCost() == -1) {
            return new ArrayList<Desk>();
        }
        ArrayList<Desk> temp = new ArrayList<>();
        for (String id : b.getidCombination()) {
            for (Desk la : allDesks) {
                if (la.getId().equals(id)) {
                    temp.add(la);
                }
            }
        }
        return temp;
    }

    @CrossOrigin
    @GetMapping("/builder/filing")
    @RequestMapping(value = "/builder/filing", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Filing> filingBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {
        Filing f = new Filing(type);
        ArrayList<Filing> allFiling = database.getListByType(Types.FILING, type);
        Builder<Filing> b = new Builder<Filing>(f);
        b.setParts(allFiling);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
        if (b.getCost() == -1) {
            return new ArrayList<Filing>();
        }
        ArrayList<Filing> temp = new ArrayList<>();
        for (String id : b.getidCombination()) {
            for (Filing la : allFiling) {
                if (la.getId().equals(id)) {
                    temp.add(la);
                }
            }
        }
        return temp;
    }

    @CrossOrigin
    @GetMapping("/builder/lamp")
    @RequestMapping(value = "/builder/lamp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Lamp> lampBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {
        Lamp l = new Lamp(type);
        ArrayList<Lamp> allLamps = database.getListByType(Types.LAMP, type);
        Builder<Lamp> b = new Builder<Lamp>(l);
        b.setParts(allLamps);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
        if (b.getCost() == -1) {
            return new ArrayList<Lamp>();
        }
        ArrayList<Lamp> temp = new ArrayList<>();
        for (String id : b.getidCombination()) {
            for (Lamp la : allLamps) {
                if (la.getId().equals(id)) {
                    temp.add(la);
                }
            }
        }
        return temp;
    }

    @CrossOrigin
    @GetMapping("/remove")
    @RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeByID(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "items", defaultValue = "") String[] items) {
        FurniturePart.Types partType = Types.fromString(type);

        boolean result = true;
        for (String item : items) {
            if (!database.removeItemByID(partType, item)) {
                result = false;
            }
        }
        return result;
    }
}
