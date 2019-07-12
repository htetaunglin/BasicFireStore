package com.htetaunglin.basicfirestore.model;


import java.util.List;

public class ContactDaoImpl implements ContactDao {

    private static ContactDao contactDao;

    private ContactDaoImpl() {
    }


    public static ContactDao getInstance() {
        if (contactDao == null)
            contactDao = new ContactDaoImpl();
        return contactDao;
    }

    @Override
    public void save(Contact contact) {
        //TODO Firebase
    }

    @Override
    public void update(Contact contact) {
        //TODO Firebase
    }

    @Override
    public void delete(Contact contact) {
        //TODO Firebase
    }

    @Override
    public List<Contact> select() {
        //TODO Firebase
        return null;
    }
}
