package pgoggin.controllers;

import pgoggin.models.Quote;
import pgoggin.models.Stock;
import pgoggin.models.StockDao;
import pgoggin.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
  @Autowired
  private StockDao dao;

  @RequestMapping(value = "/")
  public String index() {
    return "index.html";
  }

}
