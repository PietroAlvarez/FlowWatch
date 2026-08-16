package dev.pietro.flowwatch.repository;

import dev.pietro.flowwatch.domain.Automation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomationRepository extends JpaRepository<Automation, Long> {
    List<Automation> findAllByOrderByNameAsc();
}
