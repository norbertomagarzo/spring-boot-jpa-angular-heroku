package pgoggin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pgoggin.models.Thing;
import pgoggin.models.ThingDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickgoggin on 2/27/17.
 */
@Controller
@RequestMapping("/")
public class ThingController {

    @Autowired
    private ThingDao dao;

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

    @RequestMapping(value = {"/thing/all"}, method = RequestMethod.GET)
    public List<Thing> getAll(HttpServletRequest req) {
        return dao.getAll();
    }

    @RequestMapping(value = {"/thing/update"}, method = RequestMethod.PATCH)
    @ResponseBody
    public Thing update(@RequestBody Thing thing, HttpServletRequest req){
        dao.update(thing);
        return dao.getById(thing.getId());
    }

    @RequestMapping(value = {"/thing/delete/all"}, method = RequestMethod.DELETE)
    @ResponseBody
    public List<Thing> deleteAll(HttpServletRequest req){
        dao.deleteAll();
        return new ArrayList<Thing>();
    }

    @RequestMapping(value = {"/thing/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public List<Thing> delete(HttpServletRequest req, @PathVariable("id") String id){
        dao.delete(Long.parseLong(id));
        return dao.getAll();
    }

}
