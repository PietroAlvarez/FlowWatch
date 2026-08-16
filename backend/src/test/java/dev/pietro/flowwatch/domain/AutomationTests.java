package dev.pietro.flowwatch.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class AutomationTests {
    @Test
    void togglesBetweenActiveAndPaused() {
        Automation automation = new Automation("Demo", "TI", "Pietro", "Diario", "Prueba");
        assertThat(automation.getStatus()).isEqualTo(AutomationStatus.ACTIVE);
        automation.toggle();
        assertThat(automation.getStatus()).isEqualTo(AutomationStatus.PAUSED);
        automation.toggle();
        assertThat(automation.getStatus()).isEqualTo(AutomationStatus.ACTIVE);
    }
}
