package locators.dosen;

import org.openqa.selenium.By;

/**
 * Locator Navbar + dropdown "Tridharma" (role dosen).
 * Sumber: src/components/ui/navigation-menu.jsx + features/kelompok1/components/TridharmaDropdown.jsx
 * Item menu adalah Radix DropdownMenuItem (role=menuitem) -> dibatasi ke role itu agar
 * tidak tertukar dengan link mobile menu yang tersembunyi.
 */
public final class NavbarLocators {

    public static final By TRIDHARMA_TRIGGER = By.xpath("//button[normalize-space()='Tridharma']");
    public static final By MENU_CONTENT = By.cssSelector("[data-slot='dropdown-menu-content']");

    public static By menuItem(String label) {
        return By.xpath(
                "//*[(@role='menuitem' or @data-slot='dropdown-menu-item')]" +
                "[contains(normalize-space(.),'" + label + "')]"
        );
    }

    private NavbarLocators() {}
}
