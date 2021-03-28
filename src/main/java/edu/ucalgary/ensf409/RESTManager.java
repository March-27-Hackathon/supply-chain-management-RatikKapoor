package edu.ucalgary.ensf409;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ucalgary.ensf409.FurniturePart.Types;

@RestController
public class RESTManager {
    Database database = null;

    public RESTManager() {
        database = new Database("jdbc:mysql://server.ratik.me:25565/INVENTORY", "root", "eNsF409");
        try {
            database.connect();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/chairs")
    @RequestMapping(value = "/chairs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Chair> chairs(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.Chair) : database.getListByType(Types.Chair, type);
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/desks")
    @RequestMapping(value = "/desks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Desk> desks(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.Desk) : database.getListByType(Types.Desk, type);
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/filings")
    @RequestMapping(value = "/filings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Filing> filings(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.Filing) : database.getListByType(Types.Filing, type);
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/lamps")
    @RequestMapping(value = "/lamps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Lamp> lamps(@RequestParam(value = "type", defaultValue = "") String type) {
        return type.length() == 0 ? database.getList(Types.Lamp) : database.getListByType(Types.Lamp, type);
    }

    // Manufacturers
    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/manufacturers")
    @RequestMapping(value = "/manufacturers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Manufacturer> manufacturers() {
        return database.getManufacturers();
    }

    // Builders
    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/builder/chair")
    @RequestMapping(value = "/builder/chair", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Chair> chairBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {

        Chair l = new Chair(type);
        ArrayList<Chair> allLamps = database.getListByType(Types.Chair, type);
        Builder<Chair> b = new Builder<Chair>(l);
        b.setParts(allLamps);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
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

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/builder/desk")
    @RequestMapping(value = "/builder/desk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Desk> deskBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {
        Desk l = new Desk(type);
        ArrayList<Desk> allLamps = database.getListByType(Types.Desk, type);
        Builder<Desk> b = new Builder<Desk>(l);
        b.setParts(allLamps);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
        ArrayList<Desk> temp = new ArrayList<>();
        for (String id : b.getidCombination()) {
            for (Desk la : allLamps) {
                if (la.getId().equals(id)) {
                    temp.add(la);
                }
            }
        }
        return temp;
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/builder/filing")
    @RequestMapping(value = "/builder/filing", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Filing> filingBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {
        Filing l = new Filing(type);
        ArrayList<Filing> allLamps = database.getListByType(Types.Lamp, type);
        Builder<Filing> b = new Builder<Filing>(l);
        b.setParts(allLamps);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
        ArrayList<Filing> temp = new ArrayList<>();
        for (String id : b.getidCombination()) {
            for (Filing la : allLamps) {
                if (la.getId().equals(id)) {
                    temp.add(la);
                }
            }
        }
        return temp;
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/builder/lamp")
    @RequestMapping(value = "/builder/lamp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Lamp> lampBuilder(@RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "number", defaultValue = "1") String number) {
        Lamp l = new Lamp(type);
        ArrayList<Lamp> allLamps = database.getListByType(Types.Lamp, type);
        Builder<Lamp> b = new Builder<Lamp>(l);
        b.setParts(allLamps);
        b.setItems();
        b.BuildMultipleItems(Integer.parseInt(number));
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
}
