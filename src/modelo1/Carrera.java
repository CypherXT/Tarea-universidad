package modelo1;

public class Carrera {
    private int id;
    private String nombre;
    private int duracionSemestres;
    private int creditosTotales;
    private String descripcion;
    private String nombreEscuela;
    
    // Constructor vacío
    public Carrera() {}
    
    // Constructor con parámetros
    public Carrera(int id, String nombre, int duracionSemestres, int creditosTotales, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.duracionSemestres = duracionSemestres;
        this.creditosTotales = creditosTotales;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public int getDuracionSemestres() { return duracionSemestres; }
    public void setDuracionSemestres(int duracionSemestres) { this.duracionSemestres = duracionSemestres; }
    
    public int getCreditosTotales() { return creditosTotales; }
    public void setCreditosTotales(int creditosTotales) { this.creditosTotales = creditosTotales; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getNombreEscuela() { return nombreEscuela; }
    public void setNombreEscuela(String nombreEscuela) { this.nombreEscuela = nombreEscuela; }
    
    @Override
    public String toString() {
        return nombre + " - " + duracionSemestres + " semestres";
    }
}