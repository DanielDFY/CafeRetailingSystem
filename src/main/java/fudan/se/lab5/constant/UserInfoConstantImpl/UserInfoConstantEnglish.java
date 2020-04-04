package fudan.se.lab5.constant.UserInfoConstantImpl;

import fudan.se.lab5.constant.UserInfoConstant;

public class UserInfoConstantEnglish implements UserInfoConstant {
    @Override
    public String getLanguageName() {
        return "English";
    }

    // account info constant
    public String getUsernamePassMismatch(){
        return "Username or password error";
    }
    public String getFailedToSignupReason(){
        return "Failed to create account, because {0}";
    }
    public String getSuccessToSignup(){
        return "User sign up successfully, name: {0}";
    }
    public String getLoginSuccess(){
        return "User has logged in";
    }
    public String getLoginFail(){
        return "Please login";
    }
    public String getFailedToLoginReason(){
        return "Failed to log in, because {0}";
    }
    public String getNullUser(){
        return "User can't be null";
    }
    public String getValidName() {
        return "Name is valid";
    }
    public String getInvalidNameWrongBegin() {
        return "Name is invalid, name is not begin with Starbb_";
    }
    public String getInvalidNameLength() {
        return "Name is invalid, name length is illegal";
    }
    public String getInvalidNameHasOtherWords() {
        return "Name is invalid, name has illegal characters";
    }
    public String getValidPass() {
        return "Password is valid";
    }
    public String getInvalidPassLength() {
        return "Password is invalid, password length is illegal";
    }
    public String getInvalidPassMissing() {
        return "Password is invalid, password missing _";
    }
    public String getInvalidPassMissingLetter() {
        return "Password is invalid, password missing letter";
    }
    public String getInvalidPassMissingNumber() {
        return "Password is invalid, password missing number";
    }
    public String getInvalidPassIllegalCharacter() {
        return "Password is invalid, password has illegal characters";
    }
    public String getNullPass() {
        return "Password is null";
    }
    public String getNullName() {
        return "Name is null";
    }
    public String getUnknownLanguage() {
        return "Unknown language";
    }

    // order info constant
    public String getOrderStyleMismatch() {
        return "Order can't be null";
    }
    public String getNullDrinkNameExist() {
        return "Null drink exists in the order";
    }
    public String getNullIngredientExist() {
        return "Null ingredient exists in the order";
    }
    public String getNullOrderValueExist() {
        return "Null number exists in the order";
    }
    public String getFailToCreateOrder() {
        return "Failed to create the order, because {0}";
    }
    public String getSuccessToCreateOrder() {
        return "name: {0}, size: {1}, number: {2}, price: {3}{4}";
    }
    public String getOrderTotalPrice() {
        return "Total price: {0}{1}";
    }
    public String getUnknownIngredientName() {
        return "Ingredient name can't be null or empty";
    }
    public String getInvalidDrinkName() {
        return "Drink name can't be null or empty";
    }
    public String getUnknownDrinkName() {
        return "Unknown drink name";
    }
    public String getComboDiscountPerSet() {
        return "Promotion: {0}, Cup size: {1}, {2}% discount for every {3} cups, money off: {4}{5}";
    }
    public String getComboOneFreePerSet() {
        return "Promotion: {0}, Cup size: {1}, 1 free for every {2} cups, money off: {3}{4}";
    }
    public String getComboSecondHalfPrice() {
        return "Promotion: {0}, Cup size: {1}, second cup half price, money off: {2}{3}";
    }
    public String getPricebreakMsg() {
        return "Promotion: {0}, {1}{2} off per {1}{3}, money off: {1}{4}";
    }
    public String getTimetriggerMsg() {
        return "Promotion: {0}, {1}% off between {2} and {3}";
    }
    public String getItemtriggerMsg() {
        return "Promotion: {0}, {1}% off for having target drink";
    }
    public String getFailEnableDiscountComponent() {
        return "Failed to enable discount component";
    }
    public String getFailDisableDiscountComponent() {
        return "Failed to disable discount component";
    }
    public String getFailGetDiscountComponent() {
        return "Failed to get unknown discount component";
    }

    // payment info const
    public String getPaymentInfo() {
        return "Price is {0}{1}, discount is {0}{2}, price after discount is {0}{2}";
    }
}

