package org.nnn4eu.nfishe.model;


import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class SeafoodProduct implements Product, Comparable<Product>{

    @NotNull
    private UUID id;
    @NotNull
    private String name;

    private String origin;
    boolean isFarmed;

    @Override
    public String toString() {
        return "SeafoodProduct   " + name +
                "\t\t" + origin +
                " " + isFarmed +
                "\t\t\t\t\t\t\t" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeafoodProduct)) return false;
        SeafoodProduct that = (SeafoodProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public SeafoodProduct(UUID id, String name, String origin, boolean isFarmed) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.isFarmed = isFarmed;
    }

    public SeafoodProduct(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isFarmed() {
        return isFarmed;
    }

    public void setFarmed(boolean farmed) {
        isFarmed = farmed;
    }



    @Override
    public int compareTo(Product other) {
        int classComparison =
                this.getClass().getName().compareTo(other.getClass().getName());

        if(classComparison != 0) {
            return classComparison;
        }

        return Comparator.comparing(Product::getName)
                .thenComparing(p-> ((SeafoodProduct)p).isFarmed, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(p-> ((SeafoodProduct)p).getOrigin(), Comparator.nullsLast(Comparator.naturalOrder()))
                .compare(this, other);
    }

}
