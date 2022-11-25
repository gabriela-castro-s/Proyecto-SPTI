package edu.eci.cvds.gestor.entities;


public class Resource {

    private String name;
    private String type;
    private int id;
    private String description;
    private Boolean available;
    private int internId;
    private String location;
    private int capacity;

    public Resource(){
        super();
    }

    public Resource(int id, String name, String location, String type, int capacity, int internId, String description, Boolean available) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
        this.available = available;
        this.internId = internId;
        this.capacity = capacity;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public int getInternId() {
        return internId;
    }

    public void setInternId(int internId) {
        this.internId = internId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString(){
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location + '\'' +
        ", description='" + description + '\'' +
                ", available=" + available + '\'' +
        ", internalID=" + internId+ '\'' +
        ", type=" + type +'\''+
        ", capacity=" + capacity +
                '}';
    }

}
