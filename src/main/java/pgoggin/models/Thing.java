package pgoggin.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by patrickgoggin on 2/27/17.
 */
@Entity
@Data
public class Thing {
    @Id
    @GeneratedValue
    Long id;

    String name;

    @Column(precision=8, scale=2)
    BigDecimal number;
}
