package com.company.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyDTO {

    private Long id;

    @NotBlank(message = "Name cannot be Empty")
    @Size(min = 2,max = 15)
    private String name;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
