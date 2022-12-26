package validator.domain;

import java.util.List;

public record ExcellWriter(
        String id,
        String type,
        String name,
        Double ppu,
        Batters batters,
        List<Topping> topping) {

}
