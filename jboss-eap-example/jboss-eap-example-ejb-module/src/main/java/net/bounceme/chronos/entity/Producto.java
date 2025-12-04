package net.bounceme.chronos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "productos")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
@NamedQuery(
    name = "Producto.findByCategoria",
    query = "SELECT p FROM Producto p WHERE p.categoria = :categoria",
    hints = {
        @QueryHint(name = "org.hibernate.cacheable", value = "true"),
        @QueryHint(name = "org.hibernate.cacheRegion", value = "query.ProductoByCategoria")
    }
)
@Data
@EqualsAndHashCode
@ToString
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Version
    @Column(name = "version")
    private Long version;
    
    // Constructores
    public Producto() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Producto(String nombre, String descripcion, BigDecimal precio, Integer stock) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }
    
    // Métodos de negocio
    public void aumentarStock(Integer cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
        }
    }
    
    public void disminuirStock(Integer cantidad) {
        if (cantidad > 0 && this.stock >= cantidad) {
            this.stock -= cantidad;
        }
    }
}