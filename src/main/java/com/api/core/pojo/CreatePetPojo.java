package com.api.core.pojo;

import java.util.List;

public class CreatePetPojo {

    private int id;
    private PetCategoryPojo category;
    private String name;
    private List<String> photoUrls;
    private List<PetTagPojo> tags;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PetCategoryPojo getCategory() {
        return category;
    }

    public void setCategory(PetCategoryPojo category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<PetTagPojo> getTags() {
        return tags;
    }

    public void setTags(List<PetTagPojo> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
