package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.AdsModel;

import java.util.List;

public interface AdsRepository extends JpaRepository<AdsModel, Long> {
//    List<AdsModel> findByTitleLike(String s);

    List<AdsModel> findAdsModelByUserId(Long id);

    List<AdsModel> findByTitleLikeIgnoreCase(String s);
}
