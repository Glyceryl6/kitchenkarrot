package io.github.tt432.kitchenkarrot.client.plate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class PlateList {
    public static final PlateList INSTANCE = new PlateList();

    private PlateList() {}

    public List<String> plates = new ArrayList<>();
}
