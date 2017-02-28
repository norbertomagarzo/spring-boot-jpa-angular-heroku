package pgoggin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pgoggin.models.Thing;
import pgoggin.models.ThingDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by patrickgoggin on 2/27/17.
 */
@Controller
@RequestMapping("/")
public class ThingController {

    @Autowired
    private ThingDao dao;

//    @RequestMapping(value = {"/", "/index"}, method= RequestMethod.GET)
//    public String index() {
//        return "index.html";
//    }

    @RequestMapping(value = {"/thing/create"}, method = RequestMethod.POST)
    @ResponseBody
    public Thing create(@RequestBody Thing thing, HttpServletRequest req) {
        Thing e = dao.create(thing);
        return e;
    }

    @RequestMapping(value = {"/things"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Thing> getList(HttpServletRequest req) {
        List<Thing> things = dao.getAll();
        return things;
    }

    @RequestMapping(value = {"/thing/createMultiple"}, method = RequestMethod.POST)
    @ResponseBody
    public void createMultiple(@RequestBody Iterable<Thing> things, HttpServletRequest req) {
        dao.createMultiple(things);
    }

    @RequestMapping(value = {"/thing/all"}, method= RequestMethod.GET)
    public List<Thing> getAll(HttpServletRequest req) {
        return dao.getAll();
    }

    @RequestMapping(value = {"/thing/update"}, method= RequestMethod.POST)
    public String update()
    {
        return "index";
    }

}
