package org.example;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BuddyInfo> buddyList = new ArrayList<>();

    public AddressBook() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BuddyInfo> getBuddyList() {
        return buddyList;
    }

    public void setBuddyList(List<BuddyInfo> buddyList) {
        this.buddyList = buddyList;
    }

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            buddyList.add(buddy);
        }
    }

    public BuddyInfo getBuddy(int index) {
        return buddyList.get(index);
    }

    public void removeBuddy(int index) {
        if (index >= 0 && index < buddyList.size()) {
            buddyList.remove(index);
        }
    }

    @Override
    public String toString() {
        return "AddressBook{id=" + id + ", buddyList=" + buddyList + "}";
    }
}