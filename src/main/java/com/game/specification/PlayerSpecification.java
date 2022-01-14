package com.game.specification;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class PlayerSpecification {

    public Specification<Player> getSpecification(Map<String, String> params) {
        return where(
                params.containsKey("name") ? containsName(params.get("name")) : containsName(null)
        ).and(
                params.containsKey("title") ? containsTitle(params.get("title")) : containsTitle(null)
        ).and(
                params.containsKey("race") ? hasRace(Race.valueOf(params.get("race"))) : hasRace(null)
        ).and(
                params.containsKey("profession") ?
                        hasProfession(Profession.valueOf(params.get("profession"))) :
                        hasProfession(null)
        ).and(
                params.containsKey("banned") ?
                        hasBannedStatus(Boolean.parseBoolean(params.get("banned"))) :
                        hasBannedStatus(null)
        ).and(
                params.containsKey("after") ?
                        hasBirthdayGreaterThan(new Date(Long.parseLong(params.get("after")))) :
                        hasBirthdayGreaterThan(null)
        ).and(
                params.containsKey("before") ?
                        hasBirthdayLessThan(new Date(Long.parseLong(params.get("before")))) :
                        hasBirthdayLessThan(null)
        ).and(
                params.containsKey("minExperience") ?
                        hasExperienceGreaterThanOrEqualTo(Integer.parseInt(params.get("minExperience"))) :
                        hasExperienceGreaterThanOrEqualTo(null)
        ).and(
                params.containsKey("maxExperience") ?
                        hasExperienceLessThanOrEqualTo(Integer.parseInt(params.get("maxExperience"))) :
                        hasExperienceLessThanOrEqualTo(null)
        ).and(
                params.containsKey("minLevel") ?
                        hasLevelGreaterThanOrEqualTo(Integer.parseInt(params.get("minLevel"))) :
                        hasLevelGreaterThanOrEqualTo(null)
        ).and(
                params.containsKey("maxLevel") ?
                        hasLevelLessThanOrEqualTo(Integer.parseInt(params.get("maxLevel"))) :
                        hasLevelLessThanOrEqualTo(null)
        );
    }

    private Specification<Player> containsName(String name) {
        return (root, cq, cb) -> name != null ?
                cb.like(root.get("name"), '%' + name+ '%') : cb.conjunction();
    }

    private Specification<Player> containsTitle(String title) {
        return (root, cq, cb) -> title != null ?
                cb.like(root.get("title"), '%' + title + '%') : cb.conjunction();
    }

    private Specification<Player> hasRace(Race race) {
        return (root, cq, cb) -> race != null ?
                cb.equal(root.get("race"), race) : cb.conjunction();
    }

    private Specification<Player> hasProfession(Profession profession) {
        return (root, cq, cb) -> profession != null ?
                cb.equal(root.get("profession"), profession) : cb.conjunction();
    }

    private Specification<Player> hasBannedStatus(Boolean bannedStatus) {
        return (root, cq, cb) -> bannedStatus != null ?
                cb.equal(root.get("banned"), bannedStatus) : cb.conjunction();
    }

    private Specification<Player> hasBirthdayGreaterThan(Date birthday) {
        return (root, cq, cb) -> birthday != null ?
                cb.greaterThan(root.get("birthday"), birthday) : cb.conjunction();
    }

    private Specification<Player> hasBirthdayLessThan(Date birthday) {
        return (root, cq, cb) -> birthday != null ?
                cb.lessThan(root.get("birthday"), birthday) : cb.conjunction();
    }

    private Specification<Player> hasExperienceGreaterThanOrEqualTo(Integer experience) {
        return (root, cq, cb) -> experience != null ?
                cb.greaterThanOrEqualTo(root.get("experience"), experience) : cb.conjunction();
    }

    private Specification<Player> hasExperienceLessThanOrEqualTo(Integer experience) {
        return (root, cq, cb) -> experience != null ?
                cb.lessThanOrEqualTo(root.get("experience"), experience) : cb.conjunction();
    }

    private Specification<Player> hasLevelGreaterThanOrEqualTo(Integer level) {
        return (root, cq, cb) -> level != null ?
                cb.greaterThanOrEqualTo(root.get("level"), level) : cb.conjunction();
    }

    private Specification<Player> hasLevelLessThanOrEqualTo(Integer level) {
        return (root, cq, cb) -> level != null ?
                cb.lessThanOrEqualTo(root.get("level"), level) : cb.conjunction();
    }
}
