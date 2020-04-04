package fudan.se.lab5.constant.UserInfoConstantImpl;

import fudan.se.lab5.constant.UserInfoConstant;

public class UserInfoConstantChinese implements UserInfoConstant {
    @Override
    public String getLanguageName() {
        return "Chinese";
    }

    // account info constant
    public String getUsernamePassMismatch(){
        return "用户名或密码错误";
    }
    public String getFailedToSignupReason(){
        return "账户创建失败，因为{0}";
    }
    public String getSuccessToSignup(){
        return "账户创建成功，姓名: {0}";
    }
    public String getLoginSuccess(){
        return "账户已经登陆";
    }
    public String getLoginFail(){
        return "请登录";
    }
    public String getFailedToLoginReason(){
        return "登录失败，因为{0}";
    }
    public String getNullUser(){
        return "账户不能为空";
    }
    public String getValidName() {
        return "账户名合法";
    }
    public String getInvalidNameWrongBegin() {
        return "账户名不合法：不是以 Starbb_ 开头";
    }
    public String getInvalidNameLength() {
        return "账户名不合法：长度不合法";
    }
    public String getInvalidNameHasOtherWords() {
        return "账户名不合法：含有不合法符号";
    }
    public String getValidPass() {
        return "密码合法";
    }
    public String getInvalidPassLength() {
        return "密码不合法：长度不合法";
    }
    public String getInvalidPassMissing() {
        return "密码不合法：缺少 _";
    }
    public String getInvalidPassMissingLetter() {
        return "密码不合法：缺少字母";
    }
    public String getInvalidPassMissingNumber() {
        return "密码不合法：缺少数字";
    }
    public String getInvalidPassIllegalCharacter() {
        return "密码不合法：含有不合法符号";
    }
    public String getNullPass() {
        return "密码为空";
    }
    public String getNullName() {
        return "账户名为空";
    }
    public String getUnknownLanguage() {
        return "未知语言";
    }

    // order info constant
    public String getOrderStyleMismatch() {
        return "订单不能为空";
    }
    public String getNullDrinkNameExist() {
        return "订单中不存在饮品";
    }
    public String getNullIngredientExist() {
        return "订单中不存在配料";
    }
    public String getNullOrderValueExist() {
        return "订单中数量为空";
    }
    public String getFailToCreateOrder() {
        return "订单创建失败，因为 {0}";
    }
    public String getSuccessToCreateOrder() {
        return "饮品: {0}, 杯型: {1}, 数量: {2}, 价格: {3}{4}";
    }
    public String getOrderTotalPrice() {
        return "总价: {0}{1}";
    }
    public String getUnknownIngredientName() {
        return "未知配料";
    }
    public String getInvalidDrinkName() {
        return "饮品名称不能为空" ;
    }
    public String getUnknownDrinkName() {
        return "未知饮品";
    }
    public String getComboDiscountPerSet() {
        return "促销策略: {0}, 杯型: {1}, {2}% 折扣每 {3} 杯, 折扣: {4}{5}";
    }
    public String getComboOneFreePerSet() {
        return "促销策略: {0}, 杯型: {1},一杯免费每 {2} 杯,折扣: {3}{4}";
    }
    public String getComboSecondHalfPrice() {
        return "促销策略: {0}, 杯型: {1}, 第二杯半价, 折扣: {2}{3}";
    }
    public String getPricebreakMsg() {
        return "促销策略: {0}, {1}{2} 折扣每 {1}{3}";
    }
    public String getTimetriggerMsg() {
        return "促销策略: {0}, {1}% 折扣在 {2} 和 {3}期间";
    }
    public String getItemtriggerMsg() {
        return "促销策略: {0}, {1}% 折扣因为目标饮品";
    }
    public String getFailEnableDiscountComponent() {
        return "启用折扣组件失败";
    }
    public String getFailDisableDiscountComponent() {
        return "无法禁用折扣组件";
    }
    public String getFailGetDiscountComponent() {
        return "未能获得未知折扣组件";
    }

    // payment info const
    public String getPaymentInfo() {
        return "价格 {0}{1}, 折扣 {0}{2}, 折后价格 {0}{2}";
    }
}
