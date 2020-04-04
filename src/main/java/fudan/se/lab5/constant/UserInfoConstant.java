package fudan.se.lab5.constant;

public interface UserInfoConstant {
    public abstract String getLanguageName();

    // account info constant
    String getUsernamePassMismatch();
    String getFailedToSignupReason();
    String getSuccessToSignup();
    String getLoginSuccess();
    String getLoginFail();
    String getFailedToLoginReason();
    String getNullUser();
    String getValidName();
    String getInvalidNameWrongBegin();
    String getInvalidNameLength();
    String getInvalidNameHasOtherWords();
    String getValidPass();
    String getInvalidPassLength();
    String getInvalidPassMissing();
    String getInvalidPassMissingLetter();
    String getInvalidPassMissingNumber();
    String getInvalidPassIllegalCharacter();
    String getNullPass();
    String getNullName();
    String getUnknownLanguage();

    // order info constant
    String getOrderStyleMismatch();
    String getNullDrinkNameExist();
    String getNullIngredientExist();
    String getNullOrderValueExist();
    String getFailToCreateOrder();
    String getSuccessToCreateOrder();
    String getOrderTotalPrice();
    String getUnknownIngredientName();
    String getInvalidDrinkName();
    String getUnknownDrinkName();
    String getComboDiscountPerSet();
    String getComboOneFreePerSet();
    String getComboSecondHalfPrice();
    String getPricebreakMsg();
    String getTimetriggerMsg();
    String getItemtriggerMsg();
    String getFailEnableDiscountComponent();
    String getFailDisableDiscountComponent();
    String getFailGetDiscountComponent();

    // payment info const
    String getPaymentInfo();
}
