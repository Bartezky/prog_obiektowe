package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;

public class GrassField extends AbstractWorldMap {

    private final Grass[] tufts;

    public GrassField(int numberOfTuft) {
        super();
        tufts = new Grass[numberOfTuft];
        randomTuft(numberOfTuft);
    }

    private void randomTuft(int numberOfTuft) {
        int limit = (int) Math.sqrt(numberOfTuft * 10) + 1;
        ArrayList<Integer> randNumbers = new ArrayList<Integer>(limit * limit);
        for (int i = 0; i < limit * limit; i++)
            randNumbers.add(i);
        Collections.shuffle(randNumbers);
        for (int i = 0; i < numberOfTuft; i++) {
            Vector2d randPosition = new Vector2d(randNumbers.get(i) / (limit), randNumbers.get(i) % limit);
            tufts[i] = new Grass(randPosition);
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        Animal animal = animalAt(position);
        if (animal != null)
            return animal;
        else
            return tuftAt(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return animalAt(position) == null;
    }

    public Grass tuftAt(Vector2d position) {
        for (Grass tuft : tufts) {
            if (tuft.location().equals(position))
                return tuft;
        }
        return null;
    }

    protected Vector2d[] getBorders() {
        int left = animals.get(0).location().x();
        int right = left;
        int low = animals.get(0).location().y();
        int up = low;
        for (Animal animal : animals) {
            Vector2d location = animal.location();
            left = Math.min(left, location.x());
            right = Math.max(right, location.x());
            low = Math.min(low, location.y());
            up = Math.max(up, location.y());
        }
        for (Grass tuft : tufts) {
            Vector2d location = tuft.location();
            left = Math.min(left, location.x());
            right = Math.max(right, location.x());
            low = Math.min(low, location.y());
            up = Math.max(up, location.y());
        }
        return new Vector2d[]{new Vector2d(left, low), new Vector2d(right, up)};
    }

}