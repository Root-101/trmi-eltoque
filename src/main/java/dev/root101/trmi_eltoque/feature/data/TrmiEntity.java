package dev.root101.trmi_eltoque.feature.data;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;

import lombok.*;

/**
 * Entidad para la tabla Trmi, almacena los valores de EUR, USD & MLC en un
 * momento del tiempo determinado.
 *
 * @author Yo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trmi", catalog = "trmi-el-toque", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"register_date"})})
public class TrmiEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trmi_id", nullable = false)
    private Integer trmiId;

    @Min(value = 0)
    @Basic(optional = false)
    @Column(name = "eur", nullable = false, precision = 6, scale = 3)
    private BigDecimal eur;

    @Min(value = 0)
    @Basic(optional = false)
    @Column(name = "usd", nullable = false, precision = 6, scale = 3)
    private BigDecimal usd;

    @Min(value = 0)
    @Basic(optional = false)
    @Column(name = "mlc", nullable = false, precision = 6, scale = 3)
    private BigDecimal mlc;

    @Basic(optional = false)
    @Column(name = "register_date", nullable = false)
    private ZonedDateTime registerDate;

    @Basic(optional = false)
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}
