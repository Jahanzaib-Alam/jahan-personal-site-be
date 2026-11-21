package org.jahanzaib.personalsite.prayertimes.repository;

import java.util.Optional;
import org.jahanzaib.personalsite.prayertimes.entity.Mosque;
import org.springframework.data.repository.Repository;

public interface MosqueRepository extends Repository<Mosque, Integer> {
  Optional<Mosque> findById(int id);
}
