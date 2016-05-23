package cn.com.tcxy.model.base;

import cn.com.tcxy.core.Pagination;
import java.util.ArrayList;
import java.util.List;

public class BaseDictionaryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Pagination pagination;

    public BaseDictionaryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPagination(Pagination pagination) {
        this.pagination=pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andValueIsNull() {
            addCriterion("value is null");
            return (Criteria) this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("value is not null");
            return (Criteria) this;
        }

        public Criteria andValueEqualTo(String value) {
            addCriterion("value =", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotEqualTo(String value) {
            addCriterion("value <>", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThan(String value) {
            addCriterion("value >", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThanOrEqualTo(String value) {
            addCriterion("value >=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThan(String value) {
            addCriterion("value <", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThanOrEqualTo(String value) {
            addCriterion("value <=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLike(String value) {
            addCriterion("value like", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotLike(String value) {
            addCriterion("value not like", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueIn(List<String> values) {
            addCriterion("value in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotIn(List<String> values) {
            addCriterion("value not in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueBetween(String value1, String value2) {
            addCriterion("value between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotBetween(String value1, String value2) {
            addCriterion("value not between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andTextIsNull() {
            addCriterion("text is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            addCriterion("text is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(String value) {
            addCriterion("text =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(String value) {
            addCriterion("text <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(String value) {
            addCriterion("text >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(String value) {
            addCriterion("text >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(String value) {
            addCriterion("text <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(String value) {
            addCriterion("text <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLike(String value) {
            addCriterion("text like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotLike(String value) {
            addCriterion("text not like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(List<String> values) {
            addCriterion("text in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(List<String> values) {
            addCriterion("text not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(String value1, String value2) {
            addCriterion("text between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(String value1, String value2) {
            addCriterion("text not between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextEnIsNull() {
            addCriterion("text_en is null");
            return (Criteria) this;
        }

        public Criteria andTextEnIsNotNull() {
            addCriterion("text_en is not null");
            return (Criteria) this;
        }

        public Criteria andTextEnEqualTo(String value) {
            addCriterion("text_en =", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnNotEqualTo(String value) {
            addCriterion("text_en <>", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnGreaterThan(String value) {
            addCriterion("text_en >", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnGreaterThanOrEqualTo(String value) {
            addCriterion("text_en >=", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnLessThan(String value) {
            addCriterion("text_en <", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnLessThanOrEqualTo(String value) {
            addCriterion("text_en <=", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnLike(String value) {
            addCriterion("text_en like", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnNotLike(String value) {
            addCriterion("text_en not like", value, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnIn(List<String> values) {
            addCriterion("text_en in", values, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnNotIn(List<String> values) {
            addCriterion("text_en not in", values, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnBetween(String value1, String value2) {
            addCriterion("text_en between", value1, value2, "textEn");
            return (Criteria) this;
        }

        public Criteria andTextEnNotBetween(String value1, String value2) {
            addCriterion("text_en not between", value1, value2, "textEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescEnIsNull() {
            addCriterion("desc_en is null");
            return (Criteria) this;
        }

        public Criteria andDescEnIsNotNull() {
            addCriterion("desc_en is not null");
            return (Criteria) this;
        }

        public Criteria andDescEnEqualTo(String value) {
            addCriterion("desc_en =", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnNotEqualTo(String value) {
            addCriterion("desc_en <>", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnGreaterThan(String value) {
            addCriterion("desc_en >", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnGreaterThanOrEqualTo(String value) {
            addCriterion("desc_en >=", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnLessThan(String value) {
            addCriterion("desc_en <", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnLessThanOrEqualTo(String value) {
            addCriterion("desc_en <=", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnLike(String value) {
            addCriterion("desc_en like", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnNotLike(String value) {
            addCriterion("desc_en not like", value, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnIn(List<String> values) {
            addCriterion("desc_en in", values, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnNotIn(List<String> values) {
            addCriterion("desc_en not in", values, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnBetween(String value1, String value2) {
            addCriterion("desc_en between", value1, value2, "descEn");
            return (Criteria) this;
        }

        public Criteria andDescEnNotBetween(String value1, String value2) {
            addCriterion("desc_en not between", value1, value2, "descEn");
            return (Criteria) this;
        }

        public Criteria andItemOrderIsNull() {
            addCriterion("item_order is null");
            return (Criteria) this;
        }

        public Criteria andItemOrderIsNotNull() {
            addCriterion("item_order is not null");
            return (Criteria) this;
        }

        public Criteria andItemOrderEqualTo(Integer value) {
            addCriterion("item_order =", value, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderNotEqualTo(Integer value) {
            addCriterion("item_order <>", value, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderGreaterThan(Integer value) {
            addCriterion("item_order >", value, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_order >=", value, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderLessThan(Integer value) {
            addCriterion("item_order <", value, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderLessThanOrEqualTo(Integer value) {
            addCriterion("item_order <=", value, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderIn(List<Integer> values) {
            addCriterion("item_order in", values, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderNotIn(List<Integer> values) {
            addCriterion("item_order not in", values, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderBetween(Integer value1, Integer value2) {
            addCriterion("item_order between", value1, value2, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andItemOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("item_order not between", value1, value2, "itemOrder");
            return (Criteria) this;
        }

        public Criteria andBulitInIsNull() {
            addCriterion("is_bulit_in is null");
            return (Criteria) this;
        }

        public Criteria andBulitInIsNotNull() {
            addCriterion("is_bulit_in is not null");
            return (Criteria) this;
        }

        public Criteria andBulitInEqualTo(Boolean value) {
            addCriterion("is_bulit_in =", value, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInNotEqualTo(Boolean value) {
            addCriterion("is_bulit_in <>", value, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInGreaterThan(Boolean value) {
            addCriterion("is_bulit_in >", value, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_bulit_in >=", value, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInLessThan(Boolean value) {
            addCriterion("is_bulit_in <", value, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInLessThanOrEqualTo(Boolean value) {
            addCriterion("is_bulit_in <=", value, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInIn(List<Boolean> values) {
            addCriterion("is_bulit_in in", values, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInNotIn(List<Boolean> values) {
            addCriterion("is_bulit_in not in", values, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInBetween(Boolean value1, Boolean value2) {
            addCriterion("is_bulit_in between", value1, value2, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andBulitInNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_bulit_in not between", value1, value2, "bulitIn");
            return (Criteria) this;
        }

        public Criteria andValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andValidEqualTo(Boolean value) {
            addCriterion("is_valid =", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotEqualTo(Boolean value) {
            addCriterion("is_valid <>", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThan(Boolean value) {
            addCriterion("is_valid >", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_valid >=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThan(Boolean value) {
            addCriterion("is_valid <", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThanOrEqualTo(Boolean value) {
            addCriterion("is_valid <=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidIn(List<Boolean> values) {
            addCriterion("is_valid in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotIn(List<Boolean> values) {
            addCriterion("is_valid not in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid not between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}