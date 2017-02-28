package pgoggin.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Created by patrickgoggin on 2/27/17.
 */

@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int stockID;

    @NotNull
    private String t;

    @NotNull
    private String name;

    @NotNull
    private double l;


    private int myShares;

    private String e;


    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }


    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }


    public int getMyShares() {
        return myShares;
    }

    public void setMyShares(int myShares) {
        this.myShares = myShares;
    }
}
