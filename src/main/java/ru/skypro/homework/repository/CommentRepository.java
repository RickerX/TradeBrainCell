package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.CommentModel;
import ru.skypro.homework.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    Optional<CommentModel> findDistinctFirstByTextEqualsAndUserEqualsAndAdsEquals(String text, UserModel user, AdsModel ads);
    List<CommentModel> findAllByAds_Id(Long ads_id);
}
