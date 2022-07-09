package org.nnn4eu.nfishe.model;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;
public class MilkProduct implements Product, Comparable<Product> {
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String volumeUnit;
    @NotNull
    private int volumeAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MilkProduct)) return false;
        MilkProduct that = (MilkProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MilkProduct      " + name +
                "\t\t" + volumeAmount +
                " " + volumeUnit +
                "\t\t\t\t" + id;
    }

    public MilkProduct(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MilkProduct(UUID id, String name, String volumeUnit, int volumeAmount) {
        this.id = id;
        this.name = name;
        this.volumeUnit = volumeUnit;
        this.volumeAmount = volumeAmount;
    }

    public String getVolumeUnit() {
        return volumeUnit;
    }

    public int getVolumeAmount() {
        return volumeAmount;
    }

    public void setVolumeAmount(int volumeAmount) {
        this.volumeAmount = volumeAmount;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Product other) {
        int classComparison =
                this.getClass().getName().compareTo(other.getClass().getName());

        if (classComparison != 0) {
            return classComparison;
        }

        return Comparator.comparing(Product::getName)
                .thenComparing(p -> ((MilkProduct) p).getVolumeUnit())
                .thenComparingInt(p -> ((MilkProduct) p).getVolumeAmount())
                .compare(this, other);
    }
}
