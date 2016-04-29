package it.smartcommunitylab.tataapp.repo;

import org.springframework.data.repository.CrudRepository;

import it.smartcommunitylab.tataapp.model.Family;

public interface FamilyRepo extends CrudRepository<Family, String> {

}
