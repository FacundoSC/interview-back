package com.meli.interview.back.subscription_api.datos;

import javax.persistence.*;

@Entity
@Table(name = "suscripciones")
@Inheritance(strategy = InheritanceType.JOINED)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String partner;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false, updatable = false)
    private User user;

    public float getPrice() {
        float price = 0;
        String partner = this.partner;

        switch (partner) {
            case "disney":
                price = 100;
                break;

            case "netflix":
                price = 200;
                break;

            case "spotify":
                price = 50;
                break;
        }
        return price;
    }
}
