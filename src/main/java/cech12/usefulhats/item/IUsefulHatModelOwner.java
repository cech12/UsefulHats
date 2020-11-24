package cech12.usefulhats.item;

/**
 * Hats with this Interface are rendered via UsefulHatModel.
 * Mostly it is better to use a blank texture for the normal helmet rendering.
 */
public interface IUsefulHatModelOwner {

    default boolean hasChristmasVariant() {
        return false;
    }

}
