package Buttons;

/**
 * Factory class for creating various types of navigation buttons.
 * This class follows the Factory Design Pattern to provide a way
 * to instantiate button objects without exposing the creation logic to the client.
 */
public class ButtonFactory {

    /**
     * Creates a navigation button of a specific type.
     *
     * @param type The type of the navigation button to create, represented by the ButtonType enum.
     * @return NavigationButton instance corresponding to the provided ButtonType.
     * @throws IllegalStateException if an unknown ButtonType is provided.
     */
    public NavigationButton createButton(ButtonType type) {
        switch (type) {
            case EXPLORE -> {
                return new ExploreButton();
            }
            case HOME -> {
                return new HomeButton();
            }
            case IMAGE_UPLOAD -> {
                return new ImageUploadButton();
            }
            case NOTIFICATIONS -> {
                return new NotificationsButton();
            }
            case PROFILE -> {
                return new ProfileButton();
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
