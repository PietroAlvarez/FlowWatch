package dev.pietro.flowwatch.repository;

import dev.pietro.flowwatch.domain.AutomationRun;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomationRunRepository extends JpaRepository<AutomationRun, Long> {
    List<AutomationRun> findTop20ByOrderByStartedAtDesc();
}
