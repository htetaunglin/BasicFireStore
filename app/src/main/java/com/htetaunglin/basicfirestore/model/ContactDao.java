package com.htetaunglin.basicfirestore.model;


import java.util.List;

public interface ContactDao {

    public void save(Contact contact);

    public void update(Contact contact);

    public void delete(Contact contact);

    public List<Contact> select();
}
