package sem2_1.task6.model;

import java.util.List;

public class Molecule {
    private final String description;
    private final List<Atom> atoms;

    public Molecule(String description, List<Atom> atoms) {
        this.description = description;
        this.atoms = List.copyOf(atoms);
    }

    public String getDescription() {
        return description;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }
}
