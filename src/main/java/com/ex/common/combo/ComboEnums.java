package com.ex.common.combo;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ComboEnums extends ComboEnumCore {


    public static enum AccountTypeEnum implements EnumComboList{
        assets("A", "الأصول"),
        liabilities("L", "الالتزامات"),
        revenues("R", "الإيرادات"),
        expenses("E", "المصروفات"),
        capital("C", "رأس المال") ;
        public final String value;
        public final String label;
        private AccountTypeEnum(String value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }

        public static List getAccountTypeList(){
            return getEnumList(AccountTypeEnum.class);
        }
        public static String getAccountTypeLabel(String value){
            return getEnumLabel(AccountTypeEnum.class,value);
        }
    }

    //---------------------------------------------------------
    public static enum AccountCategoryEnum implements EnumComboList{
        debit("DB", "مدين"),
        credit("CR", "دائن");
        public final String value;
        public final String label;
        private AccountCategoryEnum(String value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }

        public static List getAccountCategoryList(){
            return getEnumList(AccountCategoryEnum.class);
        }
        public static String getAccountCategoryLabel(String value){
            return getEnumLabel(AccountCategoryEnum.class,value);
        }
    }

    //---------------------------------------------------------
    public static enum StatusEnum implements EnumComboList{
        active("active", "فعال"),
        inactive("inactive", "غير فعال");
        public final String value;
        public final String label;
        private StatusEnum(String value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }

        public static List getStatusList(){
            return getEnumList(StatusEnum.class);
        }
        public static String getStatusLabel(String value){
            return getEnumLabel(StatusEnum.class,value);
        }
    }

  public static enum StatusBoolEnum implements EnumComboList{
        active(true, "فعال"),
        inactive(false, "غير فعال");
        public final boolean value;
        public final String label;
        private StatusBoolEnum(boolean value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value+"";
        }
        public String getLabel() {
            return label;
        }

        public static List getStatusBoolList(){
            return getEnumList(StatusBoolEnum.class);
        }
        public static String getStatusBoolLabel(Boolean value){
            return getEnumLabel (StatusBoolEnum.class,value+"" );
        }
    }

    //---------------------------------------------------------
    public static enum BudgetTypeEnum implements EnumComboList{
        posted("A", "موازنه واحدة للجميع"),
        notPosted("P", "موازنه لكل فرع ");
        public final String value;
        public final String label;
        private BudgetTypeEnum(String value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }

        public static List getBudgetTypeList(){
            return getEnumList(BudgetTypeEnum.class);
        }
        public static String getBudgetTypeLabel(String value){
            return getEnumLabel(BudgetTypeEnum.class,value);
        }
    }

    //---------------------------------------------------------
  public static enum IntervalStatusEnum implements EnumComboList{
        open("OP", "مفتوحة"),
        partiallyClosed("PC", "مغلقة جزئيا"),
        closed("CL", "مغلقة");
        public final String value;
        public final String label;
        private IntervalStatusEnum(String value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }

        public static List getIntervalStatusList(){
            return getEnumList(IntervalStatusEnum.class);
        }
        public static String getIntervalStatusLabel(String value){
            return getEnumLabel(IntervalStatusEnum.class,value);
        }
    }
    //-----------------------------------------------------------------------
    public static enum FixedAssetItemStatusEnum implements EnumComboList {

        available("AV", "موجود"),
        custody("CU", "في عهدة"),
        damaged("DA", "تالف"),
        sold("SO", "مباع");

        public final String value;
        public final String label;

        private FixedAssetItemStatusEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValueLabel() {
            return "[" + value + "] " + label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public static List FixedAssetItemStatusList() {
            return getEnumList(FixedAssetItemStatusEnum.class);
        }

        public static String getFixedAssetStatusLable(String value) {
            return getEnumLabel(FixedAssetItemStatusEnum.class, value);
        }
    }

    public static enum BranchRegionEnum implements EnumComboList{
        westBank("WB", "الضفة الغربية"),
        gaza("GA", "غزة"),
        other("NO","غير ذلك");

        public final String value;
        public final String label;
        private BranchRegionEnum(String value,String label) {
            this.value = value;
            this.label = label;
        }
        public String getValueLabel() {
            return "["+value+"] "+label;
        }
        public String getValue() {
            return value;
        }
        public String getLabel() {
            return label;
        }

        public static List getBranchLocationList(){
            return getEnumList(AccountCategoryEnum.class);
        }
        public static String getBranchLocationLabel(String value){
            return getEnumLabel(AccountCategoryEnum.class,value);
        }
    }
}
