package dev.root101.trmi_eltoque.feature.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repo para gestionar la entidad `Trmi`
 *
 * @author Yo
 */
@Repository
public interface TrmiRepo extends JpaRepository<TrmiEntity, Integer> {

    public TrmiEntity findFirstByOrderByRegisterDateDesc();
}
