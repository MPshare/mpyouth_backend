package kr.go.mapo.mpyouth;

import kr.go.mapo.mpyouth.domain.Category;
import kr.go.mapo.mpyouth.domain.ERole;
import kr.go.mapo.mpyouth.domain.Role;
import kr.go.mapo.mpyouth.domain.RoleHierarchy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Role role1 = Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build();

            em.persist(role1);

            Role role2 = Role.builder()
                    .name(ERole.ROLE_MANAGER)
                    .build();

            em.persist(role2);

            RoleHierarchy roleHierarchy1 = RoleHierarchy.builder()
                    .childName("ROLE_ADMIN")
                    .build();

            em.persist(roleHierarchy1);

            RoleHierarchy roleHierarchy2 = RoleHierarchy.builder()
                    .childName("ROLE_MANAGER")
                    .parentName(roleHierarchy1)
                    .build();

            em.persist(roleHierarchy2);

            Category youthCategory = Category.builder()
                    .name("청소년활동")
                    .build();
            Category youthChileCategory1 = Category.builder()
                    .name("건강/스포츠")
                    .build();
            Category youthChileCategory2 = Category.builder()
                    .name("모험개척")
                    .build();
            Category youthChileCategory3 = Category.builder()
                    .name("역사탐방")
                    .build();
            Category youthChileCategory4 = Category.builder()
                    .name("봉사협력")
                    .build();


            youthCategory.addChildCategory(youthChileCategory1);
            youthCategory.addChildCategory(youthChileCategory2);
            youthCategory.addChildCategory(youthChileCategory3);
            youthCategory.addChildCategory(youthChileCategory4);

            em.persist(youthCategory);

            Category volunteerCategory = Category.builder()
                    .name("봉사활동")
                    .build();
            Category volunteerChileCategory1 = Category.builder()
                    .name("교육봉사")
                    .build();
            Category volunteerChileCategory2 = Category.builder()
                    .name("노력봉사")
                    .build();
            Category volunteerChileCategory3 = Category.builder()
                    .name("문화봉사")
                    .build();
            Category volunteerChileCategory4 = Category.builder()
                    .name("재능봉사")
                    .build();

            volunteerCategory.addChildCategory(volunteerChileCategory1);
            volunteerCategory.addChildCategory(volunteerChileCategory2);
            volunteerCategory.addChildCategory(volunteerChileCategory3);
            volunteerCategory.addChildCategory(volunteerChileCategory4);

            em.persist(volunteerCategory);

            Category lifeLongEduCategory = Category.builder()
                    .name("평생교육")
                    .build();

            em.persist(lifeLongEduCategory);

            Category donationCategory = Category.builder()
                    .name("재능기부")
                    .build();
            Category donationChileCategory1 = Category.builder()
                    .name("디자인")
                    .build();
            Category donationChileCategory2 = Category.builder()
                    .name("번역/외국어")
                    .build();
            Category donationChileCategory3 = Category.builder()
                    .name("생활")
                    .build();
            Category donationChileCategory4 = Category.builder()
                    .name("음악/영상")
                    .build();
            Category donationChileCategory5 = Category.builder()
                    .name("프로그램 개발")
                    .build();
            Category donationChileCategory6 = Category.builder()
                    .name("문서작성")
                    .build();
            Category donationChileCategory7 = Category.builder()
                    .name("마케팅/비지니스")
                    .build();



            donationCategory.addChildCategory(donationChileCategory1);
            donationCategory.addChildCategory(donationChileCategory2);
            donationCategory.addChildCategory(donationChileCategory3);
            donationCategory.addChildCategory(donationChileCategory4);
            donationCategory.addChildCategory(donationChileCategory5);
            donationCategory.addChildCategory(donationChileCategory6);
            donationCategory.addChildCategory(donationChileCategory7);

            em.persist(donationCategory);
        }
    }


}

