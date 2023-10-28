package enviro.fund.production.repository;

import enviro.fund.production.model.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {
}
