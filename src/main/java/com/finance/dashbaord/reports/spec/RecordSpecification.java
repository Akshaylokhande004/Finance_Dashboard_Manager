package com.finance.dashbaord.reports.spec;

import com.finance.dashbaord.entity.FinancialRecord;
import com.finance.dashbaord.reports.dto.DashBoardFilterRequest;
import org.springframework.data.jpa.domain.Specification;


public class RecordSpecification {

    public static Specification<FinancialRecord> filter(
            DashBoardFilterRequest req,
            String userEmail
    ) {

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            // ✅ ALWAYS APPLY
            predicate = cb.and(predicate,
                    cb.equal(root.get("user").get("email"), userEmail));

            predicate = cb.and(predicate,
                    cb.isFalse(root.get("isDeleted")));

            // 🔥 VERY IMPORTANT: NULL SAFE
            if (req != null) {

                // DATE FILTER
                if (req.getStartDate() != null) {
                    predicate = cb.and(predicate,
                            cb.greaterThanOrEqualTo(
                                    root.get("date"),
                                    req.getStartDate()
                            ));
                }

                if (req.getEndDate() != null) {
                    predicate = cb.and(predicate,
                            cb.lessThanOrEqualTo(
                                    root.get("date"),
                                    req.getEndDate()
                            ));
                }

                // TYPE FILTER (ENUM SAFE)
                if (req.getType() != null) {
                    predicate = cb.and(predicate,
                            cb.equal(
                                    root.get("type"),
                                    req.getType() // ✅ no .name()
                            ));
                }

                // CATEGORY FILTER
                if (req.getCategory() != null && !req.getCategory().isBlank()) {
                    predicate = cb.and(predicate,
                            cb.equal(
                                    root.get("category"),
                                    req.getCategory()
                            ));
                }
            }

            return predicate;
        };
    }
}
