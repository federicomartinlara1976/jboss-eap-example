package net.bounceme.chronos.bean;

import java.io.Serializable;
import java.util.Date;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class SessionTestBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    private int contadorVisitas;
    
    @Getter
    @Setter
    private String usuario;
    
    @Getter
    @Setter
    private Date ultimoAcceso;
    
    @PostConstruct
    public void init() {
        ultimoAcceso = new Date();
        contadorVisitas = 0;
    }
    
    public void incrementarContador() {
        contadorVisitas++;
        ultimoAcceso = new Date();
    }
}
